package me.abitofevrything.world3d.util;

import me.abitofevrything.world3d.events.EventManager;
import me.abitofevrything.world3d.events.game.GameCloseEvent;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	private static int FPS_CAP = Integer.MAX_VALUE;

	private static long lastFrameTime;
	private static float delta;

	private static boolean isFullScreen = false;
	
	public static void createDisplay(String title, int width, int height) {
		
		DisplayMode desktop = Display.getDesktopDisplayMode();
		if (width == 0) width = desktop.getWidth();
		if (height == 0) height = desktop.getHeight();
		
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			ContextAttribs attribs = new ContextAttribs(3, 2).withProfileCore(true).withForwardCompatible(true);
			Display.setInitialBackground(0, 0, 0);
			Display.create(new PixelFormat().withDepthBits(24).withSamples(4), attribs);
			Display.setTitle(title);
			Display.setLocation(0, 0);
			GL11.glEnable(GL13.GL_MULTISAMPLE);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.err.println("Couldn't create display!");
			System.exit(-1);
		}
		GL11.glViewport(0, 0, width, height);
		lastFrameTime = getCurrentTime();
	}
	
	public static void createFullScreenDisplay(String title, int targetWidth, int targetHeight) {
		
		DisplayMode desktop = Display.getDesktopDisplayMode();
		if (targetWidth == 0) targetWidth = desktop.getWidth();
		if (targetHeight == 0) targetHeight = desktop.getHeight();
		
		try {
			
			DisplayMode[] displayModes = Display.getAvailableDisplayModes();
			
			DisplayMode chosen = null;
			int chosenOffset = -1;
			
			for (DisplayMode curr : displayModes) {
				if (curr.isFullscreenCapable()) {
					int offset = (int)(Math.abs(targetWidth - curr.getWidth()) * Math.abs(targetHeight - curr.getHeight()));
					
					if (offset < chosenOffset || chosenOffset == -1) {
						chosenOffset = offset;
						chosen = curr;
					}
				}
			}
			
			if (chosen == null) {
				createDisplay(title, 1, 1);
				maximiseDisplay();
				return;
			} else if (chosen.getWidth() != targetWidth || chosen.getHeight() != targetHeight) {
				System.out.println("Unable to create fullscreen display with requested resolution (" + targetWidth + "*" + targetHeight + "). Using " + chosen.getWidth() + "*" + chosen.getHeight() + " instead.");
			}
			
			isFullScreen = true;
			
			Display.setDisplayModeAndFullscreen(chosen);
			Display.setInitialBackground(0, 0, 0);
			ContextAttribs attribs = new ContextAttribs(3, 2).withProfileCore(true).withForwardCompatible(true);
			Display.create(new PixelFormat().withDepthBits(24).withSamples(4), attribs);
			Display.setTitle(title);
			GL11.glEnable(GL13.GL_MULTISAMPLE);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.err.println("Couldn't create display!");
			System.exit(-1);
		}
		GL11.glViewport(0, 0, targetWidth, targetHeight);
		lastFrameTime = getCurrentTime();
	}

	public static void update() {
		if (Display.wasResized() && !isFullScreen) {
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		}
		Display.sync(FPS_CAP);
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime) / 1000f;
		lastFrameTime = currentFrameTime;
	}

	public static void setTitle(String title) {
		Display.setTitle(title);
	}
	
	/**
	 * Gets the last frame time in seconds
	 * 
	 * @return the last frame time in seconds
	 */
	public static float getFrameTime() {
		return delta;
	}

	public static void closeDisplay() {
		EventManager.triggerEvent(new GameCloseEvent());
		Display.destroy();
	}

	private static long getCurrentTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}

	public static void maximiseDisplay() {
		try {
			Display.setLocation(0, 0);
			Display.setDisplayMode(Display.getDesktopDisplayMode());
		} catch (LWJGLException e) {
			System.err.println("Unable to maximise window");
			e.printStackTrace();
		}
	}
	
}
