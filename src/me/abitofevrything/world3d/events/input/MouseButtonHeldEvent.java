package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.Event;

/**
 * An event triggered every frame a button on the mouse is held
 * 
 * @author abitofevrything
 *
 */
public class MouseButtonHeldEvent extends Event {

	private int button;
	
	public MouseButtonHeldEvent(int button) {
		this.button = button;
	}
	
	/**
	 * @return The index of the button being held
	 */
	public int getButton() {
		return button;
	}

}
