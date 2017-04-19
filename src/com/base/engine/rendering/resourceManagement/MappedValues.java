package com.base.engine.rendering.resourceManagement;

import java.util.HashMap;

import com.base.engine.core.math.Vector3f;
import com.base.engine.rendering.Texture;

public abstract class MappedValues {
	private HashMap<String, Vector3f> vector3fHashMap;
	private HashMap<String, Float> floatHashMap;
	private HashMap<String, Texture> textureHashMap;
	public MappedValues(){
		vector3fHashMap = new HashMap<String, Vector3f>();
		floatHashMap = new HashMap<>();
		textureHashMap = new HashMap<>();
	}
	
	public void addVector3f(String name, Vector3f vector){
		vector3fHashMap.put(name, vector);
	}
	
	public Vector3f getVector3f(String name){
		Vector3f result = vector3fHashMap.get(name);
		if(result != null){
			return result;
		}
		return new Vector3f(0,0,0);
	}	
	public void addFloat(String name, float number){
		floatHashMap.put(name, number);
	}
	
	public float getFloat(String name){
		Float result = floatHashMap.get(name);
		if(result != null){
			return result;
		}
		return 0;
	}
	
	public void addTexture(String name, Texture texture){
		textureHashMap.put(name, texture);
	}
	
	public Texture getTexture(String name){
		Texture result = textureHashMap.get(name);
		if(result != null){
			return result;
		}
		return null;
	}
}
