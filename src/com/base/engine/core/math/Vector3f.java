package com.base.engine.core.math;


public class Vector3f {
	private float x;
	private float y;
	private float z;
	
	public Vector3f(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f(Vector2f r, float z){
		this.x = r.getX();
		this.y = r.getY();
		this.z = z;
	}
	public Vector3f(float z){
		this.x = z;
		this.y = z;
		this.z = z;
	}
	
	public float length(){
		return (float)Math.sqrt(x*x + y*y + z*z);
	}	
	
	public float dot(Vector3f r){
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}
	
	public float max(){
		return Math.max(x, Math.max(y, z));
	}
	
	public Vector3f cross(Vector3f r){
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();
		
		return new Vector3f(x_, y_, z_);
	}
	
	public Vector3f normalize(){
		float length = length();
		
		return new Vector3f(x/length, y/length, z/length);
	}
	
	public Vector3f rotate(Quaternion rotation){
		Quaternion conjugate = rotation.conjugate();
		Quaternion w = rotation.mul(this).mul(conjugate);
		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}
	
	public Vector3f rotate(Vector3f axis,float angle){
//		angle = (float)Math.toRadians(angle);
//		float sinHalfAngle = (float)Math.sin(Math.toRadians(angle/2));
//		float cosHalfAngle = (float)Math.cos(Math.toRadians(angle/2));
//		
//		float rX = axis.getX() * sinHalfAngle;
//		float rY = axis.getY() * sinHalfAngle;
//		float rZ = axis.getZ() * sinHalfAngle;
//		float rW = cosHalfAngle;
//		
//		Quaternion rotation = new Quaternion(rX,rY,rZ,rW);
//		Quaternion conjugate = rotation.conjugate();
//		
//		Quaternion w = rotation.mul(this).mul(conjugate);
//		
////		x = w.getX();
////		y = w.getY();
////		z = w.getZ();
//		
//		return new Vector3f(w.getX(), w.getY(), w.getZ());
		
		float sinAngle = (float)Math.sin(-angle);
		float cosAngle = (float)Math.cos(-angle);
		
		return this.cross(axis.mul(sinAngle)).add(	//Rotation on local X
				(this.mul(cosAngle)).add(			//Rotation on local Z
						axis.mul(this.dot(axis.mul(1-cosAngle))))); //Rotation on local Y

	}
	
	public Vector3f abs()
	{
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}	
	
	public Vector3f interpolate(Vector3f destination, float lerpFactor){
		return destination.subtract(this).mul(lerpFactor).add(this);
	}
	
	public Vector3f add(Vector3f r){
		return new Vector3f(x+r.getX(), y+r.getY(), z+r.getZ());
	}
	
	public Vector3f add(float r){
		return new Vector3f(x+r, y+r, z+r);
	}
	
	public Vector3f subtract(Vector3f r){
		return new Vector3f(x-r.getX(), y-r.getY(), z-r.getZ());
	}
	public Vector3f subtract(float r){
		return new Vector3f(x-r, y-r, z-r);
	}
	
	public Vector3f mul(Vector3f r){
		return new Vector3f(x*r.getX(), y*r.getY(), z*r.getZ());
	}
	public Vector3f mul(float r){
		return new Vector3f(x*r, y*r, z*r);
	}
	
	public Vector3f div(Vector3f r){
		return new Vector3f(x/r.getX(), y/r.getY(), z/r.getZ());
	}
	public Vector3f div(float r){
		return new Vector3f(x/r, y/r, z/r);
	}
	
	public String toString(){
		return "(" + x + "," + y + "," + z + ")";
	}
	
	public Vector2f getXY(){
		return new Vector2f(x,y);
	}
	
	public Vector2f getYZ(){
		return new Vector2f(y,z);
	}
	
	public Vector2f getZX(){
		return new Vector2f(z,x);
	}
	public Vector2f getYX(){
		return new Vector2f(y,x);
	}
	
	public Vector2f getZY(){
		return new Vector2f(z,y);
	}
	
	public Vector2f getXZ(){
		return new Vector2f(x,z);
	}
		
	public Vector3f set(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	public Vector3f set(Vector3f vector){
		this.x = vector.getX();
		this.y = vector.getY();
		this.z = vector.getZ();
		return this;
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
	
	public float getZ(){
		return z;
	}
	
	public void setZ(float z){
		this.z = z;
	}
	
	public boolean equals(Vector3f other){
		return x == other.getX() && y == other.getY() && z == other.getZ();
	}
}
