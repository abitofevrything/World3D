package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.Event;

public class MouseReleasedEvent extends Event {

	private int button, x, y;
	
	public MouseReleasedEvent(int button, int x, int y) {
		this.button = button;
		this.x = x;
		this.y = y;
	}

	public int getButton() {
		return button;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
