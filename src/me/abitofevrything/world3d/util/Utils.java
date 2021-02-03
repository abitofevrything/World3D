package me.abitofevrything.world3d.util;

import me.abitofevrything.world3d.entity.Entity;

import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import static java.lang.Math.*;

/**
 * A utility class
 * 
 * @author abitofevrything
 *
 */
public class Utils {
	/**
	 * Creates a transformation matrix for a given entity
	 * 
	 * @param entity The entity to create the matrix for
	 * @return The created matrix
	 */
	public static Matrix4f createTransformationMatrix(Entity entity) {
		Matrix4f matrix = new Matrix4f();

		matrix.setIdentity();
		Matrix4f.translate(entity.getPosition(), matrix, matrix);
		Matrix4f.rotate((float)toRadians(entity.getRx()), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float)toRadians(entity.getRy()), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float)toRadians(entity.getRz()), new Vector3f(0,0,1), matrix, matrix);
		Matrix4f.scale(new Vector3f(entity.getScale(), entity.getScale(), entity.getScale()), matrix, matrix);
		return matrix;
	}
	
	/**
	 * Calculates the orientation vectors for a given roll, pitch and yaw
	 * 
	 * @param roll
	 * @param pitch
	 * @param yaw
	 * @return An array containing the foward, up and right vectors in that order
	 */
	public static Vector3f[] createOrientationVectors(float roll, float pitch, float yaw) {
		float x = (float)toRadians(-pitch);
		float y = (float)toRadians(yaw);
		float z = (float)toRadians(-roll);
		
//		Axes matX = {
//		  {1,     0,     0 },
//		  {0, cos(x),sin(x)},
//		  {0,-sin(x),cos(x)}
//		  };
		
		Matrix3f matX = new Matrix3f();
		matX.m00 = 1; matX.m01 =  0;                  matX.m02 = 0;
		matX.m10 = 0; matX.m11 =  (float)cos(x); matX.m12 = (float)sin(x);
		matX.m20 = 0; matX.m21 = -(float)sin(x); matX.m22 = (float)cos(x);
		
//		Axes matY = {
//		    {cos(y),0,-sin(y)},
//		    {     0,1,      0},
//		    {sin(y),0, cos(y)}
//		};
		
		Matrix3f matY = new Matrix3f();
		matY.m00 = (float)cos(y); matY.m01 = 0; matY.m02 = -(float)sin(y);
		matY.m10 = 0;                  matY.m11 = 1; matY.m12 = 0;
		matY.m20 = (float)sin(y); matY.m21 = 0; matY.m22 = (float)cos(y);
		
//		  Axes matZ = {
//		    { cos(z),sin(z),0},
//		    {-sin(z),cos(z),0},
//		    {      0,     0,1}
//		  };
		  
		Matrix3f matZ = new Matrix3f();
		matZ.m00 = (float)cos(z);  matZ.m01 = (float)sin(z); matZ.m02 = 0;
		matZ.m10 = -(float)sin(z); matZ.m11 = (float)cos(z); matZ.m12 = 0;
		matZ.m20 = 0;              matZ.m21 = 0;             matZ.m22 = 1;
		
//		  Axes axes = {
//		    {1,0,0},
//		    {0,1,0},
//		    {0,0,1}
//		  };

		Matrix3f axes = new Matrix3f();
		axes.m00 = 1; axes.m01 = 0; axes.m02 = 0;
		axes.m10 = 0; axes.m11 = 1; axes.m12 = 0;
		axes.m20 = 0; axes.m21 = 0; axes.m22 = 1;
		
//		  mul(axes,matX);
//		  mul(axes,matY);
//		  mul(axes,matZ);

		Matrix3f.mul(axes, matY, axes);
		Matrix3f.mul(axes, matZ, axes);
		Matrix3f.mul(axes, matX, axes);

		return new Vector3f[] {
			new Vector3f(axes.m20, axes.m21, axes.m22), //AT
			new Vector3f(axes.m10, axes.m11, axes.m12), //UP
			new Vector3f(axes.m00, axes.m01, axes.m02), //RIGHT
		};
	}
	
}
