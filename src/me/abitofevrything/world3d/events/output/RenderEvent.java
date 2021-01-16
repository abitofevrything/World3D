package me.abitofevrything.world3d.events.output;

import me.abitofevrything.world3d.events.Event;

/**
 * An event that is triggered once per frame render
 * 
 * @author abitofevrything
 *
 */
public class RenderEvent extends Event {
	
	private String type;
	
	public RenderEvent(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}
