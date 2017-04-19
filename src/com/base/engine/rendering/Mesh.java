package com.base.engine.rendering;
import java.util.ArrayList;
import java.util.HashMap;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL11.glDrawElements;

//import static org.lwjgl.opengl.GL30.glBindVertexArray;


import com.base.engine.core.Util;
import com.base.engine.core.math.Vector3f;
import com.base.engine.rendering.meshLoading.IndexedModel;
import com.base.engine.rendering.meshLoading.OBJModel;
import com.base.engine.rendering.resourceManagement.MeshResource;

public class Mesh {
	
	private static HashMap<String, MeshResource> loadedModels = new HashMap<>();
	private MeshResource resource;
	private String fileName;
	
	public Mesh(String fileName){
		MeshResource oldResource = loadedModels.get(fileName);
		this.fileName = fileName;
		
		if(oldResource != null){
			resource = oldResource;
			resource.addReference();
		} else {
			loadMesh(fileName);
			loadedModels.put(fileName, resource);
		}
	}
	
	public Mesh(Vertex[] vertices, int[] indices){
		this(vertices, indices, false);
	}
	
	public Mesh(Vertex[] vertices, int[] indices, boolean calcNormal){
//		resource = new MeshResource();
		fileName ="";
		addVertices(vertices, indices, calcNormal);
	}
	
	@Override
	protected void finalize(){
		if(resource.removeReference() && !fileName.isEmpty()){
			loadedModels.remove(fileName);
		}
	}

	
	public void addVertices(Vertex[] vertices, int[] indices, boolean calcNormal){
		
		if(calcNormal){
			calcNormal(vertices, indices);
		}
		
		resource = new MeshResource(indices.length);

//		resource.setSize();
		
		glBindBuffer(GL_ARRAY_BUFFER,resource.getVbo());
		glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);
		glBindVertexArray(resource.getVao());
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW);
		//glUniform3f()
	}
	
	public void draw(){
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12); //3 * 4(float)
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20); //3 * 4(float)

				//		glDrawArrays(GL_TRIANGLES, 0, size);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
		glDrawElements(GL_TRIANGLES, resource.getSize(), GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
	}
	
	private void calcNormal(Vertex[] vertices, int[] indices){
		for(int i = 0; i < indices.length; i+=3){
			int i0 = indices[i];
			int i1 = indices[i+1];
			int i2 = indices[i+2];
			
			Vector3f v1 = vertices[i1].getPos().subtract(vertices[i0].getPos());
			Vector3f v2 = vertices[i2].getPos().subtract(vertices[i0].getPos());
			
			Vector3f normal = v1.cross(v2).normalize();
			
			vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
			vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
			vertices[i2].setNormal(vertices[i2].getNormal().add(normal));

		}
		
		for (int i = 0; i < vertices.length; i++){
			vertices[i].setNormal(vertices[i].getNormal().normalize());
		}
	}
	
	private Mesh loadMesh(String fileName){
		String[] splitArray = fileName.split("\\.");
		String ext = splitArray[splitArray.length-1];
		
		
		if(!ext.equals("obj")){
			System.err.println("File format error: " + ext + " is not a supported file extension.");
			new Exception().printStackTrace();
			System.exit(1); 
		}
		OBJModel test = new OBJModel("./res/models/"+fileName);
		IndexedModel model = test.toIndexedModel();
		model.calcNormals();
		ArrayList<Vertex> vertices = new ArrayList<>();
		
		for(int i = 0; i < model.getPositions().size(); i++){
			vertices.add(new Vertex(model.getPositions().get(i), model.getTexCoords().get(i), model.getNormals().get(i)));
			
		}
		Vertex[] vertexData = new Vertex[vertices.size()];
		vertices.toArray(vertexData);
		
		Integer[] indexData = new Integer[model.getIndices().size()];
		model.getIndices().toArray(indexData);
		
		addVertices(vertexData, Util.toIntArray(indexData), false);
			
		return null;
	}
	
}
