package me.abitofevrything.world3d.events.output;

import me.abitofevrything.world3d.audio.AudioListener;
import me.abitofevrything.world3d.audio.AudioSource;
import me.abitofevrything.world3d.audio.Sound;
import me.abitofevrything.world3d.events.Event;

public class SoundPlayedEvent extends Event {
	
	private AudioSource source;
	private AudioListener listener;
	
	private Sound sound;

	public SoundPlayedEvent(AudioSource source, AudioListener listener, Sound sound) {
		this.source = source;
		this.listener = listener;
		this.sound = sound;
	}

	public AudioSource getSource() {
		return source;
	}

	public AudioListener getListener() {
		return listener;
	}

	public Sound getSound() {
		return sound;
	}
	
	
}
