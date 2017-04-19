package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.math.Vector2f;
import com.base.engine.core.math.Vector3f;
import com.base.engine.rendering.Window;

public class FreeLook extends GameComponent{
	
	public static final Vector3f yAxis = new Vector3f(0,1,0);
	private boolean mouseLocked = false;
	private float sensitivity;
	private int unlockMouseKey;
	private Vector2f centerPosition = new Vector2f(Window.getWidth()/2, Window.getHeight()/2);
	
	public FreeLook(){
		this(0.5f, Input.KEY_ESCAPE);
	}
	
	public FreeLook(float sensitivity){
		this(sensitivity, Input.KEY_ESCAPE);
	}
	
	public FreeLook(float sensitivity, int unlockMouseKey){
		this.sensitivity = sensitivity;
		this.unlockMouseKey = unlockMouseKey;
	}
	
	@Override
	public void input(float delta){
		float rotationAmount = (float)(100 * delta);
//		float sensitivity = 0.5f;

		if(Input.getKey(unlockMouseKey)){
			Input.setCursor(true);
			mouseLocked = false;
		}
		if(Input.getMouseDown(0)){
			Input.setMousePosition(centerPosition);
			Input.setCursor(false);
			mouseLocked = true;
		}
		
		if (Input.getKey(Input.KEY_0)){
			System.out.println(getTransform().getPosition());
			System.out.println(getTransform().getRotation().toString());

		}
		
		if(Input.getKey(Input.KEY_UP)){
			getTransform().rotate(getTransform().getRotation().getLeft(), ((float)Math.toRadians(rotationAmount)));
//			getTransform().setRotation(getTransform().getRotation().mul(new Quaternion().initRotation(getTransform().getTransformedRotation().getLeft(), ((float)Math.toRadians(-rotationAmount)))).normalized());
//			rotateX(-rotationAmount);
		}
		if(Input.getKey(Input.KEY_DOWN)){
			getTransform().rotate(getTransform().getRotation().getLeft(), ((float)Math.toRadians(-rotationAmount)));
//			getTransform().setRotation(getTransform().getRotation().mul(new Quaternion().initRotation(getTransform().getTransformedRotation().getLeft(), ((float)Math.toRadians(rotationAmount)))).normalized());
///			rotateX(rotationAmount);
		}
		if(Input.getKey(Input.KEY_LEFT)){
//			rotateY(-rotationAmount);
			getTransform().rotate(yAxis, ((float)Math.toRadians(-rotationAmount)));
//			getTransform().setRotation(getTransform().getRotation().mul(new Quaternion().initRotation(yAxis, ((float)Math.toRadians(rotationAmount)))).normalized());
		}
		if(Input.getKey(Input.KEY_RIGHT)){
			getTransform().rotate(yAxis, ((float)Math.toRadians(rotationAmount)));
//			getTransform().setRotation(getTransform().getRotation().mul(new Quaternion().initRotation(yAxis, ((float)Math.toRadians(-rotationAmount)))).normalized());

//			rotateY(rotationAmount);
		}
		
		if(mouseLocked){
			Vector2f deltaPos = Input.getMousePosition().subtract(centerPosition);
			
			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;
			
			if(rotY){
				getTransform().rotate(yAxis, ((float)Math.toRadians(deltaPos.getX() * sensitivity)));
//				getTransform().setRotation(getTransform().getRotation().mul(new Quaternion().initRotation(yAxis, ((float)Math.toRadians(deltaPos.getX() * sensitivity)))).normalized());
//				rotateY((float)Math.toRadians(deltaPos.getX() * sensitivity));
			}
			if(rotX){
				getTransform().rotate(getTransform().getRotation().getRight(), ((float)Math.toRadians(-deltaPos.getY() * sensitivity)));
//				getTransform().setRotation(getTransform().getRotation().mul(new Quaternion().initRotation(getTransform().getTransformedRotation().getRight(), ((float)Math.toRadians(-deltaPos.getY() * sensitivity)))).normalized());
//				rotateX((float)Math.toRadians(-deltaPos.getY() * sensitivity));
			}
			if(rotY || rotX){
				Input.setMousePosition(new Vector2f(Window.getWidth()/2, Window.getHeight()/2));
			}
		}
	}
}
