#version 330

in vec2 textureCoordinates;

uniform sampler2D colourInput;

out vec4 colourOutput;

void main(void) {
	colourOutput = vec4(1.0) - texture(colourInput, textureCoordinates);
}