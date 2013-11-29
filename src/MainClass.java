import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

@SuppressWarnings("serial")
public class MainClass extends Frame implements GLEventListener, MouseListener {
	public static int screenHeight;
	public static int screenWidth;
	public static GLCanvas canvas;

	private static MazeRunner runner;
	private static StartMenu start;
	private static StateManager manager;

	public static Maze maze;
	public static Player player;
	public static Camera camera;
	public static UserInput input;

	private static boolean texture = true;
	private static Texture muur;
	private static Texture vloer;
	private static Texture plafond;

	public static void main(String[] args) {
		new MainClass();
	}

	public MainClass() {
		super("MazeRunner");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
		setSize(screenWidth, screenHeight);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		GLCapabilities caps = new GLCapabilities();
		caps.setDoubleBuffered(true);
		caps.setHardwareAccelerated(true);
		canvas = new GLCanvas();
		add(canvas);
		canvas.addMouseListener(this);
		setVisible(true);
		setRunner(new MazeRunner(screenWidth, screenHeight));
		start = new StartMenu(screenWidth, screenHeight);
		setManager(new StateManager(2));
		// canvas.addGLEventListener(runner);
		canvas.addGLEventListener(this);
		initJOGL();
	}

	public void initJOGL() {
		// First, we set up JOGL. We start with the default settings.
		GLCapabilities caps = new GLCapabilities();
		// Then we make sure that JOGL is hardware accelerated and uses double
		// buffering.
		caps.setDoubleBuffered(true);
		caps.setHardwareAccelerated(true);

		// Now we add the canvas, where OpenGL will actually draw for us. We'll
		// use settings we've just defined.

		Animator anim = new Animator(MainClass.canvas);
		anim.start();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

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

	@Override
	public void display(GLAutoDrawable arg0) {
		int n = getManager().getGameState();
		switch (n) {
		case 1:
			start.display(arg0);
			break;
		case 2:
			getRunner().display(arg0);
			break;
		}
	}

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable arg0) {
		GL gl = arg0.getGL();
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(0, screenWidth, 0, screenHeight, -1, 1);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glDisable(GL.GL_DEPTH_TEST);
		GLCapabilities caps = new GLCapabilities();
		caps.setDoubleBuffered(true);
		caps.setHardwareAccelerated(true);
		Animator anim = new Animator(canvas);
		anim.start();
		if (texture) {
			texture = false;
			loadTexture();
		}
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
		getRunner().reshape(arg0, arg1, arg2, arg3, arg4);
	}

	public static void loadTexture() {
		try {
			File f = new File(
					"C://Users/Michiel/Documents/GitHub/EWI3620TU-GROUP09/Textures/muur2.jpg");
			TextureData data = TextureIO.newTextureData(f, false, "jpg");
			muur = TextureIO.newTexture(data);
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Caught IOException: " + e.getMessage());
		}
	}

	public static Texture getTexture(String name) {
		switch (name) {
		case "muur":
			return muur;
		case "vloer":
			return vloer;
		case "plafond":
			return plafond;
		}
		return muur;
	}

	public static MazeRunner getRunner() {
		return runner;
	}

	public static void setRunner(MazeRunner runner) {
		MainClass.runner = runner;
	}

	public static StateManager getManager() {
		return manager;
	}

	public static void setManager(StateManager manager) {
		MainClass.manager = manager;
	}
}