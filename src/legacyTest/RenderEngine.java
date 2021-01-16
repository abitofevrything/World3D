package legacyTest;

import legacyTest.animatedModelRenderer.AnimatedModelRenderer;
import legacyTest.skyboxRenderer.SkyboxRenderer;
import me.abitofevrything.world3d.events.EventManager;
import me.abitofevrything.world3d.events.output.RenderEvent;
import me.abitofevrything.world3d.util.DisplayManager;

/**
 * This class represents the entire render engine.
 * 
 * @author Karl
 *
 */
public class RenderEngine {

	private MasterRenderer renderer;

	private RenderEngine(MasterRenderer renderer) {
		this.renderer = renderer;
	}

	/**
	 * Updates the display.
	 */
	public void update() {
		DisplayManager.update();
	}

	/**
	 * Renders the scene to the screen.
	 * 
	 * @param scene
	 *            - the game scene.
	 */
	public void renderScene() {
		renderer.renderScene();
		EventManager.triggerEvent(new RenderEvent("frameRender"));
		update();
	}

	/**
	 * Cleans up the renderers and closes the display.
	 */
	public void close() {
		DisplayManager.closeDisplay();
	}

	/**
	 * Initializes a new render engine. Creates the display and initializes the
	 * renderers.
	 * 
	 * @return
	 */
	public static RenderEngine init() {
		SkyboxRenderer skyRenderer = new SkyboxRenderer();
		AnimatedModelRenderer entityRenderer = new AnimatedModelRenderer();
		MasterRenderer renderer = new MasterRenderer(entityRenderer, skyRenderer);
		return new RenderEngine(renderer);
	}

}
