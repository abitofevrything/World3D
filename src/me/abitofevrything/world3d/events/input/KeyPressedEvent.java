package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.Event;

/**
 * Event triggered upon key being pressed
 * 
 * @author abitofevrything
 *
 */
public class KeyPressedEvent extends Event {

	private int key;
	
	public KeyPressedEvent(int key) {
		this.key = key;
	}

	/**
	 * @return The index of the pressed key
	 */
	public int getKey() {
		return key;
	}
	
}
