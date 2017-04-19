#version 330

in vec2 texCoord0;
out vec4 fragColor;

uniform vec3 R_ambientIntensity;
uniform sampler2D diffuse;

void main(){
	fragColor = texture(diffuse, texCoord0.xy) * vec4(R_ambientIntensity, 1);

}