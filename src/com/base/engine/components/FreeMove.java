package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.math.Vector3f;

public class FreeMove extends GameComponent {

	private int rightKey;
	private int leftKey;
	private int forwardKey;
	private int backKey;
	private float speed;
	public FreeMove(){
		this(10, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D);
	}
	
	public FreeMove(float speed){
		this(speed, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D);
	}
	
	public FreeMove(float speed, int forwardKey, int backKey, int leftKey, int rightKey){
		this.speed = speed;
		this.forwardKey = forwardKey;
		this.backKey = backKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
	}
	@Override
	public void input(float delta){
		float moveAmount = (float)(speed * delta);
		
		if(Input.getKey(forwardKey)){
			move(getTransform().getRotation().getForward(), moveAmount);
		}
		if(Input.getKey(backKey)){
			move(getTransform().getRotation().getForward(), -moveAmount);
		}
		if(Input.getKey(leftKey)){
			move(getTransform().getRotation().getLeft(), moveAmount);
		}
		if(Input.getKey(rightKey)){
			move(getTransform().getRotation().getRight(), moveAmount);
		}
	}
	
	
	
	public void move(Vector3f direction, float amount){
		getTransform().setPosition(getTransform().getPosition().add(direction.mul(amount)));
	}

}
