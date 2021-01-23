package me.abitofevrything.world3d.util.cameras;

import me.abitofevrything.world3d.events.input.MouseDraggedEvent;
import me.abitofevrything.world3d.events.input.MouseDraggedEventListener;
import me.abitofevrything.world3d.events.input.MouseScrollEvent;
import me.abitofevrything.world3d.events.input.MouseScrollEventListener;

import org.lwjgl.util.vector.Vector3f;

/**
 * A {@link Camera} that rbits around a point
 * 
 * Controls are :
 *  - Scroll up / down : Zoom in / out
 *  - Drag mouse : Rotate camera
 * 
 * @author abitofevrything
 *
 */
public class OrbitCamera extends Camera {

	private static final float MIN_DISTANCE = 5, MAX_DISTANCE = 300;
	
	private static final float SCROLL_SENSITIVITY = 1;
	private static final float PITCH_SENSITIVITY = 0.3f;
	private static final float YAW_SENSITIVITY = 0.3f;
	private static final float MAX_PITCH = 90;

	private Vector3f relativePosition = new Vector3f(0, 0, 0);
	
	private Vector3f center;
	
	private float yaw = 0;
	private float pitch = 10;
	private float angleAroundPlayer = 0;
	private float distanceFromPlayer = 10;

	/**
	 * Creates an {@link OrbitCamera}
	 * 
	 * @param center The point to orbit around
	 */
	public OrbitCamera(Vector3f center) {
		this.center = center;
		
		//Listen for mouse dragged event to update pitch & yaw
		new MouseDraggedEventListener() {
			@Override
			public void onEvent(MouseDraggedEvent event) {
				if (event.getButton() == 0) {
					pitch += -event.getDY() * PITCH_SENSITIVITY;
					clampPitch();
					angleAroundPlayer += -event.getDX() * YAW_SENSITIVITY;
				}
			}
		}.listen();
		
		//Listen to mouse scroll events to update distance from target
		new MouseScrollEventListener() {
			@Override
			public void onEvent(MouseScrollEvent event) {
				distanceFromPlayer += (distanceFromPlayer + 10) * -event.getDWheel() / (1000/SCROLL_SENSITIVITY);
				if (distanceFromPlayer < MIN_DISTANCE) {
					distanceFromPlayer = MIN_DISTANCE;
				} else if (distanceFromPlayer > MAX_DISTANCE) {
					distanceFromPlayer = MAX_DISTANCE;
				}
			}
		}.listen();
	}

	/**
	 * Creates an {@link OrbitCamera} centered on the origin
	 */
	public OrbitCamera() {
		this(new Vector3f(0, 0, 0));
	}
	
	@Override
	public void update() {
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 360 - angleAroundPlayer;
		yaw %= 360;
	}

	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		float theta = angleAroundPlayer;
		relativePosition.x = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		relativePosition.y = verticDistance;
		relativePosition.z = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
	}

	/**
	 * @return The horizontal distance of the camera from the origin.
	 */
	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}

	/**
	 * @return The height of the camera from the aim point.
	 */
	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}

	/**
	 * Ensures the camera's pitch isn't too high or too low.
	 */
	private void clampPitch() {
		if (pitch < 0) {
			pitch = 0;
		} else if (pitch > MAX_PITCH) {
			pitch = MAX_PITCH;
		}
	}

	@Override
	public Vector3f getPosition() {
		return Vector3f.add(center, relativePosition, null);
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
	public float getPitch() {
		return pitch;
	}

}
