package me.abitofevrything.world3d.util;

import me.abitofevrything.world3d.entity.Entity;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

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
		Matrix4f.rotate((float)Math.toRadians(entity.getRx()), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(entity.getRy()), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(entity.getRz()), new Vector3f(0,0,1), matrix, matrix);
		Matrix4f.scale(new Vector3f(entity.getScale(), entity.getScale(), entity.getScale()), matrix, matrix);
		return matrix;
	}
	
}
