package me.abitofevrything.world3d.audio;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector3f;

import me.abitofevrything.world3d.events.EventManager;
import me.abitofevrything.world3d.events.output.SoundPlayedEvent;

/**
 * Represents a source for audio in the engine
 * 
 * @see AudioListener
 * @see Sound
 * 
 * @author abitofevrything
 *
 */
public class AudioSource {

	private static List<Integer> sources = new ArrayList<Integer>();
	
	private Vector3f position, velocity;
	
	private int sourceID;
	private float volume, pitch;

	private int distanceAttenuationModel = AL10.AL_INVERSE_DISTANCE;
	private float distanceAttenuationMaxDist = 100, distanceAttenuationMinDist = 10, distanceAttenuationRolloffFactor = 1;
	
	/**
	 * Creates an {@link AudioSource}
	 * 
	 * @param position The position for this source
	 */
	public AudioSource(Vector3f position) {
		this(position, new Vector3f(0,0,0), 1, 1);
	}
	
	/**
	 * Creates an {@link AudioSource}
	 * 
	 * @param position The position for this source
	 * @param velocity The velocity for this source
	 */
	public AudioSource(Vector3f position, Vector3f velocity) {
		this(position, velocity, 1, 1);
	}
	
	/**
	 * Creates an {@link AudioSource}
	 * 
	 * @param position The position for this source
	 * @param volume The volume to play sounds at from this source
	 */
	public AudioSource(Vector3f position, float volume) {
		this(position, new Vector3f(0,0,0), volume, 1);
	}
	
	/**
	 * Creates an {@link AudioSource}
	 * 
	 * @param position The position of this source
	 * @param volume The volume to play sounds at from this source
	 * @param pitch The pitch to play sounds at from this source
	 */
	public AudioSource(Vector3f position, float volume, float pitch) {
		this(position, new Vector3f(0,0,0), volume, pitch);
	}
	
	/**
	 * Creates an {@link AudioSource}
	 * 
	 * @param position The position of this source
	 * @param velocity The velocity of this source
	 * @param volume The volume to play sounds at when using this source
	 * @param pitch The pitch to play sounds at when using this source
	 */
	public AudioSource(Vector3f position, Vector3f velocity, float volume, float pitch) {
		this.sourceID = AL10.alGenSources();
		sources.add(sourceID);
		
		setPosition(position);
		setVelocity(velocity);
		setVolume(volume);
		setSourcePitch(pitch);
	}
	
	/**
	 * Plays a {@link Sound} to the currently bound {@link AudioListener} from this source
	 * 
	 * @param sound The sound to play
	 * 
	 * @see AudioListener#getBound()
	 * @see SoundPlayedEvent
	 */
	public void playSound(Sound sound) {
		AL10.alDistanceModel(getDistanceAttenuationModel());
		AL10.alSourcef(sourceID, AL10.AL_ROLLOFF_FACTOR, getDistanceAttenuationRolloffFactor());
		AL10.alSourcef(sourceID, AL10.AL_MAX_DISTANCE, getDistanceAttenuationMaxDist());
		AL10.alSourcef(sourceID, AL10.AL_REFERENCE_DISTANCE, getDistanceAttenuationMinDist());
		
		AL10.alSourcei(sourceID, AL10.AL_BUFFER, sound.getBuffer());
		AL10.alSourcePlay(sourceID);
		
		EventManager.triggerEvent(new SoundPlayedEvent(this, AudioListener.getBound(), sound));
	}
	
	/**
	 * Deletes all the sources
	 */
	public static void deleteSources() {
		for (int source : sources) {
			AL10.alDeleteSources(source);
		}
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
		AL10.alSource3f(sourceID, AL10.AL_POSITION, position.x, position.y, position.z);
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
		AL10.alSource3f(sourceID, AL10.AL_VELOCITY, velocity.x, velocity.y, velocity.z);
	}
	
	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
		AL10.alSourcef(sourceID, AL10.AL_GAIN, volume);
	}

	public float getSourcePitch() {
		return pitch;
	}

	public void setSourcePitch(float pitch) {
		this.pitch = pitch;
		AL10.alSourcef(sourceID, AL10.AL_PITCH, pitch);
	}

	public int getSourceID() {
		return sourceID;
	}

	public int getDistanceAttenuationModel() {
		return distanceAttenuationModel;
	}

	public void setDistanceAttenuationModel(int distanceAttenuationModel) {
		this.distanceAttenuationModel = distanceAttenuationModel;
	}

	public float getDistanceAttenuationMaxDist() {
		return distanceAttenuationMaxDist;
	}

	public void setDistanceAttenuationMaxDist(float distanceAttenuationMaxDist) {
		this.distanceAttenuationMaxDist = distanceAttenuationMaxDist;
	}

	public float getDistanceAttenuationMinDist() {
		return distanceAttenuationMinDist;
	}

	public void setDistanceAttenuationMinDist(float distanceAttenuationMinDist) {
		this.distanceAttenuationMinDist = distanceAttenuationMinDist;
	}

	public float getDistanceAttenuationRolloffFactor() {
		return distanceAttenuationRolloffFactor;
	}

	public void setDistanceAttenuationRolloffFactor(float distanceAttenuationRolloffFactor) {
		this.distanceAttenuationRolloffFactor = distanceAttenuationRolloffFactor;
	}
	
}
