package me.abitofevrything.world3d.util;

import me.abitofevrything.world3d.rendering.opengl.Vao;

/**
 * A class to generate cubes
 * 
 * @author abitofevrything
 *
 */
public class CubeGenerator {

	private static final int[] INDICES = { 0, 1, 3, 1, 2, 3, 1, 5, 2, 2, 5, 6, 4, 7, 5, 5, 7, 6, 0,
			3, 4, 4, 3, 7, 7, 3, 6, 6, 3, 2, 4, 5, 0, 0, 5, 1 };

	/**
	 * Generates a VAO containing the vertex positions of a box, to be used as a skybox.
	 * @param size - the size of the box.
	 * @return The VAO containing the box.
	 */
	public static Vao generateCube(float size) {
		Vao vao = Vao.create();
		vao.bind();
		vao.createIndexBuffer(INDICES);
		vao.createAttribute(0, getVertexPositions(size), 3);
		vao.createAttribute(1, getTextureCoords(), 2);
		vao.unbind();
		return vao;
	}

	private static float[] getVertexPositions(float size) {
		return new float[] { -size, size, size, size, size, size, size, -size, size, -size, -size,
				size, -size, size, -size, size, size, -size, size, -size, -size, -size, -size,
				-size };
	}
	
	private static float[] getTextureCoords() {
		return new float[] {0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0,
				1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0,
				0};
	}

}
