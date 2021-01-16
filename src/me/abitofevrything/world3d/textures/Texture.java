package me.abitofevrything.world3d.textures;

import me.abitofevrything.world3d.util.ResourceFile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Texture {

	private final int textureId;
	private final int width, height;
	private final int type;

	public Texture(int textureId, int width, int height) {
		this(textureId, GL11.GL_TEXTURE_2D, width, height);
	}

	protected Texture(int textureId, int type, int width, int height) {
		this.textureId = textureId;
		this.type = type;
		this.width = width;
		this.height = height;
	}

	public void bindToUnit(int unit) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
		GL11.glBindTexture(type, textureId);
	}

	public void delete() {
		GL11.glDeleteTextures(textureId);
	}

	public static TextureBuilder newTexture(ResourceFile textureFile) {
		return new TextureBuilder(textureFile);
	}
	
	public int getId() {
		return textureId;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
