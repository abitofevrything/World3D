package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.Event;

public class MouseDraggedEvent extends Event {

	private int button, startX, startY, endX, endY;

	public MouseDraggedEvent(int button, int startX, int startY, int endX, int endY) {
		this.button = button;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}

	public int getButton() {
		return button;
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
