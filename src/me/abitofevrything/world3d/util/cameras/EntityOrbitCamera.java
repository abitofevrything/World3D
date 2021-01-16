package me.abitofevrything.world3d.util.cameras;

import me.abitofevrything.world3d.entity.Entity;
import me.abitofevrything.world3d.events.input.Input;
import me.abitofevrything.world3d.events.input.MouseMovedEvent;
import me.abitofevrything.world3d.events.input.MouseMovedEventListener;
import me.abitofevrything.world3d.events.input.MouseScrollEvent;
import me.abitofevrything.world3d.events.input.MouseScrollEventListener;

import org.lwjgl.util.vector.Vector3f;

public class EntityOrbitCamera extends EntityTrackCamera {

	private static final float MIN_DISTANCE = 5, MAX_DISTANCE = 300;
	
	private static final float SCROLL_SENSITIVITY = 1;
	private static final float MOVE_SENSITIVITY = 1;
	
	public EntityOrbitCamera(Entity entity, float distance, Vector3f centerOffset, float rx, float ry, float rz) {
		super(entity, distance, centerOffset, rx, ry, rz);
		Input.setMouseGrabbed(true);
		
		new MouseScrollEventListener() {
			
			@Override
			public void onEvent(MouseScrollEvent event) {
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
	
	public EntityOrbitCamera(Entity entity, float distance) {
		this(entity, distance, new Vector3f(0, 0, 0), 0, 0, 0);
	}
	
	public EntityOrbitCamera(Entity entity, float distance, Vector3f centerOffset) {
		this(entity, distance, centerOffset, 0, 0, 0);
	}
	
	@Override
	public void update() {
		
	}
}
