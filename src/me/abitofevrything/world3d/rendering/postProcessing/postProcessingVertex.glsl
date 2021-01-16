#version 330

in vec2 position;

out vec2 textureCoordinates;

void main(void) {

	gl_Position = vec4(position, 0.0, 1.0);
	textureCoordinates = position * 0.5 + 0.5;

}