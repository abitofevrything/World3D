package me.abitofevrything.world3d.events.output;

import me.abitofevrything.world3d.audio.AudioListener;
import me.abitofevrything.world3d.audio.AudioSource;
import me.abitofevrything.world3d.audio.Sound;
import me.abitofevrything.world3d.events.EventListener;

/**
 * A listener for {@link SoundPlayedEvent}
 * 
 * @see Sound
 * @see AudioListener
 * @see AudioSource
 * @see AudioListener#bind()
 * @see AudioSource#playSound(Sound)
 * @see SoundPlayedEvent
 * 
 * @author abitofevrything
 *
 */
public abstract class SoundPlayedEventListener extends EventListener<SoundPlayedEvent> {}
