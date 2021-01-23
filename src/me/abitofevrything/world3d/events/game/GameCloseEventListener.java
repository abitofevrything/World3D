package me.abitofevrything.world3d.events.game;

import me.abitofevrything.World3D;
import me.abitofevrything.world3d.events.EventListener;

/**
 * A listener for {@link GameCloseEvent}
 * 
 * @see GameCloseEvent
 * @see World3D#exit()
 * 
 * @author abitofevrything
 *
 */
public abstract class GameCloseEventListener extends EventListener<GameCloseEvent> {}
