package me.abitofevrything.world3d.rendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import me.abitofevrything.World3D;
import me.abitofevrything.world3d.entity.Entity;
import me.abitofevrything.world3d.events.EventSubscribable;
import me.abitofevrything.world3d.events.output.RenderEvent;
import me.abitofevrything.world3d.events.output.RenderEventListener;
import me.abitofevrything.world3d.rendering.opengl.Vao;
import me.abitofevrything.world3d.rendering.shaders.ShaderProgram;
import me.abitofevrything.world3d.textures.Texture;
import me.abitofevrything.world3d.util.cameras.Camera;

/**
 * Represents a renderer
 * 
 * @author abitofevrything
 *
 * @param <S> The type of {@link ShaderProgram} to use for this renderers
 */
public abstract class Renderer<S extends ShaderProgram> extends EventSubscribable {

	private S shader;
	
	private RenderTarget target;
	private String renderPool;
	
	/**
	 * Creates a renderer that will render a specific render pool to a specific render target
	 * 
	 * @param shader
	 * @param target
	 * @param pool
	 */
	public Renderer(S shader, RenderTarget target, String pool) {
		this.shader = shader;
		this.target = target;
		this.renderPool = pool;
	}
	
	/**
	 * Creates a renderer that will render to a specific render target
	 *
	 * @param shader
	 * @param target
	 */
	public Renderer(S shader, RenderTarget target) {
		this(shader, target, "");
	}
	
	/**
	 * Creates a renderer that will render a specific render pool to the screen
	 * 
	 * @param shader
	 * @param pool
	 */
	public Renderer(S shader, String pool) {
		this(shader, RenderTarget.SCREEN, pool);
	}
	
	/**
	 * Creates a renderer that will render to the screen
	 * 
	 * @param shader
	 */
	public Renderer(S shader) {
		this(shader, RenderTarget.SCREEN, "");
	}
	
	private void render(List<Entity> entities, Camera camera) {
		Map<Vao, Map<Texture, List<Entity>>> renderBatches = new HashMap<Vao, Map<Texture, List<Entity>>>();
		
		/* Sort the entities into batches */
		for (Entity e : entities) {
			Vao mesh = e.getModel().getMesh();
			
			Map<Texture, List<Entity>> entityMeshBatch;
			
			if ((entityMeshBatch = renderBatches.get(mesh)) == null) {
				entityMeshBatch = new HashMap<Texture, List<Entity>>();
				renderBatches.put(mesh, entityMeshBatch);
			}
			
			Texture texture = e.getModel().getTexture();
			
			List<Entity> entityTextureBatch;
			
			if ((entityTextureBatch = entityMeshBatch.get(texture)) == null) {
				entityTextureBatch = new ArrayList<Entity>();
				entityMeshBatch.put(texture, entityTextureBatch);
			}
			
			entityTextureBatch.add(e);
		}
		
		target.bindToRenderOutput();
		shader.start();
		render(renderBatches, camera);
		shader.stop();
		
		super.triggerEventForSubscribers(new RenderEvent("renderer"));
		target.finishRender();
	};
	
	/**
	 * Indicates that this renderer should automatically render its designated batch on every frame renders
	 * 
	 * @param camera The camera to use for rendering
	 */
	public void scheduleRenders(Camera camera) {
		new RenderEventListener() {
			
			@Override
			public void onEvent(RenderEvent event) {
				render(World3D.getRenderPool(renderPool), camera);
			}
		}.listen();
	}
	
	public abstract void render(Map<Vao, Map<Texture, List<Entity>>> entities, Camera camera);

	public S getShader() {
		return shader;
	}
	
	public void setTarget(RenderTarget target) {
		this.target = target;
	}
	
	/**
	 * Renders a mesh using the currently bound shader, textures and mesh
	 * 
	 * @param mesh The mesh to render. This should be bound with {@link Vao#bind(int...)} first
	 */
	protected static void renderMesh(Vao mesh) {
		GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
	}
}
