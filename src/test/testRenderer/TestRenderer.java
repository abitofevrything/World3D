package test.testRenderer;

import java.util.List;
import java.util.Map;

import me.abitofevrything.world3d.entity.Entity;
import me.abitofevrything.world3d.rendering.RenderTarget;
import me.abitofevrything.world3d.rendering.Renderer;
import me.abitofevrything.world3d.rendering.opengl.OpenGlUtils;
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
		OpenGlUtils.enableDepthTesting(true);
		
		shader.projectionViewMatrix.loadMatrix(camera.getProjectionViewMatrix());
		
		for (Vao vao : entities.keySet()) {
			vao.bind(0, 1);
			
			Map<Texture, List<Entity>> modelBatch = entities.get(vao);
			for (Texture texture : modelBatch.keySet()) {
				shader.textureSampler.loadAndBindTexture(texture, 0);
				
				List<Entity> textureBatch = modelBatch.get(texture);
				for (Entity entity : textureBatch) {
					shader.transformationMatrix.loadMatrix(entity.getTransformationMatrix());
					
					super.renderMesh(vao);
				}
			}
			vao.unbind(0, 1);
		}
	}

}
