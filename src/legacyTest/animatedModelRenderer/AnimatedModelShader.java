package legacyTest.animatedModelRenderer;

import me.abitofevrything.world3d.rendering.shaders.ShaderProgram;
import me.abitofevrything.world3d.rendering.shaders.UniformMat4Array;
import me.abitofevrything.world3d.rendering.shaders.UniformMatrix;
import me.abitofevrything.world3d.rendering.shaders.UniformSampler;
import me.abitofevrything.world3d.rendering.shaders.UniformVec3;
import me.abitofevrything.world3d.util.ResourceFile;

public class AnimatedModelShader extends ShaderProgram {

	private static final int MAX_JOINTS = 50;// max number of joints in a skeleton
	private static final int DIFFUSE_TEX_UNIT = 0;

	private static final ResourceFile VERTEX_SHADER = new ResourceFile("legacyTest/animatedModelRenderer", "animatedEntityVertex.glsl");
	private static final ResourceFile FRAGMENT_SHADER = new ResourceFile("legacyTest/animatedModelRenderer", "animatedEntityFragment.glsl");

	protected UniformMatrix projectionViewMatrix = new UniformMatrix("projectionViewMatrix");
	protected UniformVec3 lightDirection = new UniformVec3("lightDirection");
	protected UniformMat4Array jointTransforms = new UniformMat4Array("jointTransforms", MAX_JOINTS);
	protected UniformMatrix transformationMatrix = new UniformMatrix("transformationMatrix");
	private UniformSampler diffuseMap = new UniformSampler("diffuseMap");

	/**
	 * Creates the shader program for the {@link AnimatedModelRenderer} by
	 * loading up the vertex and fragment shader code files. It also gets the
	 * location of all the specified uniform variables, and also indicates that
	 * the diffuse texture will be sampled from texture unit 0.
	 */
	public AnimatedModelShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER, "in_position", "in_textureCoords", "in_normal", "in_jointIndices",
				"in_weights");
		super.addUniforms(projectionViewMatrix, diffuseMap, lightDirection, jointTransforms, transformationMatrix);
		connectTextureUnits();
	}

	/**
	 * Indicates which texture unit the diffuse texture should be sampled from.
	 */
	private void connectTextureUnits() {
		super.start();
		diffuseMap.loadTexUnit(DIFFUSE_TEX_UNIT);
		super.stop();
	}

}
