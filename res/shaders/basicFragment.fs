#version 330

//in vec4 color;

in vec2 texCoord0;
out vec4 fragColor;


uniform vec3 color;
uniform sampler2D sampler;

//There is a prblem with transparent and non-existing texture.

void main(){
	vec4 textureColor = texture(sampler, texCoord0.xy);
	if(textureColor.x == 0 && textureColor.y == 0 && textureColor.z == 0) 	//textureColor.xyz == vec3(0,0,0)
		fragColor = vec4(color, 1.0f);
	else 
		fragColor = textureColor * vec4(color, 1.0f);
	
	//fragColor = texture(sampler, texCoord0.xy) * vec4(color,1.0f);//vec4(0,0,0,0);//color;
	//gl_FragColor = texture2D(sampler, texCoord0.xy);
}
