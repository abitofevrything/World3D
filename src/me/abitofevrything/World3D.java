package me.abitofevrything;

import me.abitofevrything.world3d.animation.Animation;
import me.abitofevrything.world3d.audio.AudioListener;
import me.abitofevrything.world3d.entity.Entity;
import me.abitofevrything.world3d.events.EventManager;
import me.abitofevrything.world3d.events.game.GameUpdateEvent;
import me.abitofevrything.world3d.events.input.Input;
import me.abitofevrything.world3d.events.output.RenderEvent;
import me.abitofevrything.world3d.loaders.AnimatedModelLoader;
import me.abitofevrything.world3d.loaders.AnimationLoader;
import me.abitofevrything.world3d.models.animatedModel.AnimatedModel;
import me.abitofevrything.world3d.rendering.RenderTarget;
import me.abitofevrything.world3d.util.Display;
import me.abitofevrything.world3d.util.ResourceFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.lwjgl.opengl.GL11;

/**
 * The main class for interacting with the engine.
 * Handles updates, entities and display management
 * 
 * @author abitofevrything
 *
 */
public class World3D {

	private static Map<String, List<Entity>> entities = new HashMap<String, List<Entity>>();
	
	/**
	 * Initializes the engine
	 * 
	 * @param title - The title of the window
	 * @param displayWidth - The width of the window. Set to 0 in fullscreen mode to use default width
	 * @param displayHeight - The height of the window. Set to 0 in fullscreen mode to use default height
	 * @param fullscreen - Whever the display should be fullscreen or not
	 */
	public static void init(String title, int displayWidth, int displayHeight, boolean fullscreen) {
		if (fullscreen) {
			Display.createFullscreen(title, displayWidth, displayHeight);
		} else {
			Display.create(title, displayWidth, displayHeight);
		}
		Input.init();
		AudioListener.init();
		
		entities.put("", new ArrayList<Entity>());
	}
	
	/**
	 * Terminates the engine
	 */
	public static void exit() {
		Display.close();
	}
	
	/**
	 * Adds an entity to the default render pool
	 * 
	 * @param e
	 * - The {@link Entity} to add
	 */
	public static void addEntity(Entity e) {
		Objects.requireNonNull(e);
		entities.get("").add(e);
	}
	
	/**
	 * Adds an entity to a specific render pool
	 * 
	 * @param key
	 * - The name of the render pool to add the entity to </br>
	 *   If {@code key.equals("")} then the entity will be added to the default render pool
	 * @param e
	 * - The {@link Entity} to add
	 * @throws NullPointerException if {@code e} is null
	 */
	public static void addEntity(String key, Entity e) {
		Objects.requireNonNull(e);
		List<Entity> keySet;
		if ((keySet = entities.get(key)) == null) {
			keySet = new ArrayList<Entity>();
			entities.put(key, keySet);
		}
		keySet.add(e);
	}
	
	/**
	 * Removes all occurences of an entity in the scene
	 * 
	 * @param e
	 */
	public static void removeEntity(Entity e) {
		for (String s : entities.keySet()) {
			List<Entity> l = entities.get(s);
			if (l.contains(e)) {
				l.remove(e);
			}
		}
	}
	
	/**
	 * Removes all entities
	 */
	public static void clearEntities() {
		entities = new HashMap<String, List<Entity>>();
		entities.put("", new ArrayList<Entity>());
	}
	
	/**
	 * Gets all of the entities in a render pool
	 * 
	 * @param key
	 * - The name of the render pool to get the entities from </br>
	 * 	 If {@code key.equals("")} then the default render pool  will be returned
	 * @return
	 * - A {@code List<Entity>} of all the entities in the specified render pool
	 */
	public static List<Entity> getRenderPool(String key) {
		List<Entity> pool;
		return (pool = entities.get(key)) == null ? new ArrayList<Entity>() : pool;
	}
	
	/**
	 * Loads a model and applies an animation to it
	 * 
	 * @param model The COLLADA file to load the mesh data from
	 * @param texture The PNG file to load the texture from
	 * @param animation The COLLADA file to load the animation from. If null, no animation will be applied
	 * @return The loaded model
	 */
	public static AnimatedModel loadAnimatedModel(ResourceFile model, ResourceFile texture, ResourceFile animation) {
		AnimatedModel loadedModel = AnimatedModelLoader.loadEntity(model, texture);
		if (animation != null) {
			Animation loadedAnimation = AnimationLoader.loadAnimation(animation);
			loadedModel.doAnimation(loadedAnimation);
		}
		return loadedModel;
	}
	
	/**
	 * Updates the engine, triggering an {@link RenderEvent} and exiting if necessary.
	 * Renderers that want to render this frame should subscribe to that event.
	 * 
	 * 
	 */
	public static void update() {
		EventManager.triggerEvent(new GameUpdateEvent());
		
		RenderTarget.SCREEN.bindToRenderOutput();
		
		GL11.glClearColor(0, 0, 1, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		EventManager.triggerEvent(new RenderEvent(RenderEvent.FRAME_RENDER));
		
		Display.update();
		
		if (Display.isCloseRequested()) {
			exit();
			System.exit(0);
		}
	}
}
