package me.abitofevrything.world3d.util.cameras;

import me.abitofevrything.world3d.entity.Entity;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * A {@link Camera} that tracks en entity, mimicking it's position, roll, pitch and yaw
 * 
 * @author abitofevrything
 *
 */
public class EntityTrackCamera extends Camera {

	private Entity tracking;
	
	private Vector3f centerOffset;
	private float distance;
	private float pitchOffset, yawOffset, rollOffset;
	
	/**
	 * Creates a {@link EntityTrackCamera}
	 * 
	 * @param entity The entity to track
	 * @param distance The distance from the entity
	 * @param centerOffset An offset relative to the entity's position to center this camera on
	 * @param pitchOffset An offset relative to the entity's pitch to set this camera's pitch to
	 * @param yawOffset An offset relative to the entity's yaw to set this camera's yaw to
	 * @param rollOffset An offset relative to the entity's roll to set this camera's roll to
	 */
	public EntityTrackCamera(Entity entity, float distance, Vector3f centerOffset, float pitchOffset, float yawOffset, float rollOffset) {
		this.tracking = entity;
		this.distance = distance;
		this.centerOffset = centerOffset;
		this.pitchOffset = pitchOffset;
		this.yawOffset = yawOffset;
		this.rollOffset = rollOffset;
	}
	
	/**
	 * Creates a {@link EntityTrackCamera}
	 * 
	 * @param entity The entity to track
	 * @param distance The distance from the entity
	 */
	public EntityTrackCamera(Entity entity, float distance) {
		this(entity, distance, new Vector3f(0, 0, 0), 0, 0, 0);
	}
	
	/**
	 * Creates a {@link EntityTrackCamera}
	 * 
	 * @param entity The entity to track
	 * @param distance The distance from the entity
	 * @param centerOffset An offset relative to the entity's position to center this camera on
	 */
	public EntityTrackCamera(Entity entity, float distance, Vector3f centerOffset) {
		this(entity, distance, centerOffset, 0, 0, 0);
	}
	
	@Override
	public Matrix4f getViewMatrix() {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Vector3f position = getPosition();
		position.negate(position);
		Matrix4f.translate(position, viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(getRoll()), new Vector3f(0, 0, 1), viewMatrix,  viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(getListenerPitch()), new Vector3f(1, 0, 0), viewMatrix,  viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(getYaw()), new Vector3f(0, 1, 0), viewMatrix,  viewMatrix);
		Matrix4f.translate(centerOffset.negate(null), viewMatrix, viewMatrix);
		return viewMatrix;
	 }
	
	@Override
	public Vector3f getPosition() {
		return Vector3f.add(tracking.getPosition(), new Vector3f(0, 0, distance), null);
	}

	@Override
	public float getPitch() {
		return -tracking.getRx() + pitchOffset;
	}

	@Override
	public float getYaw() {
		return -tracking.getRy() + yawOffset;
	}

	@Override
	public float getRoll() {
		return -tracking.getRz() + rollOffset;
	}
	
	public void increaseDistance(float distanceChange) {
		distance += distanceChange;
	}

	public void increasePitchOffset(float pitchOffsetChange) {
		pitchOffset += pitchOffsetChange;
	}
	
	public void increaseYawOffset(float yawOffsetChange) {
		yawOffset += yawOffsetChange;
	}
	
	public void increaseRollOffset(float rollOffsetChange) {
		rollOffset += rollOffsetChange;
	}

	public Entity getTracking() {
		return tracking;
	}

	public void setTracking(Entity tracking) {
		this.tracking = tracking;
	}

	public Vector3f getCenterOffset() {
		return centerOffset;
	}

	public void setCenterOffset(Vector3f centerOffset) {
		this.centerOffset = centerOffset;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getPitchOffset() {
		return pitchOffset;
	}

	public void setPitchOffset(float pitchOffset) {
		this.pitchOffset = pitchOffset;
	}

	public float getYawOffset() {
		return yawOffset;
	}

	public void setYawOffset(float yawOffset) {
		this.yawOffset = yawOffset;
	}

	public float getRollOffset() {
		return rollOffset;
	}

	public void setRollOffset(float rollOffset) {
		this.rollOffset = rollOffset;
	}

	@Override
	public void update() {}
	
}
