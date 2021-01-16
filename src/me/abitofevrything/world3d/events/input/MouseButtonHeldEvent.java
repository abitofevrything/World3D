package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.Event;

public class MouseButtonHeldEvent extends Event {

	private int button;
	
	public MouseButtonHeldEvent(int button) {
		this.button = button;
	}
	
	public int getButton() {
		return button;
	}

}
