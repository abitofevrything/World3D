package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.Event;

/**
 * An event triggered when the mouse is moved
 * 
 * @author abitofevrything
 *
 */
public class MouseMovedEvent extends Event {

	private int startX, startY, endX, endY;

	public MouseMovedEvent(int startX, int startY, int endX, int endY) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}

	/**
	 * @return The x position of the mouse at the start of the move
	 */
	public int getStartX() {
		return startX;
	}

	/**
	 * @return The y position of the mouse at the start of the move
	 */
	public int getStartY() {
		return startY;
	}

	/**
	 * @return The x position of the mouse at the end of the move
	 */
	public int getEndX() {
		return endX;
	}

	/**
	 * @return The y position of the mouse at the end of the move
	 */
	public int getEndY() {
		return endY;
	}

	/**
	 * @return The delta x of the mouse during the move
	 */
	public int getDX() {
		return endX - startX;
	}
	
	/**
	 * @return The delta y of the mouse during the move
	 */
	public int getDY() {
		return endY - startY;
	}
	
}
