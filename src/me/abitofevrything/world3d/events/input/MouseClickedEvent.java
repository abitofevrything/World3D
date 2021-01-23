package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.Event;

/**
 * An event representing a mouse button being clicked
 * 
 * @author abitofevrything
 *
 */
public class MouseClickedEvent extends Event {

	private int button, x, y;
	
	public MouseClickedEvent(int button, int x, int y) {
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
	 * @return The x position of the mouse when the button was pressed
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @return The y position of the mouse when the button was pressed
	 */
	public int getY() {
		return y;
	}
	
}
