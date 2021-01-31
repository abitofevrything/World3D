package me.abitofevrything.world3d.util.cameras;

import me.abitofevrything.world3d.events.input.Input;
import me.abitofevrything.world3d.events.input.KeyHeldEvent;
import me.abitofevrything.world3d.events.input.KeyHeldEventListener;
import me.abitofevrything.world3d.events.input.MouseMovedEvent;
import me.abitofevrything.world3d.events.input.MouseMovedEventListener;
import me.abitofevrything.world3d.events.input.MouseScrolledEvent;
import me.abitofevrything.world3d.events.input.MouseScrolledEventListener;
import me.abitofevrything.world3d.util.Display;
import me.abitofevrything.world3d.util.SmoothFloat;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

/**
 * A smooth, fully user-controlled {@link Camera}
 * 
 * Controls are : 
 *  - Move mouse : Rotate Camera
 *  - Press W / S : Move fowards / backwards
 *  - Press A / D : Move left / right
 *  - Press Space / Shift : Move up / down
 *  
 *    
 * @author abitofevrything
 *
 */
public class SmoothFreeCamera extends Camera {
	
	private static final float PITCH_SENSITIVITY = 0.3f;
	private static final float YAW_SENSITIVITY = 0.3f;

	private float speed = 10;
	private float yaw = 0, pitch = 0;
	private SmoothFloat x, y, z;
	
	/**
	 * Creates a {@link SmoothFreeCamera}
	 * 
	 * @param pos The original position
	 */
	public SmoothFreeCamera(Vector3f pos) {
		this.x = new SmoothFloat(pos.x, 10);
		this.y = new SmoothFloat(pos.y, 10);
		this.z = new SmoothFloat(pos.z, 10);
		
		new MouseMovedEventListener() {
			@Override
			public void onEvent(MouseMovedEvent event) {
				pitch -= event.getDY() * PITCH_SENSITIVITY;
				if (pitch < -90) {
					pitch = -90;
				} else if (pitch > 90) {
					pitch = 90;
				}
				
				yaw += event.getDX() * YAW_SENSITIVITY;
				yaw %= 360;
			}
		}.listen();
		
		new KeyHeldEventListener() {
			
			@Override
			public void onEvent(KeyHeldEvent event) {
				
				if (event.getKey() == GLFW_KEY_W) {
					x.increaseTarget((float)Math.sin(Math.toRadians(yaw)) * speed * Display.getFrameTime());
					z.increaseTarget((float)-Math.cos(Math.toRadians(yaw)) * speed * Display.getFrameTime());
				} else if (event.getKey() == GLFW_KEY_S) {
					x.increaseTarget((float)-Math.sin(Math.toRadians(yaw)) * speed * Display.getFrameTime());
					z.increaseTarget((float)Math.cos(Math.toRadians(yaw)) * speed * Display.getFrameTime());
				} else if (event.getKey() == GLFW_KEY_SPACE) {
					y.increaseTarget(speed * Display.getFrameTime());
				} else if (event.getKey() == GLFW_KEY_LEFT_SHIFT) {
					y.increaseTarget(-speed * Display.getFrameTime());
				} else if (event.getKey() == GLFW_KEY_D) {
					x.increaseTarget((float)Math.cos(Math.toRadians(yaw)) * speed * Display.getFrameTime());
					z.increaseTarget((float)Math.sin(Math.toRadians(yaw)) * speed * Display.getFrameTime());
				} else if (event.getKey() == GLFW_KEY_A) {
					x.increaseTarget((float)-Math.cos(Math.toRadians(yaw)) * speed * Display.getFrameTime());
					z.increaseTarget((float)-Math.sin(Math.toRadians(yaw)) * speed * Display.getFrameTime());
				}
			}
		}.listen();
		
		new MouseScrolledEventListener() {
			
			@Override
			public void onEvent(MouseScrolledEvent event) {
				speed += (speed * event.getDWheel()) / 1000;
			}
		}.listen();
	}
	
	/**
	 * Creates a {@link SmoothFreeCamera} at the origin
	 */
	public SmoothFreeCamera() {
		this(new Vector3f(0, 0, 0));
	}
	
	@Override
	public Vector3f getPosition() {
		return new Vector3f(x.get(), y.get(), z.get());
	}

	@Override
	public float getYaw() {
		return yaw;
	}

	@Override
	public float getRoll() {
		return 0;
	}

	@Override
	public void update() {
		Input.setMouseGrabbed(true);
		
		x.update(Display.getFrameTime());
		y.update(Display.getFrameTime());
		z.update(Display.getFrameTime());
	}

	@Override
	public float getPitch() {
		return pitch;
	}

}
