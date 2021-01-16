package me.abitofevrything.world3d.events;

public abstract class EventListener<E extends Event> {
	
	public void listen() {
		EventManager.addEventListener(this);
	}
	
	public abstract void onEvent(E event);
	
}
