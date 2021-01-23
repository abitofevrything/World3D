package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.Event;

/**
 * An event triggered when the mouse is "dragged"
 * "dragged" means moved while a button is pressed
 * 
 * @author abitofevrything
 *
 */
public class MouseDraggedEvent extends Event {

	private int button, startX, startY, endX, endY;

	public MouseDraggedEvent(int button, int startX, int startY, int endX, int endY) {
		this.button = button;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}

	/**
	 * @return The index of the pressed button
	 */
	public int getButton() {
		return button;
	}

	/**
	 * @return The x position of the mouse when the drag was started
	 */
	public int getStartX() {
		return startX;
	}

	/**
	 * @return The y position of the mouse when the drag was started
	 */
	public int getStartY() {
		return startY;
	}

	/**
	 * @return The x position of the mouse when the drag was ended
	 */
	public int getEndX() {
		return endX;
	}

	/**
	 * @return The y position of the mouse when the drag was ended
	 */
	public int getEndY() {
		return endY;
	}

	/**
	 * @return The delta x of the mouse during the drag
	 */
	public int getDX() {
		return endX - startX;
	}
	
	/**
	 * @return The delta y of the mouse during the drag
	 */
	public int getDY() {
		return endY - startY;
	}
}
