package me.abitofevrything.world3d.util.cameras;

import me.abitofevrything.world3d.events.input.Input;
import me.abitofevrything.world3d.events.input.MouseDraggedEvent;
import me.abitofevrything.world3d.events.input.MouseDraggedEventListener;
import me.abitofevrything.world3d.events.input.MouseScrollEvent;
import me.abitofevrything.world3d.events.input.MouseScrollEventListener;
import me.abitofevrything.world3d.util.DisplayManager;
import me.abitofevrything.world3d.util.SmoothFloat;

import org.lwjgl.util.vector.Vector3f;

/**
 * A {@link Camera} which orbits a point in a smooth manner
 * 
 * Controls are :
 *  - Scroll in / out : Zoom in / out
 *  - Drag mouse : rotate camera around specified point
 * 
 * @author abitofevrything
 *
 */
public class SmoothOrbitCamera extends Camera {

	private static final float PITCH_SENSITIVITY = 0.3f;
	private static final float YAW_SENSITIVITY = 0.3f;
	private static final float MAX_PITCH = 90;

	private Vector3f relativePosition = new Vector3f(0, 0, 0);
	
	private Vector3f center;
	
	private float yaw = 0;
	private SmoothFloat pitch = new SmoothFloat(10, 10);
	private SmoothFloat angleAroundPlayer = new SmoothFloat(0, 10);
	private SmoothFloat distanceFromPlayer = new SmoothFloat(10, 5);

	/**
	 * Creates a {@link SmoothOrbitCamera}
	 * 
	 * @param center The point for this camera to orbit around.
	 */
	public SmoothOrbitCamera(Vector3f center) {
		this.center = center;

		//Listen for mouse dragged event to update pitch & yaw
		new MouseDraggedEventListener() {
			@Override
			public void onEvent(MouseDraggedEvent event) {
				if (event.getButton() == 0) {
					pitch.increaseTarget(-event.getDY() * PITCH_SENSITIVITY);
					clampPitch();
					angleAroundPlayer.increaseTarget(-event.getDX() * YAW_SENSITIVITY);
				}
			}
		}.listen();
		
		//Listen to mouse scroll events to update distance from target
		new MouseScrollEventListener() {
			@Override
			public void onEvent(MouseScrollEvent event) {
				distanceFromPlayer.increaseTarget((distanceFromPlayer.get() + 10) * -event.getDWheel() / 1000);
				distanceFromPlayer.clamp(5, 300);
			}
		}.listen();
	}

	public SmoothOrbitCamera() {
		this(new Vector3f(0, 0, 0));
	}
	
	@Override
	public void update() {
		Input.setMouseGrabbed(false);
		
		//Update the smooth floats
		distanceFromPlayer.update(DisplayManager.getFrameTime());
		pitch.update(DisplayManager.getFrameTime());
		angleAroundPlayer.update(DisplayManager.getFrameTime());
		
		//Calculate and update relative position
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 360 - angleAroundPlayer.get();
		yaw %= 360;
	}

	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		float theta = angleAroundPlayer.get();
		relativePosition.x = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		relativePosition.y = verticDistance;
		relativePosition.z = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
	}

	/**
	 * @return The horizontal distance of the camera from the origin.
	 */
	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer.get() * Math.cos(Math.toRadians(pitch.get())));
	}

	/**
	 * @return The height of the camera from the aim point.
	 */
	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer.get() * Math.sin(Math.toRadians(pitch.get())));
	}

	/**
	 * Ensures the camera's pitch isn't too high or too low.
	 */
	private void clampPitch() {
		if (pitch.getTarget() < 0) {
			pitch.setTarget(0);
		} else if (pitch.getTarget() > MAX_PITCH) {
			pitch.setTarget(MAX_PITCH);
		}
	}

	@Override
	public Vector3f getPosition() {
		return Vector3f.add(center, relativePosition, null);
	}

	@Override
	public float getPitch() {
		return pitch.get();
	}

	@Override
	public float getYaw() {
		return yaw;
	}

	@Override
	public float getRoll() {
		return 0;
	}
	
	/**
	 * Sets the center of this camera
	 * 
	 * @param center The center of this camera
	 */
	public void setCenter(Vector3f center) {
		this.center = center;
	}

}
