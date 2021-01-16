package me.abitofevrything.world3d.audio;

import me.abitofevrything.world3d.events.game.GameCloseEvent;
import me.abitofevrything.world3d.events.game.GameCloseEventListener;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector3f;

public class AudioListener {
	
	private static AudioListener bound;
	
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
	
	public AudioListener(Vector3f position, Vector3f velocity, float volume, float pitch) {
		this.position = position;
		this.velocity = velocity;
		this.volume = volume;
		this.pitch = pitch;
	}
	
	public AudioListener(Vector3f position) {
		this(position, new Vector3f(0,0,0), 1, 1);
	}
	
	public AudioListener(Vector3f position, Vector3f velocity) {
		this(position, velocity, 1, 1);
	}
	
	public AudioListener(Vector3f position, Vector3f velocity, float volume) {
		this(position, velocity, volume, 1);
	}
	
	public AudioListener(Vector3f position, float volume) {
		this(position, new Vector3f(0,0,0), volume, 1);
	}
	
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
