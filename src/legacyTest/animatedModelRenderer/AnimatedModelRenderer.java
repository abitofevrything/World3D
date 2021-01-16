package legacyTest.animatedModelRenderer;

import me.abitofevrything.world3d.entity.Entity;
import me.abitofevrything.world3d.rendering.opengl.OpenGlUtils;
import me.abitofevrything.world3d.rendering.opengl.Vao;
import me.abitofevrything.world3d.textures.Texture;
import me.abitofevrything.world3d.util.Utils;
import me.abitofevrything.world3d.util.cameras.Camera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

/**
 * 
 * This class deals with rendering an animated entity. Nothing particularly new
 * here. The only exciting part is that the joint transforms get loaded up to
 * the shader in a uniform array.
 * 
 * @author Karl
 *
 */
public class AnimatedModelRenderer {

	private AnimatedModelShader shader;

	/**
	 * Initializes the shader program used for rendering animated models.
	 */
	public AnimatedModelRenderer() {
		this.shader = new AnimatedModelShader();
	}

	/**
	 * Renders an animated entity. The main thing to note here is that all the
	 * joint transforms are loaded up to the shader to a uniform array. Also 5
	 * attributes of the VAO are enabled before rendering, to include joint
	 * indices and weights.
	 * 
	 * @param entity
	 *            - the animated entity to be rendered.
	 * @param camera
	 *            - the camera used to render the entity.
	 * @param lightDir
	 *            - the direction of the light in the scene.
	 */
	public void render(List<Entity> entities, Camera camera, Vector3f lightDir) {
		Map<Vao, Map<Texture, List<Entity>>> sorted = new HashMap<Vao, Map<Texture, List<Entity>>>();
		for (Entity entity : entities) {
			if (sorted.get(entity.getModel().getMesh()) == null) {
				sorted.put(entity.getModel().getMesh(), new HashMap<Texture, List<Entity>>());
			}
			if (sorted.get(entity.getModel().getMesh()).get(entity.getModel().getTexture()) == null) {
				sorted.get(entity.getModel().getMesh()).put(entity.getModel().getTexture(), new ArrayList<Entity>());
			}
			sorted.get(entity.getModel().getMesh()).get(entity.getModel().getTexture()).add(entity);
		}
		render(sorted, camera, lightDir);
	}

	public void render(Map<Vao, Map<Texture, List<Entity>>> entities, Camera camera, Vector3f lightDir) {
		prepare(camera, lightDir);
		for (Vao vao : entities.keySet()) {
			vao.bind(0, 1, 2, 3, 4);
			Map<Texture, List<Entity>> meshTextures = entities.get(vao);
			for (Texture texture : meshTextures.keySet()) {
				texture.bindToUnit(0);
				List<Entity> textureEntities = meshTextures.get(texture);
				for (Entity entity : textureEntities) {
					shader.jointTransforms.loadMatrixArray(entity.getModel().getJointTransforms());
					shader.transformationMatrix.loadMatrix(Utils.createTransformationMatrix(entity));
					GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getMesh().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
				}
			}
			vao.unbind(0, 1, 2, 3, 4);
		}
		finish();
	}
	
	/**
	 * Deletes the shader program when the game closes.
	 */
	public void cleanUp() {
		shader.cleanUp();
	}

	/**
	 * Starts the shader program and loads up the projection view matrix, as
	 * well as the light direction. Enables and disables a few settings which
	 * should be pretty self-explanatory.
	 * 
	 * @param camera
	 *            - the camera being used.
	 * @param lightDir
	 *            - the direction of the light in the scene.
	 */
	private void prepare(Camera camera, Vector3f lightDir) {
		shader.start();
		shader.projectionViewMatrix.loadMatrix(camera.getProjectionViewMatrix());
		shader.lightDirection.loadVec3(lightDir);
		OpenGlUtils.antialias(true);
		OpenGlUtils.disableBlending();
		OpenGlUtils.enableDepthTesting(true);
	}

	/**
	 * Stops the shader program after rendering the entity.
	 */
	private void finish() {
		shader.stop();
	}

}
