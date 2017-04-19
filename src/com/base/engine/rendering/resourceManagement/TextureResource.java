package com.base.engine.rendering.resourceManagement;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
//import static org.lwjgl.opengl.GL15.GL_TEXTURE_2D_MULTISAMPLE;

import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.glDrawBuffers;
import static org.lwjgl.opengl.EXTFramebufferObject.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.base.engine.core.Util;


public class TextureResource {
	private int id;
	private int refCount;
	private int numTextures;
	private IntBuffer textureID;
	private int width;
	private int height;
	private int frameBuffer;
	private int renderBuffer;
	public TextureResource(int width, int height, ByteBuffer[] data, float[] filter, int[] attachements){
		this(1, width, height, data, filter, attachements);
	}
	
	public TextureResource(int numTextures, int width, int height, ByteBuffer[] data, float[] filter, int[] attachements){
//		this.id = glGenTextures();
		this.numTextures = numTextures;
		this.textureID = Util.createIntBuffer(numTextures);
		this.refCount = 1;
		this.width = width;
		this.height = height; 
		this.frameBuffer = 0;
		this.renderBuffer = 0;
		initTextures(data, filter);
		initRenderTarget(attachements);
	}
	
	public void initRenderTarget(int[] attachements){
		//System.out.println("CHECK");

		if(attachements == null){
			return;
		}
		
		int[] drawBuffers = new int[numTextures];
		boolean hasDepth = false;
		
		for(int i = 0; i < numTextures; i++){
			if(attachements[i] == GL_DEPTH_ATTACHMENT){
				drawBuffers[i] = GL_NONE;
				hasDepth = true;
			} else{
				drawBuffers[i] = attachements[i];
			}
			if(attachements[i] == GL_NONE){
				continue;
			}
			if(frameBuffer == 0){
				frameBuffer = glGenFramebuffersEXT();
		        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, frameBuffer);
			}
	        glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, attachements[i], GL_TEXTURE_2D, textureID.get(i), 0);	
		}
		if(frameBuffer == 0){
			return;
		}
		if(!hasDepth){
			frameBuffer = glGenRenderbuffersEXT();
//			frameBuffer = glGenFra
	        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, frameBuffer);
	        glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL_DEPTH_COMPONENT, width, height);
	        glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_ATTACHMENT_EXT, GL_RENDERBUFFER_EXT, renderBuffer);
		}
		glDrawBuffers(Util.createFlippedBuffer(drawBuffers));
//		glDr
		if(glCheckFramebufferStatusEXT(GL_FRAMEBUFFER_EXT) != GL_FRAMEBUFFER_COMPLETE_EXT){
			System.out.println("Framebuffer Error");
			System.exit(0);
		}
		
	}
	
	public void initTextures(ByteBuffer[] data, float[] filter){
		glGenTextures(textureID);

		for (int i = 0; i < numTextures; i++){
			glBindTexture(GL_TEXTURE_2D, textureID.get(i));
//			System.out.println(fileName);

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data[i]);
		}
	}
	
	public void bind(int textureNum){
		glBindTexture(GL_TEXTURE_2D,textureID.get(textureNum));

	}
	public void bindAsRenderTarget(){
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, frameBuffer);
        glViewport(0,0,width, height);
	}
	
	//This needs fixing
	@Override
	protected void finalize(){
		glDeleteBuffers(id);
	}
	
	public void addReference(){
		refCount++;
	}
	public boolean removeReference(){
		refCount--;
		return refCount == 0;
	}
	
	public void addNumTexture(){
		numTextures++;
	}
	
	public boolean removeNumTexture(){
		numTextures--;
		return numTextures == 0;
	}

//	public int getId(){
//		return id;
//	}
	public int getId(){
		return textureID.get(0);
	}
	
	public IntBuffer getTextureId(){
		return textureID;
	}
}
