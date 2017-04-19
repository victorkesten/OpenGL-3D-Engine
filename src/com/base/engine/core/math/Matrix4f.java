package com.base.engine.core.math;

/**
 * float 4x4 matrix class.
 * @author Kesten
 *
 */
public class Matrix4f {

	//internal matrix
	private float[][] matrix;
	
	/**
	 * Creates an empty Matrix with all null elements
	 */
	public Matrix4f(){
		matrix = new float[4][4];
	}
	/**
	 * Sets matrix to be an Identity matrix
	 * @return this - The object Matrix4f
	 */
	public Matrix4f initIdentity(){
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				if(i == j){
					matrix[i][j] = 1;
				} else {
					matrix[i][j] = 0;
				}
			}
		}
		return this;
	}

	/**
	 * Takes in a translation with values x,y,z. 
	 * 1 = x
	 * 2 = y
	 * 3 = z
	 * 4 = alpha level.
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public Matrix4f initTranslation(float x, float y, float z){
		//x, y, z, w
		// Last row is alpha. We do x row * by x
		matrix[0][0] = 1; 	matrix[0][1] = 0; matrix[0][2] = 0; 	matrix[0][3] = x;
		matrix[1][0] = 0; 	matrix[1][1] = 1; matrix[1][2] = 0; 	matrix[1][3] = y;
		matrix[2][0] = 0; 	matrix[2][1] = 0; matrix[2][2] = 1; 	matrix[2][3] = z;
		matrix[3][0] = 0; 	matrix[3][1] = 0; matrix[3][2] = 0; 	matrix[3][3] = 1;
		return this;
	}
	
	
	
	/**
	 * We set the Matrix to scaling Matrix. 
	 * x value = x;
	 * y value = y;
	 * z value = z;
	 * alpha = 1;
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public Matrix4f initScaling(float x, float y, float z){

		matrix[0][0] = x; 	matrix[0][1] = 0; matrix[0][2] = 0; 	matrix[0][3] = 0;
		matrix[1][0] = 0; 	matrix[1][1] = y; matrix[1][2] = 0; 	matrix[1][3] = 0;
		matrix[2][0] = 0; 	matrix[2][1] = 0; matrix[2][2] = z; 	matrix[2][3] = 0;
		matrix[3][0] = 0; 	matrix[3][1] = 0; matrix[3][2] = 0; 	matrix[3][3] = 1;
		
		return this;
	}
	/**
	 * 
	 * @param fov
	 * @param aspect
	 * @param zNear
	 * @param zFar
	 * @return
	 */
	public Matrix4f initPerspective(float fov, float aspect, float zNear, float zFar){
		
		float tanHalfFOV = (float)Math.tan(Math.toRadians(fov/2));
		float zRange = zNear - zFar;
		
		matrix[0][0] = 1.0f / (tanHalfFOV * aspect); 	matrix[0][1] = 0; 				matrix[0][2] = 0; 						matrix[0][3] = 0;
		matrix[1][0] = 0; 						matrix[1][1] = 1.0f / tanHalfFOV; 	matrix[1][2] = 0; 						matrix[1][3] = 0;
		matrix[2][0] = 0; 						matrix[2][1] = 0; 					matrix[2][2] = (-zNear - zFar)/zRange; 	matrix[2][3] = 2 * zFar * zNear / zRange;
		matrix[3][0] = 0; 						matrix[3][1] = 0;					matrix[3][2] = 1; 						matrix[3][3] = 0;
		
		return this;
	}
	/**
	 * Initializes an orthographic  view on Matrix.
	 * @param left
	 * @param right
	 * @param bottom
	 * @param top
	 * @param near
	 * @param far
	 * @return
	 */
	public Matrix4f initOrthographic(float left, float right, float bottom, float top, float near, float far){
		float width = right - left;
		float height = top - bottom;
		float depth = far - near;
		matrix[0][0] = 2/width; 	matrix[0][1] = 0; matrix[0][2] = 0; 	matrix[0][3] = -(right+left)/width;
		matrix[1][0] = 0; 	matrix[1][1] = 2/height; matrix[1][2] = 0; 	matrix[1][3] = -(top+bottom)/height;
		matrix[2][0] = 0; 	matrix[2][1] = 0; matrix[2][2] = -2/depth; 	matrix[2][3] = -(far+near)/depth;
		matrix[3][0] = 0; 	matrix[3][1] = 0; matrix[3][2] = 0; 	matrix[3][3] = 1;
		return this;
	}
	
	/**
	 * Initializes 2D Rotations around each axis. 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public Matrix4f initRotation(float x, float y, float z){
		//x, y, z, w
		//yaw. 2D rotations around each plane.
		Matrix4f rotationX = new Matrix4f();
		Matrix4f rotationY = new Matrix4f();
		Matrix4f rotationZ = new Matrix4f();

		x = (float) Math.toRadians(x);
		y = (float) Math.toRadians(y);
		z = (float) Math.toRadians(z);
		
		//Rotation on the Z axis
		rotationZ.matrix[0][0] = (float) Math.cos(z); 	rotationZ.matrix[0][1] = -(float) Math.sin(z); rotationZ.matrix[0][2] = 0; 	rotationZ.matrix[0][3] = 0;
		rotationZ.matrix[1][0] = (float) Math.sin(z); 	rotationZ.matrix[1][1] = (float) Math.cos(z); rotationZ.matrix[1][2] = 0; 	rotationZ.matrix[1][3] = 0;
		rotationZ.matrix[2][0] = 0; 	rotationZ.matrix[2][1] = 0; rotationZ.matrix[2][2] = 1; 	rotationZ.matrix[2][3] = 0;
		rotationZ.matrix[3][0] = 0; 	rotationZ.matrix[3][1] = 0; rotationZ.matrix[3][2] = 0; 	rotationZ.matrix[3][3] = 1;
		
		//Rotation on the X Axis
		rotationX.matrix[0][0] = 1; 	rotationX.matrix[0][1] = 0; rotationX.matrix[0][2] = 0; 	rotationX.matrix[0][3] = 0;
		rotationX.matrix[1][0] = 0; 	rotationX.matrix[1][1] = (float) Math.cos(x); rotationX.matrix[1][2] = -(float) Math.sin(x); 	rotationX.matrix[1][3] = 0;
		rotationX.matrix[2][0] = 0; 	rotationX.matrix[2][1] = (float) Math.sin(x); rotationX.matrix[2][2] = (float) Math.cos(x); 	rotationX.matrix[2][3] = 0;
		rotationX.matrix[3][0] = 0; 	rotationX.matrix[3][1] = 0; rotationX.matrix[3][2] = 0; 	rotationX.matrix[3][3] = 1;
		
		//Rotation on the Y Axis;
		rotationY.matrix[0][0] = (float)Math.cos(y); 	rotationY.matrix[0][1] = 0; rotationY.matrix[0][2] = -(float) Math.sin(y); 	rotationY.matrix[0][3] = 0;
		rotationY.matrix[1][0] = 0; 					rotationY.matrix[1][1] = 1; rotationY.matrix[1][2] = 0; 	rotationY.matrix[1][3] = 0;
		rotationY.matrix[2][0] = (float) Math.sin(y); 	rotationY.matrix[2][1] = 0; rotationY.matrix[2][2] = (float) Math.cos(y); 	rotationY.matrix[2][3] = 0;
		rotationY.matrix[3][0] = 0; 					rotationY.matrix[3][1] = 0; rotationY.matrix[3][2] = 0; 	rotationY.matrix[3][3] = 1;
		
		matrix = rotationZ.mul(rotationY.mul(rotationX)).getMatrix();
		return this;
	}
	
	/**
	 * 
	 * @param forward
	 * @param up
	 * @return
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up){
		Vector3f f = forward;
		f.normalize();
		
		Vector3f right = up;
		right.normalize();
		right = right.cross(f);
		
		Vector3f u = f.cross(right);
		
		return initRotation(forward, up, right);
	}
	

	public Matrix4f initRotation(Vector3f forward, Vector3f up, Vector3f right)
	{
		Vector3f f = forward;
		Vector3f r = right;
		Vector3f u = up;

		matrix[0][0] = r.getX();	matrix[0][1] = r.getY();	matrix[0][2] = r.getZ();	matrix[0][3] = 0;
		matrix[1][0] = u.getX();	matrix[1][1] = u.getY();	matrix[1][2] = u.getZ();	matrix[1][3] = 0;
		matrix[2][0] = f.getX();	matrix[2][1] = f.getY();	matrix[2][2] = f.getZ();	matrix[2][3] = 0;
		matrix[3][0] = 0;		matrix[3][1] = 0;		matrix[3][2] = 0;		matrix[3][3] = 1;

		return this;
	}
	
	public Vector3f transform(Vector3f vec){
		return new Vector3f(	matrix[0][0] * vec.getX() + matrix[0][1] * vec.getY() + matrix[0][2]*vec.getZ() + matrix[0][3],
								matrix[1][0] * vec.getX() + matrix[1][1] * vec.getY() + matrix[1][2]*vec.getZ() + matrix[1][3],
								matrix[2][0] * vec.getX() + matrix[2][1] * vec.getY() + matrix[2][2]*vec.getZ() + matrix[2][3]);
	}
	
	
	public Matrix4f mul(Matrix4f r){
		Matrix4f res = new Matrix4f();
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				res.set(i, j, 	get(i,0) * r.get(0, j) + 
								get(i,1) * r.get(1, j) + 
								get(i,2) * r.get(2, j) +
								get(i,3) * r.get(3, j));
			}
		}
		return res;
	}
	
	public float[][] getMatrix() {
		float [][] res = new float[4][4];
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				res[i][j] = matrix[i][j];
			}
		}
		return res;
	}
	
	public float get(int x, int y){
		return matrix[x][y];
	}

	public void setMatrix(float[][] matrix) {
		this.matrix = matrix;
	}
	
	public void set(int x, int y, float value){
		matrix[x][y] = value;
	}
}
