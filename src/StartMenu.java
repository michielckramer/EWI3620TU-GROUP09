import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

import com.sun.opengl.util.Animator;

public class StartMenu implements GLEventListener, MouseListener,
		MouseMotionListener {

	public static boolean init = true;

	private int screenWidth;
	private int screenHeight;
	private float buttonSize;

	public StartMenu(int a, int b) {
		this.screenWidth = a;
		this.screenHeight = b;
	}

	public void display(GLAutoDrawable drawable) {
		if (init) {
			init(drawable);
			init = false;
		}
		screenWidth = MainClass.screenWidth;
		screenHeight = MainClass.screenHeight;

		buttonSize = screenHeight / 10;
		GL gl = drawable.getGL();
		gl.glClearColor(1f, 1f, 1f, 0f);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		// gl.glLoadIdentity();
		// gl.glBegin(GL.GL_QUADS);
		// gl.glColor3f(1.0f, 1.0f, 0.0f);
		// gl.glVertex2f(buttonSize, buttonSize);
		// gl.glVertex2f(2 * buttonSize, buttonSize);
		// gl.glVertex2f(2 * buttonSize, 2 * buttonSize);
		// gl.glVertex2f(buttonSize, 2 * buttonSize);
		// gl.glEnd();
		// gl.glDisable(GL.GL_LIGHTING);
		// gl.glFlush();
		drawButtons(gl);
	}

	public void drawButtons(GL gl) {
		gl.glBegin(GL.GL_QUADS);
		gl.glColor3f(1, 1, 0);
		gl.glVertex2f(buttonSize, buttonSize);
		gl.glVertex2f(2 * buttonSize, buttonSize);
		gl.glVertex2f(2 * buttonSize, 2 * buttonSize);
		gl.glVertex2f(buttonSize, 2 * buttonSize);

		// gl.glColor3f(1, 0, 1);
		// gl.glVertex2f(buttonSize, buttonSize);
		// gl.glVertex2f(2 * buttonSize, buttonSize);
		// gl.glVertex2f(2 * buttonSize, 2 * buttonSize);
		// gl.glVertex2f(buttonSize, 2 * buttonSize);
		gl.glEnd();
		gl.glDisable(GL.GL_LIGHTING);
		gl.glFlush();
	}

	public void init(GLAutoDrawable drawable) {
		MainClass.canvas.addMouseListener(this);
		MainClass.canvas.addGLEventListener(this);
		MainClass.canvas.addMouseMotionListener(this);
		GL gl = drawable.getGL();

		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_LIGHT0);
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
		gl.glOrtho(0, this.screenWidth, 0, this.screenHeight, -1, 1);

		// Set the matrix mode to GL_MODELVIEW, allowing us to manipulate the
		// model-view matrix.
		gl.glMatrixMode(GL.GL_MODELVIEW);

		// We leave the model view matrix as the identity matrix. As a result,
		// we view the world 'looking forward' from the origin.
		gl.glLoadIdentity();

		// We have a simple 2D application, so we do not need to check for depth
		// when rendering.
		gl.glDisable(GL.GL_DEPTH_TEST);

		Animator anim = new Animator(MainClass.canvas);
		anim.start();
	}

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
		MainClass.getRunner().reshape(arg0, arg1, arg2, arg3, arg4);

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (MainClass.getManager().getGameState() == 1) {
			MainClass.getManager().setGameState(2);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}