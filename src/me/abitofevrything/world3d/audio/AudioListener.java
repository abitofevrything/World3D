package me.abitofevrything.world3d.audio;

import me.abitofevrything.world3d.events.game.GameCloseEvent;
import me.abitofevrything.world3d.events.game.GameCloseEventListener;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector3f;

/**
 * Represents an Entity that can listen to audio
 * 
 * @see AudioSource
 * @see Sound
 * 
 * @author abitofevrything
 *
 */
public class AudioListener {
	
	private static AudioListener bound;
	
	/**
	 * Call to initialize OpenAL and setup 
	 */
	public static void init() {
		try {
			AL.create();
			new GameCloseEventListener() {
				@Override
				public void onEvent(GameCloseEvent event) {
					Sound.deleteBuffers();
					AudioSource.deleteSources();
					AL.destroy();
				}
			}.listen();
		} catch (LWJGLException e) {
			System.err.println("Unable to create OpenAL context");
			e.printStackTrace();
		}
	}
	
	private Vector3f position, velocity;
	private float volume, pitch;
	
	/**
	 * Creates an {@link AudioListener}
	 * 
	 * @param position The position of this listener
	 * @param velocity The velocity of this listener
	 * @param volume The volume to play sounds at when using this listener
	 * @param pitch The pitch to play sounds at when using this listener
	 */
	public AudioListener(Vector3f position, Vector3f velocity, float volume, float pitch) {
		this.position = position;
		this.velocity = velocity;
		this.volume = volume;
		this.pitch = pitch;
	}
	
	/**
	 * Creates an {@link AudioListener}
	 * 
	 * @param position The position of this listener
	 */
	public AudioListener(Vector3f position) {
		this(position, new Vector3f(0,0,0), 1, 1);
	}
	
	/**
	 * Creates an {@link AudioListener}
	 * 
	 * @param position The position of this listener
	 * @param velocity The velocityof this listener
	 */
	public AudioListener(Vector3f position, Vector3f velocity) {
		this(position, velocity, 1, 1);
	}
	
	/**
	 * Creats an {@link AudioListener}
	 * 
	 * @param position The position of this listener
	 * @param velocity The velcity of this listenr
	 * @param volume The volume to play sounds at when using this listener
	 */
	public AudioListener(Vector3f position, Vector3f velocity, float volume) {
		this(position, velocity, volume, 1);
	}
	
	/**
	 * Creates an {@link AudioListener}
	 * 
	 * @param position The position of this listener
	 * @param volume The volume to play sounds at when using this listener
	 */
	public AudioListener(Vector3f position, float volume) {
		this(position, new Vector3f(0,0,0), volume, 1);
	}
	
	/**
	 * Binds this listener to the audio output
	 * 
	 * Sounds played with {@link AudioSource#playSound(Sound)} will now be played to this listener
	 */
	public void bind() {
		bound = this;
		
		AL10.alListenerf(AL10.AL_GAIN, volume);
		AL10.alListenerf(AL10.AL_PITCH, pitch);
		
		AL10.alListener3f(AL10.AL_POSITION, position.x, position.y, position.z);
		AL10.alListener3f(AL10.AL_VELOCITY, velocity.x, velocity.y, velocity.z);
	}
	
	public static AudioListener getBound() {
		return bound;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}

	public float getListenerPitch() {
		return pitch;
	}

	public void setListenerPitch(float pitch) {
		this.pitch = pitch;
	}
}
