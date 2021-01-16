package me.abitofevrything.world3d.rendering.shaders;

import me.abitofevrything.world3d.util.ResourceFile;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderProgram {

	private int programID;

	private List<Uniform> uniforms = new ArrayList<Uniform>();
	
	public ShaderProgram(ResourceFile vertexFile, ResourceFile fragmentFile, String... inVariables) {
		int vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		int fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes(inVariables);
		GL20.glLinkProgram(programID);
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
	}
	
	protected void addUniforms(Uniform... uniforms){
		for(Uniform uniform : uniforms){
			uniform.storeUniformLocation(programID, true);
			this.uniforms.add(uniform);
		}
		GL20.glValidateProgram(programID);
		
	}
	
	protected void addUniformsNoWarn(Uniform... uniforms) {
		for(Uniform uniform : uniforms){
			uniform.storeUniformLocation(programID, false);
			this.uniforms.add(uniform);
		}
		GL20.glValidateProgram(programID);
	}

	public void start() {
		GL20.glUseProgram(programID);
	}

	public void stop() {
		GL20.glUseProgram(0);
	}

	public void cleanUp() {
		stop();
		GL20.glDeleteProgram(programID);
	}

	private void bindAttributes(String[] inVariables){
		for(int i=0;i<inVariables.length;i++){
			GL20.glBindAttribLocation(programID, i, inVariables[i]);
		}
	}
	
	private int loadShader(ResourceFile file, int type) {
		StringBuilder shaderSource = new StringBuilder();
		try {
			BufferedReader reader = file.getReader();
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("//\n");
			}
			reader.close();
		} catch (Exception e) {
			System.err.println("Could not read file.");
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader "+ file);
			System.exit(-1);
		}
		return shaderID;
	}

	public List<Uniform> getUniforms() {
		return uniforms;
	}

}
