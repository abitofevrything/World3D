package me.abitofevrything.world3d.events.output;

import me.abitofevrything.world3d.events.Event;

/**
 * An event that is triggered once per frame render
 * 
 * @author abitofevrything
 *
 */
public class RenderEvent extends Event {
	
	public static final int FRAME_RENDER = 0, RENDER_TARGET_UPDATE = 1, RENDERER_FINISH = 2, POST_PROCESSING_EFFECT_FINISH = 3;

	private int type;
	
	public RenderEvent(int type) {
		this.type = type;
	}
	
	/**
	 * @return The type of this render
	 */
	public int getType() {
		return type;
	}
	
}
