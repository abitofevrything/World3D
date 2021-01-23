package me.abitofevrything.world3d.util.cameras;

import me.abitofevrything.world3d.entity.Entity;
import me.abitofevrything.world3d.events.input.Input;
import me.abitofevrything.world3d.events.input.MouseMovedEvent;
import me.abitofevrything.world3d.events.input.MouseMovedEventListener;
import me.abitofevrything.world3d.events.input.MouseScrollEvent;
import me.abitofevrything.world3d.events.input.MouseScrollEventListener;
import me.abitofevrything.world3d.util.DisplayManager;
import me.abitofevrything.world3d.util.SmoothFloat;

import org.lwjgl.util.vector.Vector3f;

/**
 * A {@link Camera} that orbits around an {@link Entity}
 * 
 * Controls are :
 *  - Scroll in / out : Zoom in / out
 *  - Move mouse : Rotate camera around target entity
 * 
 * @author abitofevrything
 *
 */
public class SmoothEntityOrbitCamera extends EntityTrackCamera {

	private static final float MIN_DISTANCE = 5, MAX_DISTANCE = 300;
	
	private static final float SCROLL_SENSITIVITY = 1;
	private static final float MOVE_SENSITIVITY = 1;
	
	private SmoothFloat pitchOffset, yawOffset, rollOffset;
	private SmoothFloat distance;
	
	/**
	 * Creates a {@link SmoothEntityOrbitCamera}
	 * 
	 * @param entity The target {@link Entity}
	 * @param distance The original distance to have from the entity
	 * @param centerOffset An offset relative to the entity's position to center this camera on
	 * @param pitchOffset The original pitch offset relative to the entity's pitch
	 * @param yawOffset The original yaw offset relative to the entity's yaw
	 * @param rollOffset The original roll offset relative to the entity's roll
	 */
	public SmoothEntityOrbitCamera(Entity entity, float distance, Vector3f centerOffset, float pitchOffset, float yawOffset, float rollOffset) {
		super(entity, distance, centerOffset, pitchOffset, yawOffset, rollOffset);
		
		this.distance = new SmoothFloat(distance, 10);
		this.pitchOffset = new SmoothFloat(pitchOffset, 10);
		this.yawOffset = new SmoothFloat(yawOffset, 10);
		this.rollOffset = new SmoothFloat(rollOffset, 10);
		
		new MouseScrollEventListener() {
			
			@Override
			public void onEvent(MouseScrollEvent event) {
				SmoothEntityOrbitCamera.this.distance.increaseTarget((getDistance() + 10) * -event.getDWheel() / (1000/SCROLL_SENSITIVITY));
				SmoothEntityOrbitCamera.this.distance.clamp(MIN_DISTANCE, MAX_DISTANCE);
			}
		}.listen();
		
		new MouseMovedEventListener() {
			
			@Override
			public void onEvent(MouseMovedEvent event) {
				SmoothEntityOrbitCamera.this.yawOffset.increaseTarget(event.getDX() * (0.5f/MOVE_SENSITIVITY));
				SmoothEntityOrbitCamera.this.pitchOffset.increaseTarget(-event.getDY() * (0.5f/MOVE_SENSITIVITY));
				SmoothEntityOrbitCamera.this.pitchOffset.clamp(-90, 90);
			}
		}.listen();
	}
	
	/**
	 * Creates a {@link SmoothEntityOrbitCamera}
	 * 
	 * @param entity The target {@link Entity}
	 * @param distance The original distance from the entity
	 */
	public SmoothEntityOrbitCamera(Entity entity, float distance) {
		this(entity, distance, new Vector3f(0, 0, 0), 0, 0, 0);
	}
	
	/**
	 * Creates a {@link SmoothEntityOrbitCamera}
	 * 
	 * @param entity The target {@link Entity}
	 * @param distance The original distance from the entity
	 * @param centerOffset An offset relative to the entity's position to center this camera on
	 */
	public SmoothEntityOrbitCamera(Entity entity, float distance, Vector3f centerOffset) {
		this(entity, distance, centerOffset, 0, 0, 0);
	}
	
	@Override
	public void update() {
		Input.setMouseGrabbed(true);
		
		distance.update(DisplayManager.getFrameTime());
		pitchOffset.update(DisplayManager.getFrameTime());
		yawOffset.update(DisplayManager.getFrameTime());
		rollOffset.update(DisplayManager.getFrameTime());
		
		setDistance(distance.get());
		setPitchOffset(pitchOffset.get());
		setYawOffset(yawOffset.get());
		setRollOffset(rollOffset.get());
	}
}
