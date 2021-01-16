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

public class SmoothEntityOrbitCamera extends EntityTrackCamera {

	private static final float MIN_DISTANCE = 5, MAX_DISTANCE = 300;
	
	private static final float SCROLL_SENSITIVITY = 1;
	private static final float MOVE_SENSITIVITY = 1;
	
	private SmoothFloat pitchOffset, yawOffset, rollOffset;
	private SmoothFloat distance;
	
	public SmoothEntityOrbitCamera(Entity entity, float distance, Vector3f centerOffset, float pitchOffset, float yawOffset, float rollOffset) {
		super(entity, distance, centerOffset, pitchOffset, yawOffset, rollOffset);
		
		this.distance = new SmoothFloat(distance, 10);
		this.pitchOffset = new SmoothFloat(pitchOffset, 10);
		this.yawOffset = new SmoothFloat(yawOffset, 10);
		this.rollOffset = new SmoothFloat(rollOffset, 10);
		
		Input.setMouseGrabbed(true);
		
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
	
	public SmoothEntityOrbitCamera(Entity entity, float distance) {
		this(entity, distance, new Vector3f(0, 0, 0), 0, 0, 0);
	}
	
	public SmoothEntityOrbitCamera(Entity entity, float distance, Vector3f centerOffset) {
		this(entity, distance, centerOffset, 0, 0, 0);
	}
	
	@Override
	public void update() {
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
