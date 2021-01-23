package me.abitofevrything.world3d.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Manages events on a global scale
 * 
 * @see Event
 * @see EventListener
 * 
 * @author abitofevrything
 *
 */
public class EventManager {
	
	private static Map<Class<?>, List<EventListener<?>>> listeners = new HashMap<Class<?>, List<EventListener<?>>>();
	
	public static void addEventListener(EventListener<?> listener) {
		Class<?> listenerClass = ((Method)Arrays.stream(listener.getClass().getDeclaredMethods()).filter(method -> method.getName().equals("onEvent")).toArray()[0]).getParameters()[0].getType();
		if (listeners.get(listenerClass) == null) {
			listeners.put(listenerClass, new ArrayList<EventListener<?>>());
		}
		listeners.get(listenerClass).add(listener);
	}

	public static void addEventListener(Class<? super Event> eventClass, EventListener<?> listener) {
		Class<?> listenerClass = eventClass;
		if (listeners.get(listenerClass) == null) {
			listeners.put(listenerClass, new ArrayList<EventListener<?>>());
		}
		listeners.get(listenerClass).add(listener);
	}
	
	public static void removeEventListener(EventListener<?> listener) {
		Class<?> listenerClass = listener.getClass().getDeclaredMethods()[0].getParameters()[0].getType();
		List<EventListener<?>> fromClass = listeners.get(listenerClass);
		if (fromClass == null) return;
		fromClass.remove(listener);
	}
	
	public static void triggerEvent(Event e) {
		List<EventListener<?>> listenersList = listeners.get(e.getClass());
		if (listenersList == null) return;
		Iterator<EventListener<?>> iterator = listenersList.iterator();
		while (iterator.hasNext()) {
			EventListener<?> el = iterator.next();
			triggerListener(el, e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends Event> void triggerListener(EventListener<E> el, Object object) {
		el.onEvent((E)object);
	}
}
