package test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

import org.lwjgl.util.vector.Vector3f;

import me.abitofevrything.World3D;
import me.abitofevrything.world3d.entity.Entity;
import me.abitofevrything.world3d.models.animatedModel.AnimatedModel;
import me.abitofevrything.world3d.rendering.RenderTarget;
import me.abitofevrything.world3d.rendering.postProcessing.PostProcessingEffect;
import me.abitofevrything.world3d.rendering.postProcessing.PostProcessingProgram;
import me.abitofevrything.world3d.textures.Texture;
import me.abitofevrything.world3d.util.CubeGenerator;
import me.abitofevrything.world3d.util.cameras.DefaultCamera;
import test.testRenderer.TestRenderer;

public class Main {
	
	public static void main(String[] args) {
		
		File f = new File("test.log");
		PrintStream file = null;
		try {
			if (!f.exists()) f.createNewFile();
			file = new PrintStream(f);
		} catch (IOException e) {
			System.err.println("Unable to generate log file");
			e.printStackTrace();
			System.exit(-1);
		}
		
		try {
		
			System.out.println("Testing engine startup and shutdown times");
			log("Starting test...", file);
			
			long testStartTime = System.currentTimeMillis();
			
			/*             ENGINE OPEN / CLOSE                     */
			
			long coldInitStartTime = System.currentTimeMillis();
			World3D.init("World3D test", 0, 0, false);
			long coldInitTime = System.currentTimeMillis() - coldInitStartTime;
			
			System.out.println("Cold init time : " + coldInitTime + "ms");
			log("Cold init time : " + coldInitTime + "ms", file);
			
			long coldExitStartTime = System.currentTimeMillis();
			World3D.exit();
			long coldExitTime = System.currentTimeMillis() - coldExitStartTime;
			
			System.out.println("Cold exit time : " + coldExitTime + "ms");
			log("Cold exit time : " + coldExitTime + "ms", file);
			
			//Engine init / exit times (normal operation)
			double avgOpenTime = 0, avgCloseTime = 0;
			for (int i = 0; i < 20; i++) {
				long startOpenTime = System.currentTimeMillis();
				World3D.init("World3D test", 0, 0, false);
				avgOpenTime = ((i * avgOpenTime) + (System.currentTimeMillis() - startOpenTime)) / (i + 1);
				
				long startCloseTime = System.currentTimeMillis();
				World3D.exit();
				avgCloseTime = ((i * avgCloseTime) + (System.currentTimeMillis() - startCloseTime)) / (i + 1);
			}
	
			System.out.println("Average init time : " + avgOpenTime + "ms");
			log("Average init time : " + avgOpenTime + "ms", file);
			System.out.println("Average exit time : " + avgCloseTime + "ms");
			log("Average exit time : " + avgCloseTime + "ms", file);
			
			//Engine init / exit time (fullscreen mode)
			double avgFullscreenOpenTime = 0, avgFullscreenCloseTime = 0;
			for (int i = 0; i < 20; i++) {
				long startOpenTime = System.currentTimeMillis();
				World3D.init("World3D test", 0, 0, true);
				avgFullscreenOpenTime = ((i * avgFullscreenOpenTime) + (System.currentTimeMillis() - startOpenTime)) / (i + 1);
				
				long startCloseTime = System.currentTimeMillis();
				World3D.exit();
				avgFullscreenCloseTime = ((i * avgFullscreenCloseTime) + (System.currentTimeMillis() - startCloseTime)) / (i + 1);
			}
	
			System.out.println("Average init time (fullscreen) : " + avgFullscreenOpenTime + "ms");
			log("Average init time (fullscreen) : " + avgFullscreenOpenTime + "ms", file);
			System.out.println("Average exit time (fullscreen) : " + avgFullscreenCloseTime + "ms");
			log("Average exit time (fullscreen) : " + avgFullscreenCloseTime + "ms", file);
			
			System.out.println("\n");
			file.println();
			
			/*                 BASIC RENDERING                       */
			
			System.out.println("Testing rendering capabilities. This may take some time.\nYou can minimize the window during this test, however your PCs performance will be affected.");
			
			World3D.init("World3D test", 1280, 720, false);
			
			TestRenderer renderer = new TestRenderer(RenderTarget.SCREEN);
			renderer.scheduleRenders(new DefaultCamera());
			
			AnimatedModel cube = new AnimatedModel(CubeGenerator.generateCube(1), Texture.newTexture("test.png").anisotropic().create());
			
			double prevFPS = 1000;
			
			for (int numCubes = 1; numCubes < 1000000; numCubes *= 5) {
				for (int j = World3D.getRenderPool("").size(); j < numCubes; j++) {
					World3D.addEntity(new Entity(cube, new Vector3f((float)Math.random() * 200 - 100, (float)Math.random() * 200 - 100, (float)Math.random() * 200 - 100)));
				}
				
				int currFrames = (int) (10 * prevFPS);
				
				long startTime = System.currentTimeMillis();
				for (int j = 0; j <= currFrames; j++) {
					World3D.update();
					System.out.print("\rEvaluating average FPS... (" + numCubes + " entities, " + (numCubes * 8) + " vertices, " + (numCubes * 12) + " triangles) : " + (int)(j / (double)currFrames * 100) + "% (frame " + j + " / " + currFrames + ")");
				}
				long diff = System.currentTimeMillis() - startTime;
				double seconds = diff / 1000;
				double framesPerSecond = currFrames / seconds;
				
				prevFPS = framesPerSecond;
				
				System.out.println("\nAverage FPS (" + numCubes + " entities, " + (numCubes * 8) + " vertices, " + (numCubes * 12) + " triangles) : " + framesPerSecond);
				log("Average FPS (" + numCubes + " entities, " + (numCubes * 8) + " vertices, " + (numCubes * 12) + " triangles) : " + framesPerSecond, file);
			}
			
			World3D.clearEntities();
			
			System.out.println("\n");
			file.println();
			
			/*               POST PROCSSING                   */
			
			for (int j = 0; j < 100; j++) {
				World3D.addEntity(new Entity(cube, new Vector3f((float)Math.random() * 200 - 100, (float)Math.random() * 200 - 100, (float)Math.random() * 200 - 100)));
			}
			
			PostProcessingProgram postProcessing = new PostProcessingProgram(new RenderTarget(), RenderTarget.SCREEN);
			renderer.setTarget(postProcessing.getInput());
			
			PostProcessingEffect effect = new PostProcessingEffect("test/testPostProcessing/testPostProcessing.glsl");
			postProcessing.addEffect(effect);
			
			prevFPS = 1000;
			
			double avgLostFramesPerLayer = 0;
			
			for (int i = 0; i < 50; i++) {
				long startTime = System.currentTimeMillis();
				
				int currFrames = (int) (10 * prevFPS);
				
				for (int j = 0; j <= currFrames; j++) {
					World3D.update();
					System.out.print("\rEvaluating average FPS... (" + (i+1) + " post processing layers) : " + (int)(j / (double)currFrames * 100) + "% (frame " + j + " / " + currFrames + ")");
				}
				long diff = System.currentTimeMillis() - startTime;
				double seconds = diff / 1000;
				double framesPerSecond = currFrames / seconds;
				
				System.out.println("\nAverage FPS (" + (i+1) + " post processing layers) : " + framesPerSecond);
				log("Average FPS (" + (i+1) + " post processing layers) : " + framesPerSecond, file);
				
				if (i != 0) {
					avgLostFramesPerLayer = ((avgLostFramesPerLayer * (i-1)) + (prevFPS - framesPerSecond)) / i;
				}
				
				prevFPS = framesPerSecond;
				
				postProcessing.addEffect(effect);
			}
			
			System.out.println("Average frames lost per layer : " + avgLostFramesPerLayer);
			log("Average frames lost per layer : " + avgLostFramesPerLayer, file);
			
			World3D.exit();
			
			long testDurationMillis = System.currentTimeMillis() - testStartTime;
			double testDurationSeconds = (double)testDurationMillis / 1000;
			
			System.out.println();
			System.out.println("Test duration : " + testDurationMillis + "ms (" + (int)Math.floor(testDurationSeconds / 60) + " minutes, " + (testDurationSeconds % 60) + " seconds)");
			file.println();
			log("Test duration : " + testDurationMillis + "ms (" + (int)Math.floor(testDurationSeconds / 60) + " minutes, " + (testDurationSeconds % 60) + " seconds)", file);
			
		} catch (Exception e) {
			file.println("Exception occurred : ");
			e.printStackTrace(file);
			
			System.out.println("Exception occurred : ");
			e.printStackTrace();
		} finally {
			file.close();
		}
	}
	
	private static void log(String s, PrintStream stream) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.setTimeZone(new SimpleTimeZone(0, "GMT"));
		sdf.applyPattern("dd MMM yyyy HH:mm:ss z");
		stream.println("[" + sdf.format(new Date()) + "] " + s);
	}

}
