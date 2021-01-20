package me.abitofevrything.world3d.rendering.postProcessing;

import java.util.ArrayList;
import java.util.List;

import me.abitofevrything.world3d.rendering.RenderTarget;

/**
 * @author abitofevrything
 */
public class PostProcessingProgram {
	
	private RenderTarget output, input;
	
	private List<PostProcessingEffect> effects = new ArrayList<PostProcessingEffect>();
	
	/**
	 * Creates an empty {@link PostProcessingProgram} with the specified input and output
	 * Empty programs will not output anything to the output target
	 * 
	 * @param input The {@link RenderTarget} to read from
	 * @param output The {@link RenderTarget} to output the program's result to
	 */
	public PostProcessingProgram(RenderTarget input, RenderTarget output) {
		this.input = input;
		this.output = output;
	}
	
	/**
	 * Creates an empty {@link PostProcessingProgram} with the specified input
	 * The output {@link RenderTarget} can be obtained with {@link PostProcessingProgram#getOutput()}
	 * Empty programs will not output anything to the output target
	 * 
	 * @param input The {@link RenderTarget} to read from
	 */
	public PostProcessingProgram(RenderTarget input) {
		this(input, new RenderTarget());
	}
	
	/**
	 * Creates an empty {@link PostProcessingProgram}
	 * The input {@link RenderTarget} can be obtained with {@link PostProcessingProgram#getInput()} and the output {@link RenderTarget} with {@link PostProcessingProgram#getOutput()}
	 * Empty programs will not output anything to the output target
	 */
	public PostProcessingProgram() {
		this(new RenderTarget(), new RenderTarget());
	}
	
	/**
	 * Adds an effect to the end of the program - that is, the effect will be executed last
	 * 
	 * @param effect The effect to add
	 */
	public void addEffect(PostProcessingEffect effect) {
		addEffect(effect, effects.size());
	}
	
	/**
	 * Inserts an effect at a specific index in the program
	 * 
	 * @param effect The effect to add
	 * @param index The index to insert the effect at
	 * 
	 * @throws IndexOutOfBoundsException - {@code link < 0 || link > size()}
	 */
	public void addEffect(PostProcessingEffect effect, int index) {
		/* Avoid having render targets bound to multiple I/Os */
		if (effects.contains(effect)) effect = effect.clone();
		
		effects.add(index, effect);
		
		if (index == 0) {
			effect.setInput(input);
			if (effects.size() == 1) {
				effect.setOutput(output);
			} else {
				effects.get(index + 1).setInput(effect.getOutput());
			}
		} else if (index == effects.size() - 1) {
			//Case where the added effect is the only one is handeled above; we can assume that there is at least one effect before this one
			effect.setOutput(output);
			effects.get(index - 1).setOutput(effect.getInput());
		} else {
			effect.setInput(effects.get(index - 1).getOutput());
			effects.get(index + 1).setInput(effect.getOutput());
		}
	}
	
	/**
	 * Removes the effect at the specified index
	 * 
	 * @param index The index of the effect to removes
	 * 
	 * @throws IndexOutOfBoundsException - {@code index < 0 || index >= size()}
	 */
	public void removeEffect(int index) {
		effects.remove(index);
		
		if (index == effects.size()) {
			if (effects.size() == 0) return;
			
			effects.get(index - 1).setOutput(output);
		} else {
			effects.get(index).setInput(index == 0 ? input : effects.get(index - 1).getOutput());
		}
	}
	
	/**
	 * Removes all instances of an effect in the program
	 * 
	 * @param effect The effect to remove
	 */
	public void removeEffect(PostProcessingEffect effect) {
		int index;
		while ((index = effects.indexOf(effect)) != -1) {
			removeEffect(index);
		}
	}
	
	public void setInput(RenderTarget input) {
		this.input = input;
	}
	
	public RenderTarget getInput() {
		return input;
	}
	
	public void setOutput(RenderTarget output) {
		this.output = output;
	}
	
	public RenderTarget getOutput() {
		return output;
	}
	
	public int size() {
		return effects.size();
	}
	
	@Override
	public String toString() {
		return super.toString() + " [input=" + input.getId() + ", output=" + output.getId() + "]";
	}
	
}
