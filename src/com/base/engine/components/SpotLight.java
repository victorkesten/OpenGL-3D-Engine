package com.base.engine.components;

import com.base.engine.core.math.Vector3f;
import com.base.engine.rendering.Attenuation;
import com.base.engine.rendering.Shader;

public class SpotLight extends PointLight{

	private float cutoff;
	
	public SpotLight(Vector3f color, float intensity, Attenuation atten, float cutoff) {
		super(color, intensity, atten);
		this.cutoff = cutoff;
		setShader(new Shader("forward-spot"));
	}

	public Vector3f getDirection() {
		return getTransform().getTransformedRotation().getForward();
	}
	
	public float getCutoff() {
		return cutoff;
	}

	public void setCutoff(float cutoff) {
		this.cutoff = cutoff;
	}

}
