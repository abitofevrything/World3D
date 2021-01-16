package me.abitofevrything.world3d.rendering.postProcessing;

import me.abitofevrything.world3d.rendering.shaders.ShaderProgram;
import me.abitofevrything.world3d.rendering.shaders.UniformSampler;
import me.abitofevrything.world3d.util.ResourceFile;

public class PostProcessingShader extends ShaderProgram {

	public UniformSampler colourInput = new UniformSampler("colourInput");
	public UniformSampler depthInput = new UniformSampler("depthInput");
	
	public PostProcessingShader(ResourceFile fragmentFile) {
		super(new ResourceFile("me/abitofevrything/world3d/rendering/postProcessing/postProcessingVertex.glsl"), fragmentFile, "position");
		super.addUniformsNoWarn(colourInput, depthInput);
		
		if (colourInput.getLocation() == -1) {
			System.err.println("Post processing effect inputs should be in the form of a sampler2D named \"colourInput\" (optional)");
		}
		
		if (depthInput.getLocation() == -1) {
			System.err.println("Post processing effect depth inputs should be in the form of a sampler2D named \"depthInput\" (optional)");
		}
	}

	public boolean hasDepthInput() {
		return depthInput.getLocation() != -1;
	}
	
	public boolean hasColourInput() {
		return colourInput.getLocation() != -1;
	}
}
