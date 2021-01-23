package me.abitofevrything.world3d.events.input;

import me.abitofevrything.world3d.events.EventListener;

/**
 * A listener for {@link MouseDraggedEvent}
 * 
 * @see Input#isMouseButtonDown(int)
 * @see Input#getMouseDeltaPosition()
 * @see Input#getMouseDX()
 * @see Input#getMouseDY()
 * @see MouseDraggedEvent
 * 
 * @author abitofevrything
 *
 */
public abstract class MouseDraggedEventListener extends EventListener<MouseDraggedEvent> {}
