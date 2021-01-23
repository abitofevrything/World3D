package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.Event;

/**
 * An event triggered when the mouse wheel is scrolled
 * 
 * @author abitofevrything
 *
 */
public class MouseScrollEvent extends Event {

	private int dWheel;
	
	public MouseScrollEvent(int dWheel) {
		this.dWheel = dWheel;
	}

	/**
	 * @return The mouse wheel delta
	 */
	public int getDWheel() {
		return dWheel;
	}
	
}
