package me.abitofevrything.world3d.util;

public class SmoothFloat {
	
	private final float agility;
	
	private float target;
	private float actual;
	
	public SmoothFloat(float initialValue, float agility){
		this.target = initialValue;
		this.actual = initialValue;
		this.agility = agility;
	}
	
	public void update(float delta){
		float offset = target - actual;
		float change = offset * delta * agility;
		actual += change;
	}
	
	public void increaseTarget(float dT){
		this.target += dT;
	}
	
	public void setTarget(float target){
		this.target = target;
	}
	
	public float get(){
		return actual;
	}
	
	public float getTarget(){
		return target;
	}

	public void clamp(float low, float high) {
		if (actual < low) {
			actual = low;
		} else if (actual > high) {
			actual = high;
		}
		if (target < low) {
			target = low;
		} else if (target > high) {
			target = high;
		}
	}
	
}
