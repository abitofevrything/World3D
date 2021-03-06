package me.abitofevrything.world3d.audio;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

/**
 * Represents a sound that can be played
 * 
 * @see AudioSource#playSound(Sound)
 * @see AudioSource
 * @see AudioListener
 * 
 * @author abitofevrything
 *
 */
public class Sound {

	private static Map<String, Integer> loadedBuffers = new HashMap<String, Integer>();
	
	private int buffer;
	private float volume, pitch;
	
	private String name;
	
	/**
	 * Creates a {@link Sound}
	 * 
	 * @param file The file to load the sound from. Must be .wav format
	 * @param volume The volume to play this sound at
	 * @param pitch The pitch to play this sound at
	 */
	public Sound(String file, float volume, float pitch) {
		Objects.requireNonNull(file);
		
		Integer buffer;
		if ((buffer = loadedBuffers.get(file)) != null) {
			this.buffer = buffer;
			this.name = file;
			this.volume = volume;
			this.pitch = pitch;
			return;
		}
		
		buffer = AL10.alGenBuffers();
		loadedBuffers.put(file, buffer);
		
		WaveData data = WaveData.create(Sound.class.getResourceAsStream("/" + file));
		AL10.alBufferData(buffer, data.format, data.data, data.samplerate);
		
		data.dispose();
		
		this.buffer = buffer;
		
		this.name = file;
		this.volume = volume;
		this.pitch = pitch;
	}
	
	/**
	 * Creates a {@link Sounds}
	 * 
	 * @param file The file to load the sound from. Must be .wav format
	 */
	public Sound(String file) {
		this(file, 1, 1);
	}
	
	/**
	 * Creates a {@link Sound}
	 * 
	 * @param file The file o oad the sound from. Must be .wav format
	 * @param volume The volume to play this sound at
	 */
	public Sound(String file, float volume) {
		this(file, volume, 1);
	}
	
	/**
	 * Deletes all loaded sounds
	 */
	public static void deleteBuffers() {
		for (int buffer : loadedBuffers.values()) {
			AL10.alDeleteBuffers(buffer);
		}
	}
	
	public int getBuffer() {
		return buffer;
	}
	
	public float getVolume() {
		return volume;
	}
	
	public void setVolume(float volume) {
		this.volume = volume;
	}
	
	public float getSoundPitch() {
		return pitch;
	}
	
	public void setSoundPitch(float pitch) {
		this.pitch = pitch;
	}

	public String getName() {
		return name;
	}
}
