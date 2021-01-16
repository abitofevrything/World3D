package legacyTest;

import org.lwjgl.opengl.Display;

import me.abitofevrything.World3D;

public class Main {

	public static void main(String[] args) {

		World3D.init("World3D");
		
		RenderEngine engine = RenderEngine.init();
		
		SceneLoader.loadScene();
		
		while (!Display.isCloseRequested()) {
			engine.renderScene();
		}

		engine.close();

	}

}