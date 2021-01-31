package me.abitofevrything.world3d.events.input;

import static org.lwjgl.glfw.GLFW.*;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import me.abitofevrything.world3d.events.EventManager;
import me.abitofevrything.world3d.events.game.GameUpdateEvent;
import me.abitofevrything.world3d.events.game.GameUpdateEventListener;
import me.abitofevrything.world3d.util.Display;

/**
 * A class for managing input
 * 
 * @author abitofevrything
 *
 */
public class Input {

	/* TODO
	 * 
	 * MouseClicked
	 * 
	 */
	
	private static boolean mouseGrabbed = false, ignoreMovedCallback = false;
	private static int grabbedX, grabbedY;
	
	private static Map<Integer, Boolean> pressedKeys = new HashMap<Integer, Boolean>();
	private static Map<Integer, Boolean> pressedButtons  = new HashMap<Integer, Boolean>();
	
	@SuppressWarnings("unused")
	private static GLFWKeyCallback keyCallback;
	@SuppressWarnings("unused")
	private static GLFWCharCallback charCallback;
	@SuppressWarnings("unused")
	private static GLFWCursorPosCallback cursorPosCallback;
	@SuppressWarnings("unused")
	private static GLFWMouseButtonCallback mouseButtonCallback;
	@SuppressWarnings("unused")
	private static GLFWScrollCallback scrollCallback;
	
	private static int prevCursorX, prevCursorY;
	
	public static void init() {
		/* KeyPressed / KeyReleased & data management for KeyHeld */
		glfwSetKeyCallback(Display.getHandle(), (keyCallback = new GLFWKeyCallback() {
			
			@Override
			public void invoke(long window, int key, int scancode, int action, int mode) {
				
				if (action == GLFW_PRESS) {
					EventManager.triggerEvent(new KeyPressedEvent(key));
					pressedKeys.put(key, true);
				} else if (action == GLFW_RELEASE) {
					EventManager.triggerEvent(new KeyReleasedEvent(key));
					pressedKeys.put(key, false);
				}
			}
			
		}));
		
		/* KeyHeld, MouseHeld */
		new GameUpdateEventListener() {
			
			@Override
			public void onEvent(GameUpdateEvent event) {
				for (int key : pressedKeys.keySet()) {
					if (pressedKeys.get(key)) {
						EventManager.triggerEvent(new KeyHeldEvent(key));
					}
				}
				
				for (int button : pressedButtons.keySet()) {
					if (pressedButtons.get(button)) {
						EventManager.triggerEvent(new MouseHeldEvent(button));
					}
				}
			}
			
		}.listen();
		
		/* KeyTyped */
		glfwSetCharCallback(Display.getHandle(), (charCallback = new GLFWCharCallback() {
			
			@Override
			public void invoke(long window, int key) {
				EventManager.triggerEvent(new KeyTypedEvent(key));
			}
			
		}));
		
		/* MouseMoved / MouseDragged */
		glfwSetCursorPosCallback(Display.getHandle(), (cursorPosCallback = new GLFWCursorPosCallback() {
			
			@Override
			public void invoke(long window, double x, double y) {
				if (ignoreMovedCallback) {
					ignoreMovedCallback = false;
					return;
				}
				
				EventManager.triggerEvent(new MouseMovedEvent(prevCursorX, prevCursorY, (int)x, (int)y));
				
				for (int button : pressedButtons.keySet()) {
					if (pressedButtons.get(button)) {
						EventManager.triggerEvent(new MouseDraggedEvent(button, prevCursorX, prevCursorY, (int)x, (int)y));
					}
				}
				
				prevCursorX = (int)x;
				prevCursorY = (int)y;
				
				if (mouseGrabbed) {
					ignoreMovedCallback = true;
					setMousePos(Display.getWidth() / 2, Display.getHeight() / 2);
				}
			}
			
		}));
		
		/* MousePressed / MouseReleased / MouseClicked */
		glfwSetMouseButtonCallback(Display.getHandle(), (mouseButtonCallback = new GLFWMouseButtonCallback() {
			
			@Override
			public void invoke(long window, int button, int action, int mode) {
				if (action == GLFW_PRESS) {
					EventManager.triggerEvent(new MousePressedEvent(button, prevCursorX, prevCursorY));
					pressedButtons.put(button, true);
					
					int x = prevCursorX, y = prevCursorY;
					
					new MouseReleasedEventListener() {
						
						@Override
						public void onEvent(MouseReleasedEvent event) {
							if (prevCursorX == x && prevCursorY == y) {
								EventManager.triggerEvent(new MouseClickedEvent(button, x, y));
							}
						}
						
					}.listen();
					
				} else if (action == GLFW_RELEASE) {
					EventManager.triggerEvent(new MouseReleasedEvent(button, prevCursorX, prevCursorY));
					pressedButtons.put(button, false);
				}
			}
		}));
		
		/* MouseScrolled */
		glfwSetScrollCallback(Display.getHandle(), (scrollCallback = new GLFWScrollCallback() {
			
			@Override
			public void invoke(long window, double dx, double dy) {
				EventManager.triggerEvent(new MouseScrolledEvent((int)dy * 100));
			}
			
		}));
	}
	
	public static boolean isKeyPressed(int key) {
		return pressedKeys.get(key) == null ? false : pressedKeys.get(key);
	}
	
	public static boolean isMouseButtonPressed(int button) {
		return pressedButtons.get(button) == null ? false : pressedButtons.get(button);
	}
	
	public static void setMousePos(int x, int y) {
		prevCursorX = x;
		prevCursorY = y;
		
		glfwSetCursorPos(Display.getHandle(), x, y);
	}
	
	public static void setMouseGrabbed(boolean grabbed) {
		if (grabbed && !mouseGrabbed) {
			grabbedX = prevCursorX;
			grabbedY = prevCursorY;
			
			ignoreMovedCallback = true;
			setMousePos(Display.getWidth() / 2, Display.getHeight() / 2);
		} else if (!grabbed && grabbed) {
			//Release mouse at grabbed coords
			ignoreMovedCallback = true;
			setMousePos(grabbedX, grabbedY);
		}
		
		glfwSetInputMode(Display.getHandle(), GLFW_CURSOR, grabbed ? GLFW_CURSOR_HIDDEN : GLFW_CURSOR_NORMAL);
		
		mouseGrabbed = grabbed;
	}
	
}

