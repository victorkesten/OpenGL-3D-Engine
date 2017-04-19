package com.base.engine.components;

import com.base.engine.core.CoreEngine;
import com.base.engine.core.math.Matrix4f;
import com.base.engine.core.math.Vector3f;

public class Camera extends GameComponent{

	
	private Matrix4f projection;

	public Camera(float fov, float aspect, float zNear, float zFar){

		this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
	}
	
	public Matrix4f getViewProjection(){
		Matrix4f cameraRotation = getTransform().getTransformedRotation().conjugate().toRotationMatrix();
		Vector3f cameraPosition = getTransform().getTransformPosition().mul(-1);
		
		Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPosition.getX(), cameraPosition.getY(), cameraPosition.getZ());

		return projection.mul(cameraRotation.mul(cameraTranslation));
	}
	
	@Override
	public void addToEngine(CoreEngine engine){
		engine.getRenderingEngine().addCamera(this);
	}

}
