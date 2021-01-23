package me.abitofevrything.world3d.rendering.shaders;

import org.lwjgl.opengl.GL20;

import me.abitofevrything.world3d.textures.Texture;

/**
 * Represents a uniform sampler
 * A sampler is used to sample textures
 * 
 * @author abitofevrything
 *
 */
public class UniformSampler extends Uniform {

	private int currentValue;
	private boolean used = false;

	public UniformSampler(String name) {
		super(name);
	}

	public void loadTexUnit(int texUnit) {
		if (!used || currentValue != texUnit) {
			GL20.glUniform1i(super.getLocation(), texUnit);
			used = true;
			currentValue = texUnit;
		}
	}
	
	public void loadAndBindTexture(Texture tex, int texUnit) {
		tex.bindToUnit(texUnit);
		loadTexUnit(texUnit);
	}

}
