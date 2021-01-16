package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.EventManager;
import me.abitofevrything.world3d.events.output.RenderEvent;
import me.abitofevrything.world3d.events.output.RenderEventListener;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input {

	private static int MDX, MDY, MDW;
	
	private static Map<Integer, Boolean> mousePressedButtons = new HashMap<Integer, Boolean>();
	private static Map<Integer, Boolean> keyboardPressedKeys = new HashMap<Integer, Boolean>();
	
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
	
	public boolean isKeyDown(int key) {
		return Keyboard.isKeyDown(key);
	}

	public static float getMouseDX() {
		return MDX;
	}
	
	public static float getMouseDY() {
		return MDY;
	}
	
	public static float getMouseDWheel() {
		return MDW;
	}
	
	public static float getMouseX() {
		return Mouse.getX();
	}
	
	public static float getMouseY() {
		return Mouse.getY();
	}
	
	public static void setMouseGrabbed(boolean grabbed) {
		Mouse.setGrabbed(grabbed);
	}
	
	public static int getMouseButtonCount() {
		return Mouse.getButtonCount();
	}
	
	public static int getMouseButtonIndex(String name) {
		return Mouse.getButtonIndex(name);
	}
	
	public static String getMouseButtonName(int index) {
		return Mouse.getButtonName(index);
	}
	
	public static boolean isMouseButtonDown(int button) {
		return Mouse.isButtonDown(button);
	}
	
	public static boolean mouseHasWheel() {
		return Mouse.hasWheel();
	}
	
	public static boolean isMouseGrabbd() {
		return Mouse.isGrabbed();
	}
	
	public static boolean isMouseInsideWindow() {
		return Mouse.isInsideWindow();
	}
	
	public static void setMousePosition(int x, int y) {
		Mouse.setCursorPosition(x, y);
	}
	
	public static int getKeyboardNumKeys() {
		return Keyboard.getKeyCount();
	}
	
}

