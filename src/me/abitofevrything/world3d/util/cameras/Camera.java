package me.abitofevrything.world3d.util.cameras;

import me.abitofevrything.world3d.audio.AudioListener;
import me.abitofevrything.world3d.events.game.GameUpdateEvent;
import me.abitofevrything.world3d.events.game.GameUpdateEventListener;
import me.abitofevrything.world3d.util.Display;
import me.abitofevrything.world3d.util.Utils;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Represents a Camera that can be used to render from in the engine
 * 
 * @author abitofevrything
 *
 */
public abstract class Camera extends AudioListener {
	
	public static final float FOV = 70;
	public static final float NEAR_PLANE = 0.2f;
	public static final float FAR_PLANE = 10000;
	
	private Vector3f prevPosition;
	
	/**
	 * Call to initialise the camera
	 * 
	 * Sets up all the update events
	 */
	public Camera() {
		super(new Vector3f(0,0,0));
		
		prevPosition = new Vector3f();
		
		new GameUpdateEventListener() {
			
			@Override
			public void onEvent(GameUpdateEvent event) {
				update();
				setPosition(getPosition());
				setVelocity(Vector3f.sub(getPosition(), prevPosition, null));
				Vector3f[] orientation = Utils.createOrientationVectors(getRoll(), getPitch(), getYaw());
				setOrientation(orientation[0], orientation[1]);
				prevPosition = getPosition();
			}
		}.listen();
	}
	
	public Matrix4f getViewMatrix() {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(getRoll()), new Vector3f(0, 0, 1), viewMatrix,  viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(-getPitch()), new Vector3f(1, 0, 0), viewMatrix,  viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(getYaw()), new Vector3f(0, 1, 0), viewMatrix,  viewMatrix);
		Vector3f position = new Vector3f(getPosition()); //getPosition() might not return a new Vector3f each time, so clone it to be safe
		position.negate(position);
		Matrix4f.translate(position, viewMatrix, viewMatrix);
		return viewMatrix;
	}
	
	public Matrix4f getProjectionMatrix() {
		Matrix4f projectionMatrix = new Matrix4f();
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
		return projectionMatrix;
	}
	
	public Matrix4f getProjectionViewMatrix() {
		return Matrix4f.mul(getProjectionMatrix(), getViewMatrix(), null);
	}
	
	public abstract Vector3f getPosition();
	
	public abstract float getPitch();
	public abstract float getYaw();
	public abstract float getRoll();
	
	/**
	 * Called once per render cycle
	 * Use to update position
	 */
	public abstract void update();

	@Override
	public String toString() {
		return super.toString() + "[x=" + getPosition().x + ", y=" + getPosition().y + ", z=" + getPosition().z + ", roll=" + getRoll() + ", pitch=" + getPitch() + ", yaw=" + getYaw() + "]";
	}
	
}
