package com.base.engine.rendering;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.base.engine.rendering.resourceManagement.TextureResource;
//import org.newdawn.slick.opengl.TextureLoader;
import com.base.engine.core.Util;

import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
//import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL32.*;

//import static org.lwjgl.opengl.ARBMultisample.GL_TEXTURE_2D_MULTISAMPLE;

public class Texture {
	
	private static HashMap<String, TextureResource> loadedTextures = new HashMap<>();
	private TextureResource resource;
	private String fileName;
	private int attachement; 
	
	public Texture(String fileName){
		this(fileName, GL_NONE);
	}
	
	public Texture(String fileName, int attachement){
		TextureResource oldResource = loadedTextures.get(fileName);
		this.fileName = fileName;
		this.attachement = attachement;
		
		if(oldResource != null){
			resource = oldResource;
			resource.addReference();
		} else {
			resource = loadTexture(fileName);
			loadedTextures.put(fileName, resource);
		}
	}
	
	public Texture(int width, int height, int attachement){
		fileName = "";
		resource = new TextureResource(1, width, height, new ByteBuffer[]{Util.createByteBuffer(1920000)}, new float[]{0}, new int[]{attachement});

	}
	@Override
	protected void finalize(){
		if(resource.removeReference() && !fileName.isEmpty()){
			loadedTextures.remove(fileName);
		}
	}
	
	public void bind(){
		bind(0);
	}
	
	public void bind(int samplerSlot){
		assert(samplerSlot >= 0 && samplerSlot <=31);
		glActiveTexture(GL_TEXTURE0);
//		glBindTexture(GL_TEXTURE_2D,resource.getId());
		resource.bind(0);
	}
	
	public void bindAsRenderTarget(){
		resource.bindAsRenderTarget();
	}
	
	public int getID(){
		return resource.getId();
	}
	
	private TextureResource loadTexture(String fileName){
//		String[] splitArray = fileName.split("\\.");
//		String ext = splitArray[splitArray.length-1];
		try{
//			int id = TextureLoader.getTexture(ext, new FileInputStream(new File("./res/textures/"+fileName))).getTextureID();
			BufferedImage image = ImageIO.read(new FileInputStream(new File("./res/textures/"+fileName)));
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			ByteBuffer buffer = Util.createByteBuffer(image.getHeight() * image.getWidth() * 4);
			
			boolean hasAlpha = image.getColorModel().hasAlpha();
			
			for(int y = 0; y <image.getHeight(); y++){
				for(int x = 0; x < image.getWidth(); x++){
					int pixel = pixels[y * image.getWidth() + x];
					buffer.put((byte)((pixel >> 16) & 0xff));
					buffer.put((byte)((pixel >> 8) & 0xff));
					buffer.put((byte)((pixel) & 0xff));
					if(hasAlpha){
						buffer.put((byte)((pixel >> 24) & 0xff));
					} else {
						buffer.put((byte)(0xff));
					}
				}
			}
			buffer.flip();
			TextureResource resource = new TextureResource(1, image.getWidth(), image.getHeight(), new ByteBuffer[]{buffer}, new float[]{0}, new int[]{attachement});
//			glBindTexture(GL_TEXTURE_2D, resource.getId());
////			System.out.println(fileName);
//
//			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
//			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
//
//			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//
//			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

			return resource;
		} catch (Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
}
