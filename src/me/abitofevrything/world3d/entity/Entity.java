package me.abitofevrything.world3d.entity;

import me.abitofevrything.world3d.audio.AudioSource;
import me.abitofevrything.world3d.events.game.GameUpdateEvent;
import me.abitofevrything.world3d.events.game.GameUpdateEventListener;
import me.abitofevrything.world3d.models.animatedModel.AnimatedModel;
import me.abitofevrything.world3d.util.Utils;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Entity extends AudioSource {

	private AnimatedModel model;
	
	private Vector3f prevPosition;
	
	private Vector3f position;
	private float rx, ry, rz;
	private float scale;
	
	public Entity(AnimatedModel model, Vector3f position, float rx, float ry, float rz, float scale) {
		super(position);
		this.model = model;
		this.position = position;
		this.rx = rx;
		this.ry = ry;
		this.rz = rz;
		this.scale = scale;
		
		this.prevPosition = position;
		
		new GameUpdateEventListener() {
			
			@Override
			public void onEvent(GameUpdateEvent event) {
				update();
				
				setVelocity(Vector3f.sub(position, prevPosition, null));
				prevPosition = position;
			}
		}.listen();
	}
	
	public Entity(AnimatedModel model, Vector3f position) {
		this(model, position, 0, 0, 0, 1);
	}
	
	public Matrix4f getTransformationMatrix() {
		return Utils.createTransformationMatrix(this);
	}

	public AnimatedModel getModel() {
		return model;
	}

	public void setModel(AnimatedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
		super.setPosition(position);
	}

	public float getRx() {
		return rx;
	}

	public void setRx(float rx) {
		this.rx = rx;
	}

	public float getRy() {
		return ry;
	}

	public void setRy(float ry) {
		this.ry = ry;
	}

	public float getRz() {
		return rz;
	}

	public void setRz(float rz) {
		this.rz = rz;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public void increasePosition(Vector3f delta) {
		Vector3f.add(position, delta, position);
	}
	
	public void increaseRx(float rx) {
		this.rx += rx;
	}
	
	public void increaseRy(float ry) {
		this.ry += ry;
	}
	
	public void increaseRz(float rz) {
		this.rz += rz;
	}
	
	/**
	 * Override to add your own update method
	 */
	public void update() {}
}
