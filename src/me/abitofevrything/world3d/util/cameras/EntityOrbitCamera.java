package me.abitofevrything.world3d.util.cameras;

import me.abitofevrything.world3d.entity.Entity;
import me.abitofevrything.world3d.events.input.Input;
import me.abitofevrything.world3d.events.input.MouseMovedEvent;
import me.abitofevrything.world3d.events.input.MouseMovedEventListener;
import me.abitofevrything.world3d.events.input.MouseScrolledEvent;
import me.abitofevrything.world3d.events.input.MouseScrolledEventListener;

import org.lwjgl.util.vector.Vector3f;

/**
 * A {@link Camera} that orbits an {@link Entity}
 * 
 * Controls are :
 *  - Move mouse : Rotate view
 *  - Scroll up / down : Zoom in / out
 * 
 * @author abitofevrything
 *
 */
public class EntityOrbitCamera extends EntityTrackCamera {

	private static final float MIN_DISTANCE = 5, MAX_DISTANCE = 300;
	
	private static final float SCROLL_SENSITIVITY = 1;
	private static final float MOVE_SENSITIVITY = 1;
	
	/**
	 * Creates an {@link EntityOrbitCamera}
	 * 
	 * @param entity The entity to orbit
	 * @param distance The original distance from the entity
	 * @param centerOffset An offset relative to the entity's position to center this camera on
	 * @param rx The original x rotation offset
	 * @param ry The original y rotation offset
	 * @param rz The original z rotation offset
	 */
	public EntityOrbitCamera(Entity entity, float distance, Vector3f centerOffset, float rx, float ry, float rz) {
		super(entity, distance, centerOffset, rx, ry, rz);
		Input.setMouseGrabbed(true);
		
		new MouseScrolledEventListener() {
			
			@Override
			public void onEvent(MouseScrolledEvent event) {
				increaseDistance((getDistance() + 10) * -event.getDWheel() / (1000/SCROLL_SENSITIVITY));
				if (getDistance() < MIN_DISTANCE) {
					setDistance(MIN_DISTANCE);
				} else if (getDistance() > MAX_DISTANCE) {
					setDistance(MAX_DISTANCE);
				}
			}
		}.listen();
		
		new MouseMovedEventListener() {
			
			@Override
			public void onEvent(MouseMovedEvent event) {
				increaseYawOffset(event.getDX() * (0.5f/MOVE_SENSITIVITY));
				increasePitchOffset(-event.getDY() * (0.5f/MOVE_SENSITIVITY));
				if (getPitchOffset() > 90) {
					setPitchOffset(90);
				} else if (getPitchOffset() < -90) {
					setPitchOffset(-90);
				}
			}
		}.listen();
	}
	
	/**
	 * Creates a {@link EntityOrbitCamera}
	 * 
	 * @param entity The entity to orbit
	 * @param distance The distance from the entity
	 */
	public EntityOrbitCamera(Entity entity, float distance) {
		this(entity, distance, new Vector3f(0, 0, 0), 0, 0, 0);
	}
	
	/**
	 * Creates a {@link EntityOrbitCamera}
	 * 
	 * @param entity The entity to orbit
	 * @param distance The distance from the entity
	 * @param centerOffset An offset relative to the entity's position to center this camera on
	 */
	public EntityOrbitCamera(Entity entity, float distance, Vector3f centerOffset) {
		this(entity, distance, centerOffset, 0, 0, 0);
	}
	
	@Override
	public void update() {}
}
