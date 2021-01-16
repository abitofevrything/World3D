package me.abitofevrything.world3d.util.cameras;

import org.lwjgl.util.vector.Vector3f;

public class DefaultCamera extends Camera {

	@Override
	public Vector3f getPosition() {
		return new Vector3f(0, 0, 0);
	}

	@Override
	public float getListenerPitch() {
		return 0;
	}

	@Override
	public float getYaw() {
		return 0;
	}

	@Override
	public float getRoll() {
		return 0;
	}

	@Override
	public void update() {}

}
