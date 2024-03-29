#version 330
#include "lighting.glh"

in vec2 texCoord0;
in vec3 normal0;
in vec3 worldPos0;

out vec4 fragColor;	

uniform sampler2D diffuse;
uniform DirectionalLight R_directionalLight;

void main(){
	fragColor = texture(diffuse, texCoord0.xy) * calcDirectionalLight(R_directionalLight, normalize(normal0), worldPos0);
}