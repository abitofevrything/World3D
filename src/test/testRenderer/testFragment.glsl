#version 330

in vec2 textureCoords;

out vec4 out_Colour;

uniform sampler2D textureSampler;

void main(void) {
	
	out_Colour = texture(textureSampler, textureCoords);
	
}