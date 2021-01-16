package test.testRenderer;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import me.abitofevrything.world3d.entity.Entity;
import me.abitofevrything.world3d.rendering.RenderTarget;
import me.abitofevrything.world3d.rendering.Renderer;
import me.abitofevrything.world3d.rendering.opengl.Vao;
import me.abitofevrything.world3d.textures.Texture;
import me.abitofevrything.world3d.util.cameras.Camera;

public class TestRenderer extends Renderer<TestShader> {

	private TestShader shader;
	
	public TestRenderer(RenderTarget output) {
		super(new TestShader(), output);
		
		shader = super.getShader();
	}
	
	@Override
	public void render(Map<Vao, Map<Texture, List<Entity>>> entities, Camera camera) {
		shader.projectionViewMatrix.loadMatrix(camera.getProjectionViewMatrix());
		
		for (Vao vao : entities.keySet()) {
			vao.bind(0, 1);
			
			Map<Texture, List<Entity>> modelBatch = entities.get(vao);
			for (Texture texture : modelBatch.keySet()) {
				shader.textureSampler.loadAndBindTexture(texture, 0);
				
				List<Entity> textureBatch = modelBatch.get(texture);
				for (Entity entity : textureBatch) {
					shader.transformationMatrix.loadMatrix(entity.getTransformationMatrix());
					
					GL11.glDrawElements(GL11.GL_TRIANGLES, vao.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
				}
			}
			vao.unbind(0, 1);
		}
	}

}
