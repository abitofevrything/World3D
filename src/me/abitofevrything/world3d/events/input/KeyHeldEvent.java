package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.Event;

public class KeyHeldEvent extends Event {

	private int key;
	
	public KeyHeldEvent(int key) {
		this.key = key;
	}

	public int getKey() {
		return key;
	}
	
}
