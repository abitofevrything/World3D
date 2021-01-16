package me.abitofevrything.world3d.rendering.shaders;

import org.lwjgl.opengl.GL20;

public abstract class Uniform {
	
	private static final int NOT_FOUND = -1;
	
	private String name;
	private int location;
	
	protected Uniform(String name){
		this.name = name;
	}
	
	protected void storeUniformLocation(int programID, boolean logNotFound){
		location = GL20.glGetUniformLocation(programID, name);
		if(location == NOT_FOUND && logNotFound){
			System.err.println("No uniform variable called " + name + " found!");
		}
	}
	
	public int getLocation(){
		return location;
	}

}
