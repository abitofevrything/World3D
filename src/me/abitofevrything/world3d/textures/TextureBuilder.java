package me.abitofevrything.world3d.textures;

import me.abitofevrything.world3d.util.ResourceFile;

public class TextureBuilder {
	
	private boolean clampEdges = false;
	private boolean mipmap = false;
	private boolean anisotropic = true;
	private boolean nearest = false;
	
	private ResourceFile file;
	
	protected TextureBuilder(ResourceFile textureFile){
		this.file = textureFile;
	}
	
	public Texture create(){
		TextureData textureData = TextureUtils.decodeTextureFile(file);
		int textureId = TextureUtils.loadTextureToOpenGL(textureData, this);
		return new Texture(textureId, textureData.getWidth(), textureData.getHeight());
	}
	
	public TextureBuilder clampEdges(){
		this.clampEdges = true;
		return this;
	}
	
	public TextureBuilder normalMipMap(){
		this.mipmap = true;
		this.anisotropic = false;
		return this;
	}
	
	public TextureBuilder nearestFiltering(){
		this.mipmap = false;
		this.anisotropic = false;
		this.nearest = true;
		return this;
	}
	
	public TextureBuilder anisotropic(){
		this.mipmap = true;
		this.anisotropic = true;
		return this;
	}
	
	protected boolean isClampEdges() {
		return clampEdges;
	}

	protected boolean isMipmap() {
		return mipmap;
	}

	protected boolean isAnisotropic() {
		return anisotropic;
	}

	protected boolean isNearest() {
		return nearest;
	}

}
