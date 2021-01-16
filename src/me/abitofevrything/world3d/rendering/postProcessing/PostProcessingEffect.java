package me.abitofevrything.world3d.rendering.postProcessing;

import org.lwjgl.opengl.GL11;

import me.abitofevrything.world3d.events.EventSubscribable;
import me.abitofevrything.world3d.events.output.RenderEvent;
import me.abitofevrything.world3d.events.output.RenderEventListener;
import me.abitofevrything.world3d.rendering.RenderTarget;
import me.abitofevrything.world3d.rendering.opengl.Vao;
import me.abitofevrything.world3d.util.ResourceFile;

public class PostProcessingEffect extends EventSubscribable implements Cloneable {
	
	private static final Vao QUAD = /*CubeGenerator.generateCube(1);*/ Vao.create();
	
	static {
		QUAD.bind();
		
		int[] indices = {
			0, 1, 2,
			1, 2, 3
		};
			
		QUAD.createIndexBuffer(indices);
		
		float[] positions = {
			-1, -1,
			 1, -1,
			-1,  1,
			 1,  1
		};
		QUAD.createAttribute(0, positions, 2);
		
		QUAD.unbind();
	}
	
	private PostProcessingShader shader;
	private RenderTarget output, input;
	
	private RenderEventListener inputUpdateListener;
	
	public PostProcessingEffect(String fragmentShader, RenderTarget input, RenderTarget output) {
		this.shader = new PostProcessingShader(new ResourceFile(fragmentShader));
		this.input = input;
		this.output = output;
		
		inputUpdateListener = new RenderEventListener() {
			
			@Override
			public void onEvent(RenderEvent event) {
				System.out.println("PPE rendering from target " + input.getId() + " to " + output.getId());
				
				output.bindToRenderOutput();
				shader.start();
				
				QUAD.bind(0);
				
				if (shader.hasColourInput()) shader.colourInput.loadAndBindTexture(input.getColourTexture(), 0);
				if (shader.hasDepthInput()) shader.depthInput.loadAndBindTexture(input.getDepthTexture(), 1);
				
				GL11.glDrawElements(GL11.GL_TRIANGLES, QUAD.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
				
				QUAD.bind(0);
				
				shader.stop();
				
				triggerEventForSubscribers(new RenderEvent("render"));
				output.finishRender();
			}
		};
		
		input.subscribeToEvent(inputUpdateListener);
	}
	
	public PostProcessingEffect(String shader) {
		this(shader, new RenderTarget(), new RenderTarget());
	}
	
	public PostProcessingEffect(PostProcessingShader shader, RenderTarget input, RenderTarget output) {
		this.shader = shader;
		this.output = output;
		this.input = input;
		
		inputUpdateListener = new RenderEventListener() {
			
			@Override
			public void onEvent(RenderEvent event) {
				output.bindToRenderOutput();
				shader.start();
				
				QUAD.bind(0);
				
				if (shader.hasColourInput()) shader.colourInput.loadAndBindTexture(input.getColourTexture(), 0);
				if (shader.hasDepthInput()) shader.depthInput.loadAndBindTexture(input.getDepthTexture(), 1);
				
				GL11.glDrawElements(GL11.GL_TRIANGLES, QUAD.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
				
				QUAD.bind(0);
				
				shader.stop();
				
				triggerEventForSubscribers(new RenderEvent("render"));
				output.finishRender();
			}
		};
		
		input.subscribeToEvent(inputUpdateListener);
	}
	
	public PostProcessingEffect(String fragmentShader, RenderTarget input) {
		this(fragmentShader, new RenderTarget(), input);
	}

	public void setOutput(RenderTarget target) {
		this.output = target;
	}
	
	public RenderTarget getOutput() {
		return output;
	}

	public void setInput(RenderTarget input) {
		/* Unsubscribe from current input updates to avoid hanging listeners */
		this.input.unsubscribeFromEvent(inputUpdateListener);
		
		this.input = input;
		
		this.input.subscribeToEvent(inputUpdateListener);
	}
	
	public RenderTarget getInput() {
		return input;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PostProcessingEffect)) return false;
		return ((PostProcessingEffect)o).shader.equals(shader);
	}
	
	@Override
	public PostProcessingEffect clone() {
		return new PostProcessingEffect(shader, new RenderTarget(), new RenderTarget());
	}
}
