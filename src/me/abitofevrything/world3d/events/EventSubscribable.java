package me.abitofevrything.world3d.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EventSubscribable {

	private Map<Class<?>, List<EventListener<?>>> listeners = new HashMap<Class<?>, List<EventListener<?>>>();
	
	@SuppressWarnings("unchecked")
	private static <E extends Event> void triggerListeners(EventListener<E> el, Object object) {
		el.onEvent((E)object);
	}
	
	protected void triggerEventForSubscribers(Event e) {
		List<EventListener<?>> listenersList = listeners.get(e.getClass());
		if (listenersList == null) return;
		Iterator<EventListener<?>> iterator = listenersList.iterator();
		while (iterator.hasNext()) {
			EventListener<?> el = iterator.next();
			triggerListeners(el, e);
		}
	}
	
	public void subscribeToEvent(EventListener<?> listener) {
		Class<?> listenerClass = ((Method)Arrays.stream(listener.getClass().getDeclaredMethods()).filter(method -> method.getName().equals("onEvent")).toArray()[0]).getParameters()[0].getType();
		if (listeners.get(listenerClass) == null) {
			listeners.put(listenerClass, new ArrayList<EventListener<?>>());
		}
		listeners.get(listenerClass).add(listener);
	}
	
	public void unsubscribeFromEvent(EventListener<?> listener) {
		Class<?> listenerClass = null;
		try {
			listenerClass = listener.getClass().getDeclaredMethod("onEvent", Event.class).getParameters()[0].getType();
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		List<EventListener<?>> fromClass = listeners.get(listenerClass);
		if (fromClass == null) return;
		fromClass.remove(listener);
	}
	
}
