package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.Event;

public class KeyReleasedEvent extends Event {

	private int key;
	
	public KeyReleasedEvent(int key) {
		this.key = key;
	}

	public int getKey() {
		return key;
	}
	
}
