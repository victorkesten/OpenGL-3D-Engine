package com.base.engine.rendering;

import com.base.engine.core.math.Vector2f;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import static org.lwjgl.opengl.EXTFramebufferObject.*;


import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.opengl.GL30.*;
//import static org.lwjgl.opengl.ARBFramebufferObject.*;

/**
 * Creates and renders our window. 
 * @author Kesten
 *
 */
public class Window {

	/**
	 * Creates our window.
	 * Currently supports only MacBook OpenGL due to constraints with newer versions
	 * @param width
	 * @param height
	 * @param title
	 */
	public static void createWindow(int width, int height, String title){
		Display.setTitle(title);
		
		try {
//			PixelFormat pixelFormat = new PixelFormat(8,8,8,8);
			PixelFormat pixelFormat = new PixelFormat().withSamples(16);
			//Explicitly tells my macbook to use this version of GL.
			ContextAttribs contextAttributes = new ContextAttribs(3,2).withProfileCore(true);
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create(pixelFormat, contextAttributes);
			//This following is what would only be needed if we had no contextAttributes.
			//Display.create();
			Keyboard.create();
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
//		Display.setTitle(title);
//		try 
//		{
//			Display.setDisplayMode(new DisplayMode(width, height));
//			Display.create();
//			Keyboard.create();
//			Mouse.create();
//		} 
//		catch (LWJGLException e) 
//		{
//			e.printStackTrace();
//		}
	}
	
	//NEW
	public static void bindAsRenderTarget(){
//		GL30.glBindFrameBuffer(GL30.GL_FRAMEBUFFER, 0);
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
       
        glViewport(0,0,Window.getWidth(), Window.getHeight());
	}
	
	/**
	 * updates screen from graphics card. 
	 */
	public static void render(){
		Display.update();
	}
	/**
	 * isCloseRequested
	 */
	public static boolean isCloseRequested(){		
		return Display.isCloseRequested();
	}
	/**
	 * @return width
	 */
	public static int getWidth(){
		return Display.getDisplayMode().getWidth();
	}
	
	/**
	 * @return height of screen
	 */
	public static int getHeight(){
		return Display.getDisplayMode().getHeight();
	}
	
	/**
	 * returns the head bar title.
	 * @return Display title
	 */
	public static String getTitle(){
		return Display.getTitle();
	}
	
	/**
	 * Disposes of Display, Keyboard and Mouse.
	 * Acknolwedges that we're done with these. 
	 */
	public static void dispose(){
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
	}

	/**
	 * 
	 * @return A Vector2f of center pixels of screen.
	 */
	public Vector2f getCenter(){
		return new Vector2f(getWidth()/2, getHeight()/2);
	}
}
