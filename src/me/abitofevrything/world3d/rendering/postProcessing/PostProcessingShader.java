package me.abitofevrything.world3d.rendering.postProcessing;

import me.abitofevrything.world3d.rendering.shaders.ShaderProgram;
import me.abitofevrything.world3d.rendering.shaders.UniformSampler;
import me.abitofevrything.world3d.util.ResourceFile;

/**
 * A shader for post processing effects
 * 
 * @author abitofevrything
 *
 */
public class PostProcessingShader extends ShaderProgram {

	public UniformSampler colourInput = new UniformSampler("colourInput");
	public UniformSampler depthInput = new UniformSampler("depthInput");
	
	public PostProcessingShader(ResourceFile fragmentFile) {
		super(new ResourceFile("me/abitofevrything/world3d/rendering/postProcessing/postProcessingVertex.glsl"), fragmentFile, "position");
		super.addUniformsNoWarn(colourInput, depthInput);
	}

	public boolean hasDepthInput() {
		return depthInput.getLocation() != -1;
	}
	
	public boolean hasColourInput() {
		return colourInput.getLocation() != -1;
	}
}
