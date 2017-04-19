package com.base.engine.core.math;

public class Vector2f {

	private float x;
	private float y;
	
	public Vector2f(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public float length(){
		return (float)Math.sqrt(x*x + y*y);
	}
	
	public float dot(Vector2f r){
		return x * r.getX() + y * r.getY();
	}
	
	public float max(){
		return Math.max(x, y);
	}
	
	public Vector2f normalize(){
		float length = length();
		
		return new Vector2f(x/length, y/length);
	}
	
	public float cross(Vector2f other){
		return x * other.getY() - y * other.getX();
	}
	
	public Vector2f rotate(float angle){
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float)(x*cos - y *sin), (float)(x*sin + y *cos));
	}
	
	public Vector2f interpolate(Vector2f destination, float lerpFactor){
		return destination.subtract(this).mul(lerpFactor).add(this);
	}
	
	
	public Vector2f add(Vector2f r){
		return new Vector2f(x+r.getX(), y+r.getY());
	}
	
	public Vector2f add(float r){
		return new Vector2f(x+r, y+r);
	}
	
	public Vector2f subtract(Vector2f r){
		return new Vector2f(x-r.getX(), y-r.getY());
	}
	public Vector2f subtract(float r){
		return new Vector2f(x-r, y-r);
	}
	
	public Vector2f mul(Vector2f r){
		return new Vector2f(x*r.getX(), y*r.getY());
	}
	public Vector2f mul(float r){
		return new Vector2f(x*r, y*r);
	}
	
	public Vector2f div(Vector2f r){
		return new Vector2f(x/r.getX(), y/r.getY());
	}
	public Vector2f div(float r){
		return new Vector2f(x/r, y/r);
	}
	
	public String toString(){
		return "(" + x + " " + y + ")";
	}
	
	public float getX(){
		return x;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public float getY(){
		return y;
	}
	
	public void setY(float y){
		this.y = y;
	}
	
	public Vector2f set(Vector2f vector){
		this.x = vector.getX();
		this.y = vector.getY();
		return this;
	}
	
	public Vector2f set(float x, float y){
		this.x = x;
		this.y = y;
		return this;
	}
	
	public Vector2f abs(){
		return new Vector2f((float)Math.abs(getX()), (float)Math.abs(getY()));
	}
	
	public boolean equals(Vector2f other){
		return x == other.getX() && y == other.getY();
	}
}

