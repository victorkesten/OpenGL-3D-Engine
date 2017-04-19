#version 330
#include "lighting.glh"


in vec2 texCoord0;
in vec3 normal0;
in vec3 worldPos0;

out vec4 fragColor;	



uniform sampler2D diffuse;


uniform PointLight R_pointLight;

void main(){
	fragColor = texture(diffuse, texCoord0.xy) * calcPointLight(R_pointLight, normalize(normal0), worldPos0);
}
