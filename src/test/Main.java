package test;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import me.abitofevrything.World3D;
import me.abitofevrything.world3d.entity.Entity;
import me.abitofevrything.world3d.models.animatedModel.AnimatedModel;
import me.abitofevrything.world3d.rendering.RenderTarget;
import me.abitofevrything.world3d.rendering.postProcessing.PostProcessingEffect;
import me.abitofevrything.world3d.rendering.postProcessing.PostProcessingProgram;
import me.abitofevrything.world3d.textures.Texture;
import me.abitofevrything.world3d.util.CubeGenerator;
import me.abitofevrything.world3d.util.ResourceFile;
import me.abitofevrything.world3d.util.cameras.EntityOrbitCamera;
import test.testRenderer.TestRenderer;

public class Main {

	public static void main(String[] args) {
		
		World3D.init("World3D test");
		
		AnimatedModel model = new AnimatedModel(CubeGenerator.generateCube(1), Texture.newTexture(new ResourceFile("diffuse.png")).anisotropic().create());

		Entity cube = new Entity(model, new Vector3f(0, 0, 0), 0, 0, 0, 1);
		
		World3D.addEntity(cube);
		
		PostProcessingProgram postProcessing = new PostProcessingProgram(new RenderTarget(), RenderTarget.SCREEN);
		
		PostProcessingEffect testPostProcessingEffect = new PostProcessingEffect("test/testPostProcessing/testPostProcessing.glsl");
		postProcessing.addEffect(testPostProcessingEffect);
		
		TestRenderer renderer = new TestRenderer(postProcessing.getInput());
		
		renderer.scheduleRenders(new EntityOrbitCamera(cube, 10));
		
//		RenderTarget inter = new RenderTarget();
//		
//		TestRenderer renderer = new TestRenderer(inter);
//		renderer.scheduleRenders(new EntityOrbitCamera(cube, 10));
//		
//		new PostProcessingEffect("test/testPostProcessing/testPostProcessing.glsl", inter, RenderTarget.SCREEN);
		
		while (!Display.isCloseRequested()) {
			World3D.update();
		}
		
		World3D.exit();
		
	}

}
