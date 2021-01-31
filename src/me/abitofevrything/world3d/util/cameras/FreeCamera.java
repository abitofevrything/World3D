package me.abitofevrything.world3d.util.cameras;

import me.abitofevrything.world3d.events.input.Input;
import me.abitofevrything.world3d.events.input.KeyHeldEvent;
import me.abitofevrything.world3d.events.input.KeyHeldEventListener;
import me.abitofevrything.world3d.events.input.MouseMovedEvent;
import me.abitofevrything.world3d.events.input.MouseMovedEventListener;
import me.abitofevrything.world3d.events.input.MouseScrolledEvent;
import me.abitofevrything.world3d.events.input.MouseScrolledEventListener;

import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

/**
 * An fully user-controlled {@link Camera}
 * 
 * Controls are :
 *  - Move mouse : Rotate camera
 *  - Press W / S : Move fowards / backwards
 *  - Press A / D : Move left / right
 *  - Press Shift / Space : Move up / down
 * 
 * @author abitofevrything
 *
 */
public class FreeCamera extends Camera {

	private static final float PITCH_SENSITIVITY = 0.3f;
	private static final float YAW_SENSITIVITY = 0.3f;
	
	private float speed = 10;
	private float yaw = 0, pitch = 0;
	private Vector3f position;
	
	/**
	 * Creates a {@link FreeCamera}
	 * 
	 * @param position The original position for this camera
	 */
	public FreeCamera(Vector3f position) {
		this.position = position;
		
		Input.setMouseGrabbed(true);
		
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
					Vector3f.add(position, (Vector3f)new Vector3f((float)Math.sin(Math.toRadians(yaw)) * speed, 0, (float)-Math.cos(Math.toRadians(yaw)) * speed), position);
				} else if (event.getKey() == GLFW_KEY_S) {
					Vector3f.add(position, (Vector3f)new Vector3f((float)Math.sin(Math.toRadians(yaw)) * speed, 0, (float)-Math.cos(Math.toRadians(yaw)) * speed).negate(null) , position);
				} else if (event.getKey() == GLFW_KEY_SPACE) {
					Vector3f.add(position, (Vector3f)new Vector3f(0, speed, 0), position);
				} else if (event.getKey() == GLFW_KEY_LEFT_SHIFT) {
					Vector3f.add(position, (Vector3f)new Vector3f(0, -speed, 0), position);
				} else if (event.getKey() == GLFW_KEY_D) {
					Vector3f.add(position, (Vector3f)new Vector3f((float)Math.cos(Math.toRadians(yaw)) * speed, 0, (float)Math.sin(Math.toRadians(yaw)) * speed) , position);
				} else if (event.getKey() == GLFW_KEY_A) {
					Vector3f.add(position, (Vector3f)new Vector3f((float)Math.cos(Math.toRadians(yaw)) * speed, 0, (float)Math.sin(Math.toRadians(yaw)) * speed).negate(null) , position);
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
	 * Creates a {@link FreeCamera} at the origin
	 */
	public FreeCamera() {
		this(new Vector3f(0, 0, 0));
	}
	
	@Override
	public void update() {}
	
	@Override
	public Vector3f getPosition() {
		return position;
	}

	@Override
	public float getPitch() {
		return pitch;
	}

	@Override
	public float getYaw() {
		return yaw;
	}

	@Override
	public float getRoll() {
		return 0;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
}
