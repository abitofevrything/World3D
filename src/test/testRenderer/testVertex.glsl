#version 330

in vec3 position;
in vec2 texCoords;

out vec2 textureCoords;

uniform mat4 projectionViewMatrix;
uniform mat4 transformationMatrix;

void main(void) {

	textureCoords = texCoords;
	
	gl_Position = projectionViewMatrix * transformationMatrix * vec4(position, 1.0);
	
}