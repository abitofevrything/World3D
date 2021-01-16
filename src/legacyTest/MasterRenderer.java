package legacyTest;

import me.abitofevrything.world3d.events.game.GameCloseEvent;
import me.abitofevrything.world3d.events.game.GameCloseEventListener;

import org.lwjgl.opengl.GL11;

import legacyTest.animatedModelRenderer.AnimatedModelRenderer;
import legacyTest.skyboxRenderer.SkyboxRenderer;

/**
 * This class is in charge of rendering everything in the scene to the screen.
 * @author Karl
 *
 */
public class MasterRenderer {

	private SkyboxRenderer skyRenderer;
	private AnimatedModelRenderer entityRenderer;

	protected MasterRenderer(AnimatedModelRenderer renderer, SkyboxRenderer skyRenderer) {
		this.skyRenderer = skyRenderer;
		this.entityRenderer = renderer;
		
		new GameCloseEventListener() {
			
			@Override
			public void onEvent(GameCloseEvent event) {
				cleanUp();
			}
		}.listen();
	}

	/**
	 * Renders the scene to the screen.
	 * @param scene
	 */
	protected void renderScene() {
		prepare();
		entityRenderer.render(Scene.getEntities(), Scene.getCamera(), Scene.getLightDirection());
		skyRenderer.render(Scene.getCamera());
	}

	/**
	 * Clean up when the game is closed.
	 */
	protected void cleanUp() {
		skyRenderer.cleanUp();
		entityRenderer.cleanUp();
	}

	/**
	 * Prepare to render the current frame by clearing the framebuffer.
	 */
	private void prepare() {
		GL11.glClearColor(1, 1, 1, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}


}
