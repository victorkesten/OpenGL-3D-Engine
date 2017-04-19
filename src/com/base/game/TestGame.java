package com.base.game;

import com.base.engine.components.Camera;
import com.base.engine.components.DirectionalLight;
import com.base.engine.components.FreeLook;
import com.base.engine.components.FreeMove;
import com.base.engine.components.MeshRenderer;
import com.base.engine.components.PointLight;
import com.base.engine.components.SpotLight;
import com.base.engine.core.Game;
import com.base.engine.core.GameObject;
import com.base.engine.core.math.Quaternion;
import com.base.engine.core.math.Vector2f;
import com.base.engine.core.math.Vector3f;
import com.base.engine.rendering.Attenuation;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Texture;
import com.base.engine.rendering.Vertex;
import com.base.engine.rendering.Window;

public class TestGame extends Game {
	
	/**
	 * Initializes (And currently) creates each individual object of what we see on the screen.
	 */
	public void init(){
//		camera = new Camera();
//		Vertex[] vertices = new Vertex[] { new Vertex( new Vector3f(-1.0f, -1.0f, 0.5773f),	new Vector2f(0.0f, 0.0f)),
//							new Vertex( new Vector3f(0.0f, -1.0f, -1.15475f),		new Vector2f(0.5f, 0.0f)),
//							new Vertex( new Vector3f(1.0f, -1.0f, 0.5773f),		new Vector2f(1.0f, 0.0f)),
//							new Vertex( new Vector3f(0.0f, 1.0f, 0.0f),      new Vector2f(0.5f, 1.0f)) };
//				
//		int indices[] = { 0, 3, 1,
//						1, 3, 2,
//						2, 3, 0,
//						1, 2, 0 };
		
		float fieldDepth = 14.0f;
		float fieldWidth = 14.0f;
		
		float checkD = 10.0f;
		float checkW = 10.0f;
		
		Vertex[] vertices = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
											new Vertex( new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
											new Vertex( new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
											new Vertex( new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};
		int indices[] = {	0,1,2,
							2,1,3 };
		
		Vertex[] vertices2 = new Vertex[] { 	new Vertex( new Vector3f(-checkW/ 10, 0.0f, -checkD/ 10), new Vector2f(0.0f, 0.0f)),
				new Vertex( new Vector3f(-checkW/ 10, 0.0f, checkD/ 10 * 3), new Vector2f(0.0f, 1.0f)),
				new Vertex( new Vector3f(checkW/ 10 * 3, 0.0f, -checkD/ 10), new Vector2f(1.0f, 0.0f)),
				new Vertex( new Vector3f(checkW/ 10 * 3, 0.0f, checkD/ 10 * 3), new Vector2f(1.0f, 1.0f))};

		int indices2[] = { 0, 1, 2,
				2, 1, 3};

		Mesh mesh2 = new Mesh(vertices2, indices2, true);
		
		Mesh mesh = new Mesh(vertices, indices, true);
		Material material = new Material();//new Texture("test.png"), new Vector3f(1,1,1), 1, 8);
		material.addTexture("diffuse", new Texture("bricks.jpg"));
		material.addFloat("specularIntensity", 1);
		material.addFloat("specularPower", 8);
		
		Material chckeredMaterial = new Material();//new Texture("test.png"), new Vector3f(1,1,1), 1, 8);
		chckeredMaterial.addTexture("diffuse", new Texture("checkered.jpg"));
		chckeredMaterial.addFloat("specularIntensity", 1);
		chckeredMaterial.addFloat("specularPower", 8);
		
		Material material2 = new Material();//new Texture("test.png"), new Vector3f(1,1,1), 1, 8);
		material2.addTexture("diffuse", new Texture("test.png"));
		material2.addFloat("specularIntensity", 1);
		material2.addFloat("specularPower", 8);
		
		Material materialTower = new Material();
		materialTower.addTexture("diffuse", new Texture("Wood_Tower_Col.jpg"));
		materialTower.addFloat("specularIntensity", 2);
		materialTower.addFloat("specularPower", 12);
		Mesh towerMesh = new Mesh("wooden watch tower2.obj");
		Mesh tempMesh = new Mesh("monkey3.obj");
		
		MeshRenderer meshRenderer = new MeshRenderer(mesh, chckeredMaterial);
//		MeshRenderer mesh
		GameObject planeObject = new GameObject();
		planeObject.addComponent(meshRenderer);
		planeObject.getTransform().getPosition().set(0,-1,5);
		
		GameObject directionalLightObject = new GameObject();
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1,1,1), 0.4f);
		directionalLightObject.addComponent(directionalLight);
		directionalLight.getTransform().setRotation(new Quaternion(new Vector3f(1,0,0), (float)Math.toRadians(-45)));

		GameObject pointLightObject = new GameObject();
		pointLightObject.addComponent(new PointLight(new Vector3f(0,1,0), 0.8f, new Attenuation(0,0,1)));
		
		GameObject spotLightObject = new GameObject();
//		SpotLight spotLight = new SpotLight(new Vector3f(0,1,1), 0.4f,
//				new Vector3f(0,0,0.1f), 
//				new Vector3f(0,0,0), 100, 
//				new Vector3f(1,0,0), 
//				0.7f);
		SpotLight spotLight = new SpotLight(new Vector3f(0,1,1), 0.4f, new Attenuation(0, 0, 0.1f), 0.7f);
		spotLightObject.addComponent(spotLight);
		
		spotLight.getTransform().getPosition().set(5,0,5);
		spotLight.getTransform().setRotation(new Quaternion().initRotation(new Vector3f(0,1,0), (float)Math.toRadians(-90)));
		
		GameObject spotLightObject2 = new GameObject();
		SpotLight spotLight2 = new SpotLight(new Vector3f(1,0,0), 0.4f, new Attenuation(0,0,0.1f), 0.7f);
		spotLightObject2.addComponent(spotLight2);
		
		spotLight2.getTransform().getPosition().set(4,2.2f,5);
		
		addObject(planeObject);
		addObject(directionalLightObject);
		addObject(pointLightObject);
		addObject(spotLightObject);
		addObject(spotLightObject2);
		
//		getRootObject().addChild(new GameObject().addComponent(new Camera(70f, (float)Window.getWidth()/(float)Window.getHeight(),0.01f, 1000f)));
		GameObject testMesh1 = new GameObject().addComponent(new MeshRenderer(mesh2, material));
		GameObject testMesh2 = new GameObject().addComponent(new MeshRenderer(mesh2, material));
		GameObject testMesh3 = new GameObject().addComponent(new MeshRenderer(tempMesh, material));
		GameObject testMesh4 = new GameObject().addComponent(new MeshRenderer(towerMesh, materialTower));
		testMesh4.getTransform().getPosition().set(9,-1,7);
		testMesh1.getTransform().getPosition().set(0,2,0);
		testMesh1.getTransform().setRotation(new Quaternion(new Vector3f(0,1,0), 0.4f));
		
		testMesh2.getTransform().getPosition().set(0,0,5);
		
		testMesh1.addChild(testMesh2);
//		testMesh1.addChild(spotLightObject2);
		Camera camera = new Camera(70f, (float)Window.getWidth()/(float)Window.getHeight(),0.01f, 1000f);
		testMesh2.addChild(new GameObject().addComponent(new FreeLook()).addComponent(new FreeMove()).addComponent(camera));
		
		//camera.getTransform().setPosition(new Vector3f(7.301537f,-2.924207f,-7.3472514f));
		//camera.getTransform().setRotation(new Quaternion(0.43300557f, 0.3603479f, -0.19429785f, 0.8030591f));
		
		
		//Far View
		//camera.getTransform().setPosition(new Vector3f(-9.241374f,1.7459126f,-17.497437f));
		//camera.getTransform().setRotation(new Quaternion(0.15474705f, 0.19546048f, -0.03124945f, 0.9679215f));
		
		//(4.564978,0.9520862,5.1487665)
		//(0.116309, 0.20925932, -0.025076203, 0.9705947)
		
		//Inside Tower
		camera.getTransform().setPosition(new Vector3f(4.564978f,0.9520862f,5.1487665f));
		camera.getTransform().setRotation(new Quaternion(0.116309f, 0.20925932f, -0.025076203f, 0.9705947f));
		
		//On Square
		//(-1.714546,-2.8340538,-8.129922)
		//(-0.12923704, -0.80589783, 0.5452931, -0.19100234)
	/*	camera.getTransform().setPosition(new Vector3f(-1.714546f,-2.8340538f,-8.129922f));
		camera.getTransform().setRotation(new Quaternion(-0.12923704f, -0.80589783f, 0.5452931f, -0.19100234f));*/

		testMesh3.getTransform().getPosition().set(5,5,5);
		testMesh3.getTransform().getRotation().set(new Quaternion(new Vector3f(0,1,0), (float)Math.toRadians(70f)));
		addObject(testMesh1);
		addObject(testMesh3);
		addObject(testMesh4);
		addObject(new GameObject().addComponent(new MeshRenderer(new Mesh("monkey3.obj"), material2)));
		
		
//		Transform.setProjection(70f, Window.getWidth(), Window.getHeight(), 0.1f, 1000);
//		Transform.setCamera(camera);		
	}
}
