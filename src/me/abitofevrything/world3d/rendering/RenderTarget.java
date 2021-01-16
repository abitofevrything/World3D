package me.abitofevrything.world3d.rendering;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

import me.abitofevrything.world3d.events.EventSubscribable;
import me.abitofevrything.world3d.events.output.RenderEvent;
import me.abitofevrything.world3d.textures.Texture;

/**
 * Represents an FBO - An item that can be drawn and read from
 * 
 * @author abitofevrything
 *
 */
public class RenderTarget extends EventSubscribable {
	
	public static final RenderTarget SCREEN = new RenderTarget(0);
	
	private int width, height;
	private int id;
	private Texture colourTex, depthTex;
	
	private RenderTarget(int width, int height, int id) {
		this.width = width;
		this.height = height;
		
		this.id = id;
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, id);
		GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
		
		/* Create and set up the colour texture for this FBO */
		colourTex = new Texture(GL11.glGenTextures(), width, height);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, colourTex.getId());
		GL11.glTexImage2D(colourTex.getId(), 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, colourTex.getId(), 0);
		
		
		/* Create and set up the depth texture for this FBO */
		depthTex = new Texture(GL11.glGenTextures(), width, height);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, depthTex.getId());
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT24, width, height, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (ByteBuffer) null);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, depthTex.getId(), 0);
	}
	
	public RenderTarget() {
		this(Display.getWidth(), Display.getHeight(), GL30.glGenFramebuffers());
	}
	
	private RenderTarget(int id) {
		this(Display.getWidth(), Display.getHeight(), id);
	}
	
	public Texture getColourTexture() {
		return colourTex;
	}
	
	public Texture getDepthTexture() {
		return depthTex;
	}

	public void bindToRenderOutput() {
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, id);
		GL11.glViewport(0, 0, width, height);
	}
	
	public void finishRender() {
		super.triggerEventForSubscribers(new RenderEvent("contentUpdate"));
	}
		
	public int getId() {
		return id;
	}
}
