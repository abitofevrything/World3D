package me.abitofevrything.world3d.rendering.shaders;

import org.lwjgl.opengl.GL20;

/**
 * Represents a uniform boolean
 * 
 * @author abitofevrything
 *
 */
public class UniformBoolean extends Uniform {

	private boolean currentBool;
	private boolean used = false;
	
	public UniformBoolean(String name){
		super(name);
	}
	
	public void loadBoolean(boolean bool){
		if(!used || currentBool != bool){
			GL20.glUniform1f(super.getLocation(), bool ? 1f : 0f);
			used = true;
			currentBool = bool;
		}
	}
	
}
