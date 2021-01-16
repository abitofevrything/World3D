package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.Event;

public class MouseMovedEvent extends Event {

	private int startX, startY, endX, endY;

	public MouseMovedEvent(int startX, int startY, int endX, int endY) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getEndX() {
		return endX;
	}

	public int getEndY() {
		return endY;
	}

	public int getDX() {
		return endX - startX;
	}
	
	public int getDY() {
		return endY - startY;
	}
	
}
