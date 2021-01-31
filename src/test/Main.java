package test;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.util.vector.Vector3f;

import me.abitofevrything.World3D;
import me.abitofevrything.world3d.audio.Sound;
import me.abitofevrything.world3d.entity.Entity;
import me.abitofevrything.world3d.events.input.KeyPressedEvent;
import me.abitofevrything.world3d.events.input.KeyPressedEventListener;
import me.abitofevrything.world3d.models.animatedModel.AnimatedModel;
import me.abitofevrything.world3d.rendering.RenderTarget;
import me.abitofevrything.world3d.textures.Texture;
import me.abitofevrything.world3d.util.CubeGenerator;
import me.abitofevrything.world3d.util.cameras.Camera;
import me.abitofevrything.world3d.util.cameras.SmoothFreeCamera;
import test.testRenderer.TestRenderer;

public class Main {
	
	public static void main(String[] args) {
		
		World3D.init("World3D", 0, 0, true);
		
		Entity e = new Entity(new AnimatedModel(CubeGenerator.generateCube(1), Texture.newTexture("test.png").anisotropic().create()), new Vector3f(0, 0, 0));
		World3D.addEntity(e);
		
		Sound s = new Sound("test.wav");
		
		Camera camera = new SmoothFreeCamera();
		camera.bind();
		
		new KeyPressedEventListener() {
			
			@Override
			public void onEvent(KeyPressedEvent event) {
				if (event.getKey() == GLFW_KEY_P) {
					e.playSound(s);
				}
			}
			
		}.listen();
		
		new TestRenderer(RenderTarget.SCREEN).scheduleRenders(camera);
		
		while (true) {
			World3D.update();
		}
		
	}

}