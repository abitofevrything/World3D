package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.Event;

/**
 * An event triggered when a mouse button is pressed
 * 
 * @author abitofevrything
 *
 */
public class MousePressedEvent extends Event {

	private int button, x, y;
	
	public MousePressedEvent(int button, int x, int y) {
		this.button = button;
		this.x = x;
		this.y = y;
	}

	/**
	 * @return The index of the pressed button
	 */
	public int getButton() {
		return button;
	}
	
	/**
	 * @return The x position at which the button was pressed
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @return The y position at which the button was pressed
	 */
	public int getY() {
		return y;
	}
	
}
