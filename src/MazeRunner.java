import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import javax.swing.ImageIcon;

import com.sun.opengl.util.*;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * MazeRunner is the base class of the game, functioning as the view controller
 * and game logic manager.
 * <p>
 * Functioning as the window containing everything, it initializes both JOGL,
 * the game objects and the game logic needed for MazeRunner.
 * <p>
 * For more information on JOGL, visit <a
 * href="http://jogamp.org/wiki/index.php/Main_Page">this page</a> for general
 * information, and <a
 * href="https://jogamp.org/deployment/jogamp-next/javadoc/jogl/javadoc/">this
 * page</a> for the specification of the API.
 * 
 * @author Bruno Scheele, revised by Mattijs Driel
 * 
 */
public class MazeRunner implements GLEventListener {
	public static Texture muur1;

	/*
	 * States: 1 = Startmenu, 2 = Running, 3 = Pause
	 */

	private static boolean collision = true;

	public boolean init = true;

	private GLCanvas canvas;

	private int screenWidth;
	private int screenHeight;
	private float buttonSize;

	private ArrayList<VisibleObject> visibleObjects;

	private static Player player; // The player object.
	private Guard guard; // The guard object
	private Camera camera; // The camera object.
	private UserInput input; // The user input object that controls the player.
	private Maze maze; // The maze.
	private long previousTime = Calendar.getInstance().getTimeInMillis();

	public static boolean getCollision() {
		return collision;
	}

	public static void setCollision(boolean coll) {
		collision = coll;
	}

	public static void exit() {
		System.exit(0);
	}

	/**
	 * Initializes the complete MazeRunner game.
	 * <p>
	 * MazeRunner extends Java AWT Frame, to function as the window. It creats a
	 * canvas on itself where JOGL will be able to paint the OpenGL graphics. It
	 * then initializes all game components and initializes JOGL, giving it the
	 * proper settings to accurately display MazeRunner. Finally, it adds itself
	 * as the OpenGL event listener, to be able to function as the view
	 * controller.
	 * 
	 * @param b
	 */
	public MazeRunner(int a, int b) {
		this.screenWidth = a;
		this.screenHeight = b;
		initJOGL();
		initObjects();
	}

	public static double getPlayerLocationX() {
		return player.getLocationX();
	}

	public static double getPlayerLocationZ() {
		return player.getLocationZ();
	}

	/**
	 * initJOGL() sets up JOGL to work properly.
	 * <p>
	 * It sets the capabilities we want for MazeRunner, and uses these to create
	 * the GLCanvas upon which MazeRunner will actually display our screen. To
	 * indicate to OpenGL that is has to enter a continuous loop, it uses an
	 * Animator, which is part of the JOGL api.
	 */
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

	/**
	 * initializeObjects() creates all the objects needed for the game to start
	 * normally.
	 * <p>
	 * This includes the following:
	 * <ul>
	 * <li>the default Maze
	 * <li>the Player
	 * <li>the Guard
	 * <li>the Camera
	 * <li>the User input
	 * </ul>
	 * <p>
	 * Remember that every object that should be visible on the screen, should
	 * be added to the visualObjects list of MazeRunner through the add method,
	 * so it will be displayed automagically.
	 */
	public void initObjects() {
		// We define an ArrayList of VisibleObjects to store all the objects
		// that need to be
		// displayed by MazeRunner.
		visibleObjects = new ArrayList<VisibleObject>();
		// Add the maze that we will be using.
		maze = new Maze();
		visibleObjects.add(maze);

		// Initialize the player.
		player = new Player(Maze.SQUARE_SIZE + Maze.SQUARE_SIZE / 2, // x-position
				Maze.SQUARE_SIZE / 2, // y-position
				Maze.SQUARE_SIZE + Maze.SQUARE_SIZE / 2, // z-position
				90, 0); // horizontal and vertical angle

		camera = new Camera(player.getLocationX(), player.getLocationY(),
				player.getLocationZ(), player.getHorAngle(),
				player.getVerAngle());

		input = new UserInput(MainClass.canvas);
		player.setControl(input);
	}

	/**
	 * init(GLAutodrawable) is called to initialize the OpenGL context, giving
	 * it the proper parameters for viewing.
	 * <p>
	 * Implemented through GLEventListener. It sets up most of the OpenGL
	 * settings for the viewing, as well as the general lighting.
	 * <p>
	 * It is <b>very important</b> to realize that there should be no drawing at
	 * all in this method.
	 */

