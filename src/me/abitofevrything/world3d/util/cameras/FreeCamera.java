package me.abitofevrything.world3d.util.cameras;

import me.abitofevrything.world3d.events.input.Input;
import me.abitofevrything.world3d.events.input.KeyHeldEvent;
import me.abitofevrything.world3d.events.input.KeyHeldEventListener;
import me.abitofevrything.world3d.events.input.MouseMovedEvent;
import me.abitofevrything.world3d.events.input.MouseMovedEventListener;
import me.abitofevrything.world3d.events.input.MouseScrollEvent;
import me.abitofevrything.world3d.events.input.MouseScrollEventListener;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class FreeCamera extends Camera {

	private static final float PITCH_SENSITIVITY = 0.3f;
	private static final float YAW_SENSITIVITY = 0.3f;
	
	private float speed = 10;
	private float yaw = 0, pitch = 0;
	private Vector3f position;
	
	static {
		
	}
	
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
				
				if (event.getKey() == Keyboard.KEY_W) {
					Vector3f.add(position, (Vector3f)new Vector3f((float)Math.sin(Math.toRadians(yaw)) * speed, 0, (float)-Math.cos(Math.toRadians(yaw)) * speed), position);
				} else if (event.getKey() == Keyboard.KEY_S) {
					Vector3f.add(position, (Vector3f)new Vector3f((float)Math.sin(Math.toRadians(yaw)) * speed, 0, (float)-Math.cos(Math.toRadians(yaw)) * speed).negate(null) , position);
				} else if (event.getKey() == Keyboard.KEY_SPACE) {
					Vector3f.add(position, (Vector3f)new Vector3f(0, speed, 0), position);
				} else if (event.getKey() == Keyboard.KEY_LSHIFT) {
					Vector3f.add(position, (Vector3f)new Vector3f(0, -speed, 0), position);
				} else if (event.getKey() == Keyboard.KEY_D) {
					Vector3f.add(position, (Vector3f)new Vector3f((float)Math.cos(Math.toRadians(yaw)) * speed, 0, (float)Math.sin(Math.toRadians(yaw)) * speed) , position);
				} else if (event.getKey() == Keyboard.KEY_A) {
					Vector3f.add(position, (Vector3f)new Vector3f((float)Math.cos(Math.toRadians(yaw)) * speed, 0, (float)Math.sin(Math.toRadians(yaw)) * speed).negate(null) , position);
				}
			}
		}.listen();
		
		new MouseScrollEventListener() {
			
			@Override
			public void onEvent(MouseScrollEvent event) {
				speed += (speed * event.getDWheel()) / 1000;
			}
		}.listen();
	}

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
	public float getListenerPitch() {
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
	
	public void setListenerPitch(float pitch) {
		this.pitch = pitch;
	}
	
}
