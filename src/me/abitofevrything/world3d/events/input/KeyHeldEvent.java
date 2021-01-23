package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.Event;

/**
 * Event triggered every frame a key is held down
 * 
 * @author abitofevrything
 *
 */
public class KeyHeldEvent extends Event {

	private int key;
	
	public KeyHeldEvent(int key) {
		this.key = key;
	}

	/**
	 * @return the index of the pressed key
	 */
	public int getKey() {
		return key;
	}
	
}
