package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.Event;

public class KeyTypedEvent extends Event {

	private int key;
	
	public KeyTypedEvent(int key) {
		this.key = key;
	}
	
	public int getKey() {
		return key;
	}
	
}
