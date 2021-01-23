package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.Event;

/**
 * An event triggered upon a key being released
 * 
 * @author abitofevrything
 *
 */
public class KeyReleasedEvent extends Event {

	private int key;
	
	public KeyReleasedEvent(int key) {
		this.key = key;
	}
	
	/**
	 * @return The index of the released key
	 */
	public int getKey() {
		return key;
	}
	
}
