package legacyTest;

import me.abitofevrything.world3d.entity.Entity;
import me.abitofevrything.world3d.events.EventManager;
import me.abitofevrything.world3d.events.game.GameUpdateEvent;
import me.abitofevrything.world3d.util.cameras.Camera;
import me.abitofevrything.world3d.util.cameras.DefaultCamera;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

/**
 * Represents all the stuff in the scene (just the camera, light, and model
 * really).
 * 
 * @author Karl
 *
 */
public class Scene {

	/**
	 * The camera that this scene should use for rendering
	 */
	private static Camera camera = new DefaultCamera();
	
	private static final List<Entity> entities = new ArrayList<Entity>();

	private static Vector3f lightDirection = new Vector3f(0, -1, 0);

	public static List<Entity> getEntities() {
		return entities;
	}

	public static void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	public static void removeEntity(Entity entity) {
		entities.remove(entity);
	}
	
	/**
	 * Updates the game. Called automatically {@link Scene.TPS} times per second
	 */
	public static void update() {
		EventManager.triggerEvent(new GameUpdateEvent());
	}
	
	/**
	 * @return The direction of the light as a vector.
	 */
	public static Vector3f getLightDirection() {
		return lightDirection;
	}

	public static void setLightDirection(Vector3f lightDir) {
		Scene.lightDirection.set(lightDir);
	}
	
	public static void setCamera(Camera camera) {
		Scene.camera = camera;
	}
	
	public static Camera getCamera() {
		return camera;
	}
}

