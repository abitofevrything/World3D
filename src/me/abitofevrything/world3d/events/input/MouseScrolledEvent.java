package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.Event;

/**
 * An event triggered when the mouse wheel is scrolled
 * 
 * @author abitofevrything
 *
 */
public class MouseScrolledEvent extends Event {

	private int dWheel;
	
	public MouseScrolledEvent(int dWheel) {
		this.dWheel = dWheel;
	}

	/**
	 * @return The mouse wheel delta
	 */
	public int getDWheel() {
		return dWheel;
	}
	
}
