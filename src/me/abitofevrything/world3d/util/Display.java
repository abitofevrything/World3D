package me.abitofevrything.world3d.util;

import me.abitofevrything.world3d.events.EventManager;
import me.abitofevrything.world3d.events.game.GameCloseEvent;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public class Display {
	
	private static int width, height;
	
	private static boolean glfwInitialized = false;

	private static long lastFrameTime;
	private static float delta;

	private static long windowHandle = 0L;
	
	@SuppressWarnings("unused")
	private static GLFWFramebufferSizeCallback callbackReference;
	
	public static void create(String title, int width, int height) {
		initGlfw();
		
		boolean shouldMaximise = (width == 0 && height == 0);
		if (shouldMaximise) {
			width = 1280; //GLFW will complain if width and height are 0
			height = 720; //Default to 1280x720
		}
		
		if (windowHandle != 0L) throw new RuntimeException("Display already created");
		
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE); 
		windowHandle = glfwCreateWindow(width, height, title, 0, 0);
		
		if (windowHandle == 0) {
			throw new RuntimeException("Unable to create display");
		}
		
		glfwMakeContextCurrent(windowHandle);
		GL.createCapabilities();
		
		glEnable(GL_MULTISAMPLE);
		
		glViewport(0, 0, Display.width, Display.height);
		lastFrameTime = getCurrentTime();
		
		glfwSetFramebufferSizeCallback(windowHandle, (callbackReference = new GLFWFramebufferSizeCallback() {
		    @Override
		    public void invoke(long window, int width, int height) {
		        updateDimensions();
		    }
		}));
		
		glfwShowWindow(windowHandle);
		
		if (shouldMaximise) {
			maximise();
		}
		
		updateDimensions();
	}
	
	public static void createFullscreen(String title, int targetWidth, int targetHeight) {
		initGlfw();
		
		if (windowHandle != 0L) throw new RuntimeException("Display already created");
		
		if (targetWidth == 0 && targetHeight == 0) {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			targetWidth = (int) screenSize.getWidth();
			targetHeight = (int) screenSize.getHeight();
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE); 
		windowHandle = glfwCreateWindow(targetWidth, targetHeight, title, glfwGetPrimaryMonitor(), 0);
		
		if (windowHandle == 0) {
			throw new RuntimeException("Unable to create display");
		}
		
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetWindowMonitor(windowHandle));
		width = videoMode.width();
		height = videoMode.height();
		
		glfwMakeContextCurrent(windowHandle);
		GL.createCapabilities();
		
		glEnable(GL_MULTISAMPLE);
	
		glViewport(0, 0, targetWidth, targetHeight);
		lastFrameTime = getCurrentTime();
		
		glfwSetFramebufferSizeCallback(windowHandle, new GLFWFramebufferSizeCallback() {
		    @Override
		    public void invoke(long window, int width, int height) {
		        updateDimensions();
		    }
		});
		
		glfwShowWindow(windowHandle);
		
		updateDimensions();
	}

	public static void update() {
		if (windowHandle == 0) throw new RuntimeException("Display not created yet");
		
		glfwPollEvents();
		glfwSwapBuffers(windowHandle);
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

	public static void close() {
		EventManager.triggerEvent(new GameCloseEvent());
		glfwDestroyWindow(windowHandle);
		windowHandle = 0L;
	}

	private static long getCurrentTime() {
		return System.currentTimeMillis();
	}

	public static void maximise() {
		if (windowHandle == 0) throw new RuntimeException("Display not created");
		
		glfwMaximizeWindow(windowHandle);
		
		updateDimensions();
	}
	
	public static boolean isCloseRequested() {
		return glfwWindowShouldClose(windowHandle);
	}
	
	public static int getWidth() {
		return width;
	}
	
	public static int getHeight() {
		return height;
	}
	
	private static void updateDimensions() {
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		glfwGetWindowSize(windowHandle, w, h);
		width = w.get(0);
		height = h.get(0);
		
		glViewport(0, 0, width, height);
	}
	
	private static void initGlfw() {
		if (!glfwInitialized) {
			glfwInitialized = true;
			
			glfwInit();
			glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
		}
	}
	
	/**
	 * @return The GLFW handle for this window
	 */
	public static long getHandle() {
		if (windowHandle == 0L) throw new RuntimeException("Display not created");
		return windowHandle;
	}
	
}
