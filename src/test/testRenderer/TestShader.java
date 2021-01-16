package test.testRenderer;

import me.abitofevrything.world3d.rendering.shaders.ShaderProgram;
import me.abitofevrything.world3d.rendering.shaders.UniformMatrix;
import me.abitofevrything.world3d.rendering.shaders.UniformSampler;
import me.abitofevrything.world3d.util.ResourceFile;

public class TestShader extends ShaderProgram {

	public UniformMatrix projectionViewMatrix = new UniformMatrix("projectionViewMatrix"), transformationMatrix = new UniformMatrix("transformationMatrix");
	public UniformSampler textureSampler = new UniformSampler("textureSampler");
	
	public TestShader() {
		super(new ResourceFile("test/testRenderer/testVertex.glsl"), new ResourceFile("test/testRenderer/testFragment.glsl"), "position", "texCoords");
		super.addUniforms(projectionViewMatrix, transformationMatrix, textureSampler);
	}
	
}
