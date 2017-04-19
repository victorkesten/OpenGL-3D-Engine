package com.base.engine.rendering;

import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;
 
public class FrameBufferObject {
 
    private int frameBufferID;
    private int colorTextureID;
    private int depthRenderBufferID;
     
    //dimensions
    private int FRAME_WIDTH;
    private int FRAME_HEIGHT;
     
    public FrameBufferObject(int width, int height){
        FRAME_WIDTH = width;
        FRAME_HEIGHT = height;
         
        frameBufferID = glGenFramebuffersEXT();
        colorTextureID = glGenTextures();
        depthRenderBufferID = glGenRenderbuffersEXT();
         
        //frame buffer object
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, frameBufferID);
         
        //color texture
        glBindTexture(GL_TEXTURE_2D, colorTextureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB8, FRAME_WIDTH, FRAME_HEIGHT, 0, GL_RGBA, GL_INT, (java.nio.ByteBuffer)null);
        glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0_EXT, GL_TEXTURE_2D, colorTextureID, 0);
     
        //depth buffer
        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, depthRenderBufferID);
        glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL_DEPTH_COMPONENT, FRAME_WIDTH, FRAME_HEIGHT);
        glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_ATTACHMENT_EXT, GL_RENDERBUFFER_EXT, depthRenderBufferID);
     
         
        //check completeness
        if(glCheckFramebufferStatusEXT(GL_FRAMEBUFFER_EXT) == GL_FRAMEBUFFER_COMPLETE_EXT){
            System.out.println("Frame buffer created sucessfully.");
        }
        else
            System.out.println("An error occured creating the frame buffer.");
         
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
    }
     
    public void Begin(int width, int height){
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, height, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glViewport(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, frameBufferID);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
     
    public void End(){
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
    }
     
    public int getTexture(){
        return colorTextureID;
    }
}