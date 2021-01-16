package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.Event;

public class MouseScrollEvent extends Event {

	private int dWheel;
	
	public MouseScrollEvent(int dWheel) {
		this.dWheel = dWheel;
	}

	public int getDWheel() {
		return dWheel;
	}
	
}
