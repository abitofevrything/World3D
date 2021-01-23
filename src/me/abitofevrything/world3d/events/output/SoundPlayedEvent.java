package me.abitofevrything.world3d.events.output;

import me.abitofevrything.world3d.audio.AudioListener;
import me.abitofevrything.world3d.audio.AudioSource;
import me.abitofevrything.world3d.audio.Sound;
import me.abitofevrything.world3d.events.Event;

/**
 * An event triggered when a sound is played
 * 
 * @author abitofevrything
 *
 */
public class SoundPlayedEvent extends Event {
	
	private AudioSource source;
	private AudioListener listener;
	
	private Sound sound;

	public SoundPlayedEvent(AudioSource source, AudioListener listener, Sound sound) {
		this.source = source;
		this.listener = listener;
		this.sound = sound;
	}

	/**
	 * @return The source this sound was played from
	 */
	public AudioSource getSource() {
		return source;
	}

	/**
	 * @return The listener this sound was played to
	 */
	public AudioListener getListener() {
		return listener;
	}

	/**
	 * @return The played sound
	 */
	public Sound getSound() {
		return sound;
	}
	
	
}
