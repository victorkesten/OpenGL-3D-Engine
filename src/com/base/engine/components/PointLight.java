package com.base.engine.components;

import com.base.engine.core.math.Vector3f;
import com.base.engine.rendering.Attenuation;
import com.base.engine.rendering.Shader;

public class PointLight extends BaseLight {
	
	private static final int COLOR_DEPTH = 256;
	
	private float range;
	private float constant;
	private float linear;
	private float exponent;
	private Attenuation atten;
	
	public PointLight(Vector3f color, float intensity, Attenuation atten){
		super(color, intensity);
		this.constant = atten.getConstant();
		this.linear = atten.getLinear();
		this.exponent = atten.getExponent();
		this.atten = atten;
		float a = exponent;
		float b = linear;
		float c = constant - COLOR_DEPTH * getIntensity() * getColor().max();; 
				
		this.range = (float)(-b + Math.sqrt(b*b - 4*a*c))/(2*a); //1000; //TODO: Calculate
		 
		
		setShader(new Shader("forward-point"));
	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}


	public Attenuation getAtten() {
		return atten;
	}


	public void setExponent(float exponent) {
		this.exponent = exponent;
	}

}
