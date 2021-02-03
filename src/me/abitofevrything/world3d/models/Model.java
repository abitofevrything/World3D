package me.abitofevrything.world3d.models;

import me.abitofevrything.world3d.rendering.opengl.Vao;
import me.abitofevrything.world3d.textures.Texture;

public class Model {

	private Vao mesh;
	private Texture texture;
	
	public Model(Vao mesh, Texture texture) {
		this.mesh = mesh;
		this.texture = texture;
	}
	
	public Vao getMesh() {
		return mesh;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public void delete() {
		mesh.delete();
		texture.delete();
	}
	
}
