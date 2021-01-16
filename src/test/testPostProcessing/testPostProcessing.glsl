#version 330

in vec2 textureCoordinates;

uniform sampler2D depthInput;
uniform sampler2D colourInput;

out vec4 colourOutput;

void main(void) {
	colourOutput = texture(depthInput, textureCoordinates);
	colourOutput = texture(colourInput, textureCoordinates);
	
	colourOutput = vec4(1.0);
}