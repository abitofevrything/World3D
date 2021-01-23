package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.EventManager;
import me.abitofevrything.world3d.events.output.RenderEvent;
import me.abitofevrything.world3d.events.output.RenderEventListener;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

/**
 * A class for managing input
 * 
 * @author abitofevrything
 *
 */
public class Input {

	private static int MDX, MDY, MDW;
	
	private static Map<Integer, Boolean> mousePressedButtons = new HashMap<Integer, Boolean>();
	private static Map<Integer, Boolean> keyboardPressedKeys = new HashMap<Integer, Boolean>();
	
	/**
	 * Initializes the input manager and the event triggers
	 */
	public static void init() {
		
		new RenderEventListener() {
			
			@Override
			public void onEvent(RenderEvent event) {
				if (Mouse.isCreated() && Keyboard.isCreated()) {
					update();
				}
			}
		}.listen();
		
		new MouseMovedEventListener() {
			
			@Override
			public void onEvent(MouseMovedEvent event) {
				for (int i = 0; i < getMouseButtonCount(); i++) {
					if (isMouseButtonDown(i)) {
						EventManager.triggerEvent(new MouseDraggedEvent(i, event.getStartX(), event.getStartY(), event.getEndX(), event.getEndY()));
					}
				}
			}
		}.listen();
		
		for (int i = 0; i < Mouse.getButtonCount(); i++) {
			mousePressedButtons.put(i, false);
		}
		
		for (int i = 0; i < Keyboard.getKeyCount(); i++) {
			keyboardPressedKeys.put(i, false);
		}
	}
	
	private static void update() {
		MDX = Mouse.getDX();
		MDY = Mouse.getDY();
		MDW = Mouse.getDWheel();
		
		if (MDX != 0 || MDY != 0) {
			EventManager.triggerEvent(new MouseMovedEvent(Mouse.getX() - MDX, Mouse.getY() - MDY, Mouse.getX(), Mouse.getY()));
		}
		
		if (MDW != 0) {
			EventManager.triggerEvent(new MouseScrollEvent(MDW));
		}
		
		for (int i = 0; i < Mouse.getButtonCount(); i++) {
			if (!mousePressedButtons.get(i)) {
				if (Mouse.isButtonDown(i)) {
					EventManager.triggerEvent(new MousePressedEvent(i, Mouse.getX(), Mouse.getY()));
					mousePressedButtons.put(i, true);
				}
			} else {
				if (!Mouse.isButtonDown(i)) {
					EventManager.triggerEvent(new MouseReleasedEvent(i, Mouse.getX(), Mouse.getY()));
					mousePressedButtons.put(i, false);
				}
			}
			
			if (Mouse.isButtonDown(i)) {
				EventManager.triggerEvent(new MouseButtonHeldEvent(i));
			}
		}
		
		for (int i = 0; i < Keyboard.getKeyCount(); i++) {
			if (!keyboardPressedKeys.get(i)) {
				if (Keyboard.isKeyDown(i)) {
					EventManager.triggerEvent(new KeyPressedEvent(i));
					keyboardPressedKeys.put(i, true);
				}
			} else {
				if (!Keyboard.isKeyDown(i)) {
					EventManager.triggerEvent(new KeyReleasedEvent(i));
					keyboardPressedKeys.put(i, false);
				}
			}
			
			if (Keyboard.isKeyDown(i)) {
				EventManager.triggerEvent(new KeyHeldEvent(i));
			}
		}
	}
	
	/**
	 * Chackes whever a keyboard key is pressed or not
	 * 
	 * @param key The key id to check.
	 * @return A boolean, true if the key is pressed, false otherwise
	 * 
	 * @see Keyboard
	 */
	public boolean isKeyDown(int key) {
		return Keyboard.isKeyDown(key);
	}

	/**
	 * Gets the mouse delta x
	 * 
	 * @return The delta x since the last update
	 */
	public static float getMouseDX() {
		return MDX;
	}
	
	/**
	 * Gets the mouse delta y
	 * 
	 * @return The delta y since the last update
	 */
	public static float getMouseDY() {
		return MDY;
	}
	
	/**
	 * Gets the mouse wheel delta
	 * 
	 * @return The mouse wheel delta since the last update
	 */
	public static float getMouseDWheel() {
		return MDW;
	}
	
	/**
	 * Gets the mouse position delta
	 * 
	 * @return The mouse position delta since the last update
	 */
	public static Vector2f getMouseDeltaPosition() {
		return new Vector2f(MDX, MDY);
	}
	
	/**
	 * Gets the x position of the mouse
	 * 
	 * @return The x position of the mouse
	 */
	public static float getMouseX() {
		return Mouse.getX();
	}
	
	/**
	 * Gets the y position of the mouse
	 * 
	 * @return The y position of the mouse
	 */
	public static float getMouseY() {
		return Mouse.getY();
	}
	
	/**
	 * Sets the mouse to be "grabbed" or not
	 * "Grabbed" means that the mouse is hidden and bound to the window
	 * 
	 * @param grabbed true if the mouse is to be grabbed, false otherwise
	 */
	public static void setMouseGrabbed(boolean grabbed) {
		Mouse.setGrabbed(grabbed);
	}
	
	/**
	 * Get the number if buttons on the mouse
	 * 
	 * @return The number of buttons on the mouse
	 */
	public static int getMouseButtonCount() {
		return Mouse.getButtonCount();
	}
	
	/**
	 * Gets the index of a given mouse button
	 * 
	 * @param name The name of the button to retrieve. Format : "BUTTON[x]" where [x] is a number from 0 to 16
	 * @return The index of the button
	 */
	public static int getMouseButtonIndex(String name) {
		return Mouse.getButtonIndex(name);
	}
	
	/**
	 * Gets the name of a button for a given index
	 * 
	 * @param index The index of the button to retrieve
	 * @return The name of the button
	 */
	public static String getMouseButtonName(int index) {
		return Mouse.getButtonName(index);
	}
	
	/**
	 * Chacks if a button on the mouse is pressed
	 * 
	 * @param button The index of the button to check
	 * @return true if the button is pressed, false otherwise
	 */
	public static boolean isMouseButtonDown(int button) {
		return Mouse.isButtonDown(button);
	}
	
	/**
	 * Checks if the mouse has a wheel
	 * 
	 * @return true if the mouse has a wheel, false otherwise
	 */
	public static boolean mouseHasWheel() {
		return Mouse.hasWheel();
	}
	
	/**
	 * Checks if the mouse is "grabbed"
	 * "Grabbed" means that the mouse is hidden and bound to the window
	 * 
	 * @return true if the mouse is grabbed, false otherwise
	 */
	public static boolean isMouseGrabbd() {
		return Mouse.isGrabbed();
	}
	
	/**
	 * Checks if the mouse is inside the window
	 * 
	 * @return true if the mouse is inside the window, false otherwise
	 */
	public static boolean isMouseInsideWindow() {
		return Mouse.isInsideWindow();
	}
	
	/**
	 * Sets the position of the mouse relative to the window bottom-left
	 * 
	 * @param x the x position of the mouse
	 * @param y the y position of the mouse
	 */
	public static void setMousePosition(int x, int y) {
		Mouse.setCursorPosition(x, y);
	}
	
	/**
	 * Gets the number of keys on the keyboard
	 * 
	 * @return the number of keys on the keyboard
	 */
	public static int getKeyboardNumKeys() {
		return Keyboard.getKeyCount();
	}
	
}

