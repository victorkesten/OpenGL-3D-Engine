package com.base.engine.rendering;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import com.base.engine.components.BaseLight;
import com.base.engine.components.Camera;
import com.base.engine.components.FreeLook;
import com.base.engine.components.FreeMove;
import com.base.engine.core.GameObject;
import com.base.engine.core.Transform;
import com.base.engine.core.math.Matrix4f;
import com.base.engine.core.math.Vector3f;
import com.base.engine.rendering.resourceManagement.MappedValues;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;



public class RenderingEngine extends MappedValues{
	
	private Camera mainCamera;

	//Initialize Open GL. Base state that the game engine should be in.
	//Sets what we want the engine to default to.

	//Permanent Structures
	private ArrayList<BaseLight> lights;
	private BaseLight activeLight;
	private HashMap<String, Integer> samplerMap;
	
	private Shader forwardAmbient;
//	private Material planeMaterial; 
	
	
	//Maybe temp
	private Camera camera;
	private Camera altCamera; 
	private GameObject cameraObject;
	private Texture targetTexture;
	private Mesh targetMesh;
	private Transform targetTransform;
	private Material targetMaterial;
	
	
	
	public RenderingEngine(){
		super();
		lights = new ArrayList<>();
		samplerMap = new HashMap<>();
//		planeMaterial = new Material();
		samplerMap.put("diffuse", 0);
//		samplerMap.put("normal", 1);
		addVector3f("ambientIntensity", new Vector3f(0.5f,0.5f,0.5f));

		glClearColor(0.7f,0.7f,0.7f,0.0f);
		
		glFrontFace(GL_CW);						//Every face in clockwise order
		glCullFace(GL_BACK);					//Get rid off back.
//		glCullFace(GL_FRONT);
		glEnable(GL_CULL_FACE); 				//We draw the cull face. 
		glEnable(GL_DEPTH_TEST);				//Z-index I guess.
//		glfwWindowHint(GLFW_SAMPLES, 4);
		glEnable(GL_MULTISAMPLE);				//This is MSAA
		
		
		camera = new Camera(70f, (float)Window.getWidth()/(float)Window.getHeight(),0.01f, 1000f);
		altCamera = new Camera(70f, (float)Window.getWidth()/(float)Window.getHeight(),0.01f, 1000f);
		targetTexture = new Texture(Window.getWidth(), Window.getHeight(), GL_COLOR_ATTACHMENT0);
		targetMaterial = new Material();
		targetMaterial.addTexture("diffuse", targetTexture);
		targetMaterial.addFloat("specularIntensity", 1);
		targetMaterial.addFloat("specularPower", 8);
		
		//mainCamera = camera;
		//cameraObject = new GameObject().addComponent(new FreeLook()).addComponent(new FreeMove()).addComponent(mainCamera);
		//mainCamera.getTransform().setPosition(new Vector3f (0,4,0));

		targetMesh = new Mesh("plane3.obj");
		//TODO: Depth clamp for later.
//		glEnable(GL_DEPTH);

		glEnable(GL_TEXTURE_2D);
//		glEnable(GL_FRAMEBUFFER_SRGB); 			//Get free gamma correction
		
//		planeMaterial.addTexture("displayTexture", new Texture( Window.getWidth(), Window.getHeight(),GL_COLOR_ATTACHMENT0));
		
	}
	
	public void updateUniformStruct(Transform transform, Material material, Shader shader, String uniformName, String uniformType){
		
		throw new IllegalArgumentException(uniformName + " is not a valid component of Transform");
	}
	
	public void addCamera(Camera camera){
		this.mainCamera = camera;
	}
	public void addShaders(){
		forwardAmbient = new Shader("forward-ambient");
		
	}
	
	public int getSamplerSlot(String samplerName){
		return samplerMap.get(samplerName);
	}
	private boolean createShader;
	
	public void render(GameObject object){
		
		Window.bindAsRenderTarget();
//		planeMaterial.getTexture("displayTexture").bindAsRenderTarget();
//		displayTexture.bindAsRenderTarget();
//		lights.clear();
		clearScreen();
//		object.addToRenderingEngine(this);
	
		//This is needed because of VAO 
		if(!createShader){
			addShaders();
			System.out.println("Shaders Added");
			createShader = true;
		}
//		Shader forwardAmbient = new Sh;

//		FrameBufferObject fb = new FrameBufferObject(Window.getWidth(), Window.getHeight());
		object.renderAll(forwardAmbient, this);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		
		glDepthMask(false);
		
		//Should in theory only be making pixel calculations (shadow/light)
		//For the picture we see in the final image. 
		glDepthFunc(GL_EQUAL);
		
		for(BaseLight light : lights){
			activeLight = light;
			//TODO: Active Light replacement
			object.renderAll(light.getShader(), this);
		}
		
		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}
	
	public void addLight(BaseLight light){
		lights.add(light);
	}
	

	//Clears the screen
	private static void clearScreen(){
		//TODO: Stencil Buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	

	
	public static String getOpenGLVersion(){
		return glGetString(GL_VERSION);
	}
	
	public Camera getMainCamera(){
		return mainCamera;
	}

	
	public BaseLight getActiveLight(){
		return activeLight;
	}
	
	public void setMainCamera(Camera mainCamera){
		this.mainCamera = mainCamera;
	}

}
