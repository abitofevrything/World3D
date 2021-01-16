package legacyTest;

import me.abitofevrything.world3d.animation.Animation;
import me.abitofevrything.world3d.entity.Entity;
import me.abitofevrything.world3d.loaders.AnimatedModelLoader;
import me.abitofevrything.world3d.loaders.AnimationLoader;
import me.abitofevrything.world3d.util.ResourceFile;
import me.abitofevrything.world3d.util.cameras.Camera;
import me.abitofevrything.world3d.util.cameras.SmoothEntityOrbitCamera;

import org.lwjgl.util.vector.Vector3f;

public class SceneLoader {

	/**
	 * Sets up the scene. Loads the entity, load the animation, tells the entity
	 * to do the animation, sets the light direction, creates the camera, etc...
	 * 
	 * @param resFolder
	 *            - the folder containing all the information about the animated entity
	 *            (mesh, animation, and texture info).
	 * @return The entire scene.
	 */
	public static void loadScene() {
		Entity entity = new Entity(AnimatedModelLoader.loadEntity(new ResourceFile(GeneralSettings.MODEL_FILE),	new ResourceFile(GeneralSettings.DIFFUSE_FILE)), new Vector3f(0, 0, 0), 0, 0, 0, 1);
		Animation animation = AnimationLoader.loadAnimation(new ResourceFile(GeneralSettings.ANIM_FILE));
		Camera camera = new SmoothEntityOrbitCamera(entity, 10);
		entity.getModel().doAnimation(animation);
		Scene.addEntity(entity);
		Scene.setCamera(camera);
		Scene.setLightDirection(GeneralSettings.LIGHT_DIR);
	}

}
