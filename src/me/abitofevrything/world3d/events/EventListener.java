package me.abitofevrything.world3d.events;

/**
 * Represents a listener to a certain type of event
 * 
 * @author abitofevrything
 *
 * @param <E> The event to listen to
 */
public abstract class EventListener<E extends Event> {
	
	/**
	 * Sets this listener to listen to events on a global scale
	 * 
	 * @see EventManager#addEventListener(EventListener)
	 * @see EventManager#triggerEvent(Event)
	 */
	public void listen() {
		EventManager.addEventListener(this);
	}
	
	/**
	 * Called whenever the event is triggered on the listening object
	 * 
	 * @see EventManager#triggerEvent(Event)
	 * @see EventSubscribable#triggerEventForSubscribers(Event)
	 * 
	 * @param event The event that triggered this call
	 */
	public abstract void onEvent(E event);
	
}