	public void init(GLAutoDrawable drawable) {
		System.out.println("init");
		drawable.setGL(new DebugGL(drawable.getGL())); // We set the OpenGL
		// pipeline to Debugging
		// mode.
		GL gl = drawable.getGL();
		GLU glu = new GLU();

		gl.glClearColor(0, 0, 0, 0); // Set the background color.

		// Now we set up our viewpoint.
		gl.glMatrixMode(GL.GL_PROJECTION); // We'll use orthogonal projection.
		gl.glLoadIdentity(); // Reset the current matrix.
		glu.gluPerspective(60, screenWidth, screenHeight, 200);
		gl.glMatrixMode(GL.GL_MODELVIEW);

		// Enable back-face culling.
		gl.glCullFace(GL.GL_BACK);
		gl.glEnable(GL.GL_CULL_FACE);

		// Enable Z-buffering.
		gl.glEnable(GL.GL_DEPTH_TEST);

		// Set and enable the lighting.
		float lightPosition[] = { 0.0f, 50.0f, 0.0f, 1.0f }; // High up in the
		// sky!
		float lightColour[] = { 1.0f, 1.0f, 1.0f, 0.0f }; // White light!
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPosition, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, lightColour, 0);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHT0);

		// Set the shading model.
		gl.glShadeModel(GL.GL_SMOOTH);
		reshape(drawable, 0, 0, screenWidth - 1, screenHeight - 1);
		reshape(drawable, 0, 0, screenWidth, screenHeight);

	}

	/**
	 * display(GLAutoDrawable) is called upon whenever OpenGL is ready to draw a
	 * new frame and handles all of the drawing.
	 * <p>
	 * Implemented through GLEventListener. In order to draw everything needed,
	 * it iterates through MazeRunners' list of visibleObjects. For each
	 * visibleObject, this method calls the object's display(GL) function, which
	 * specifies how that object should be drawn. The object is passed a
	 * reference of the GL context, so it knows where to draw.
	 */
	public void display(GLAutoDrawable drawable) {
		if (init) {
			init(drawable);
			init = false;
		}
		// System.out.println("test");
		GL gl = drawable.getGL();
		gl.glEnable(GL.GL_LIGHTING);
		// Calculating time since last frame.
		GLU glu = new GLU();
		Calendar now = Calendar.getInstance();
		long currentTime = now.getTimeInMillis();
		int deltaTime = (int) (currentTime - previousTime);
		previousTime = currentTime;
		updateMovement(deltaTime, collision);
		updateCamera();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		glu.gluLookAt(camera.getLocationX(), camera.getLocationY(),
				camera.getLocationZ(), camera.getVrpX(), camera.getVrpY(),
				camera.getVrpZ(), camera.getVuvX(), camera.getVuvY(),
				camera.getVuvZ());

		// Display all the visible objects of MazeRunner.
		for (Iterator<VisibleObject> it = visibleObjects.iterator(); it
				.hasNext();) {
			it.next().display(gl);
		}

		gl.glLoadIdentity();
		// Flush the OpenGL buffer.
		gl.glFlush();
	}

	/**
	 * displayChanged(GLAutoDrawable, boolean, boolean) is called upon whenever
	 * the display mode changes.
	 * <p>
	 * Implemented through GLEventListener. Seeing as this does not happen very
	 * often, we leave this unimplemented.
	 */
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
		// GL gl = drawable.getGL();
	}

	/**
	 * reshape(GLAutoDrawable, int, int, int, int, int) is called upon whenever
	 * the viewport changes shape, to update the viewport setting accordingly.
	 * <p>
	 * Implemented through GLEventListener. This mainly happens when the window
	 * changes size, thus changin the canvas (and the viewport that OpenGL
	 * associates with it). It adjust the projection matrix to accomodate the
	 * new shape.
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL gl = drawable.getGL();
		GLU glu = new GLU();

		// Setting the new screen size and adjusting the viewport.
		StateManager.setScreenWidth(width);
		StateManager.setScreenHeight(height);
		screenWidth = width;
		screenHeight = height;
		buttonSize = screenHeight / 10.0f;
		gl.glViewport(0, 0, screenWidth, screenHeight);

		// Set the new projection matrix.
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(60, screenWidth / screenHeight, .1, 200);
		gl.glMatrixMode(GL.GL_MODELVIEW);
	}

	/**
	 * updateMovement(int) updates the position of all objects that need moving.
	 * This includes rudimentary collision checking and collision reaction.
	 */
	private void updateMovement(int deltaTime, boolean collision) {
		double lx = player.getLocationX();
		double lz = player.getLocationZ();
		player.update(deltaTime);
		if (Maze.isWall(player.getLocationX() - 0.2,
				player.getLocationZ() - 0.2)
				|| Maze.isWall(player.getLocationX() + 0.2,
						player.getLocationZ() - 0.2)
				|| Maze.isWall(player.getLocationX() - 0.2,
						player.getLocationZ() + 0.2)
				|| Maze.isWall(player.getLocationX() + 0.2,
						player.getLocationZ() + 0.2)) {
			if (collision && player.getLocationY() >= 0
					&& player.getLocationY() <= Maze.SQUARE_SIZE) {
				player.setLocationX(lx);
				player.setLocationZ(lz);
			}
		}
	}

	/*
	 * updateCamera() updates the camera position and orientation. <p> This is
	 * done by copying the locations from the Player, since MazeRunner runs on a
	 * first person view.
	 */
	private void updateCamera() {
		camera.setLocationX(player.getLocationX());
		camera.setLocationY(player.getLocationY());
		camera.setLocationZ(player.getLocationZ());
		camera.setHorAngle(player.getHorAngle());
		camera.setVerAngle(player.getVerAngle());
		camera.calculateVRP();
	}

	public static Texture loadTexture(String a) {
		try {
			File f = new File(a);
			TextureData data = TextureIO.newTextureData(f, false, "jpg");
			muur1 = TextureIO.newTexture(data);
			// muur1.getHeight();
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Caught IOException: " + e.getMessage());
		}
		return muur1;
	}
}
