import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public class StartMenu implements ActionListener {

	private static int width;
	private static int height;

	public static void display(GL gl) {
		width = StateManager.getScreenWidth();
		height = StateManager.getScreenHeight();
		init(gl);
		gl.glClearColor(1f, 1f, 1f, 0f);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		// gl.glLoadIdentity();
		gl.glBegin(GL.GL_QUADS);
		gl.glColor3f(1.0f, 1.0f, 0.0f);
		gl.glVertex2f(10, 10);
		gl.glVertex2f(100, 10);
		gl.glVertex2f(100, 100);
		gl.glVertex2f(10, 100);
		gl.glEnd();
		gl.glDisable(GL.GL_LIGHTING);
		gl.glFlush();
	}

	public static void init(GL gl) {
		// Set the matrix mode to GL_PROJECTION, allowing us to manipulate the
		// projection matrix
		gl.glMatrixMode(GL.GL_PROJECTION);

		// Always reset the matrix before performing transformations, otherwise
		// those transformations will stack with previous transformations!
		gl.glLoadIdentity();

		/*
		 * glOrtho performs an "orthogonal projection" transformation on the
		 * active matrix. In this case, a simple 2D projection is performed,
		 * matching the viewing frustum to the screen size.
		 */
		gl.glOrtho(0, width, 0, height, -1, 1);

		// Set the matrix mode to GL_MODELVIEW, allowing us to manipulate the
		// model-view matrix.
		gl.glMatrixMode(GL.GL_MODELVIEW);

		// We leave the model view matrix as the identity matrix. As a result,
		// we view the world 'looking forward' from the origin.
		gl.glLoadIdentity();

		// We have a simple 2D application, so we do not need to check for depth
		// when rendering.
		gl.glDisable(GL.GL_DEPTH_TEST);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
	}
}