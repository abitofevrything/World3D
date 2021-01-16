package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.Event;

public class KeyPressedEvent extends Event {

	private int key;
	
	public KeyPressedEvent(int key) {
		this.key = key;
	}

	public int getKey() {
		return key;
	}
	
}
