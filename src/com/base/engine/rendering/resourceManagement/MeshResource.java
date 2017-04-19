package com.base.engine.rendering.resourceManagement;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;


public class MeshResource {
	private int vbo;
	private int vao;
	private int ibo;	//index buffer object
	private int size;
	private int refCount;
	
	public MeshResource(int size){
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		vao = glGenVertexArrays();
		this.size =size;
		this.refCount = 1;
	}
	
	@Override
	protected void finalize(){
		glDeleteBuffers(vbo);
		glDeleteVertexArrays(vao);
		glDeleteBuffers(ibo);
	}
	
	public void addReference(){
		refCount++;
	}
	public boolean removeReference(){
		refCount--;
		return refCount == 0;
	}

	public int getVbo() {
		return vbo;
	}

	public int getVao() {
		return vao;
	}

	public int getIbo() {
		return ibo;
	}
	
	public int getSize(){
		return size;
	}
	
	public void setSize(int size){
		this.size = size;
	}
}
