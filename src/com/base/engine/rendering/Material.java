package com.base.engine.rendering;

import java.util.HashMap;

import com.base.engine.rendering.resourceManagement.MappedValues;

public class Material extends MappedValues{
	
	private HashMap<String, Texture> textureHashMap;
//	private Texture texture;
//	private Vector3f color;
//	private float specularIntensity;
//	private float specularPower;
//	


	public Material(){
		super();
		textureHashMap = new HashMap<>();
//		this.texture = texture;
//		this.color = color;
//		this.specularPower = specularPower;
//		this.specularIntensity = specularIntensity;
	}

	public void addTexture(String name, Texture texture){
		textureHashMap.put(name, texture);
	}
	
	public Texture getTexture(String name){
		Texture result = textureHashMap.get(name);
		if(result != null){
			return result;
		}
		return new Texture("test.png");
	}


}
