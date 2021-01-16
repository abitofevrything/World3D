package legacyTest.skyboxRenderer;

import me.abitofevrything.world3d.rendering.shaders.ShaderProgram;
import me.abitofevrything.world3d.rendering.shaders.UniformMatrix;
import me.abitofevrything.world3d.util.ResourceFile;

public class SkyboxShader extends ShaderProgram {

	private static final ResourceFile VERTEX_SHADER = new ResourceFile("legacyTest/skyboxRenderer", "skyboxVertex.glsl");
	private static final ResourceFile FRAGMENT_SHADER = new ResourceFile("legacyTest/skyboxRenderer", "skyboxFragment.glsl");

	protected UniformMatrix projectionViewMatrix = new UniformMatrix("projectionViewMatrix");

	public SkyboxShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER, "in_position");
		super.addUniforms(projectionViewMatrix);
	}
}
