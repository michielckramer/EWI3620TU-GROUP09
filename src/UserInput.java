import java.awt.event.*;

import javax.media.opengl.GLCanvas;

/**
 * The UserInput class is an extension of the Control class. It also implements
 * three interfaces, each providing handler methods for the different kinds of
 * user input.
 * <p>
 * For making the assignment, only some of these handler methods are needed for
 * the desired functionality. The rest can effectively be left empty (i.e. the
 * methods under 'Unused event handlers').
 * <p>
 * Note: because of how java is designed, it is not possible for the game window
 * to react to user input if it does not have focus. The user must first click
 * the window (or alt-tab or something) before further events, such as keyboard
 * presses, will function.
 * 
 * @author Mattijs Driel
 * 
 */
public class UserInput extends Control implements MouseListener,
		MouseMotionListener, KeyListener {
	int x, y, sx, sy;
	int dx = -90;
	int dy = 0;

	/**
	 * UserInput constructor.
	 * <p>
	 * To make the new UserInput instance able to receive input, listeners need
	 * to be added to a GLCanvas.
	 * 
	 * @param canvas
	 *            The GLCanvas to which to add the listeners.
	 */
	public UserInput(GLCanvas canvas) {
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addKeyListener(this);
	}

	/*
	 * **********************************************
	 * * Updating * **********************************************
	 */

	@Override
	public void update() {
		dX = dx;
		dY = dy;
	}

	/*
	 * **********************************************
	 * * Input event handlers * **********************************************
	 */

	@Override
	public void mousePressed(MouseEvent event) {
		x = event.getX();
		y = event.getY();
		sx = dx;
		sy = dy;
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		// if (StateManager.getGameState() == 2) {
		dx = -event.getX() + x + sx;
		if (-event.getY() + y + sy > 90) {
			dy = 89;
		} else {
			if (-event.getY() + y + sy < -90) {
				dy = -89;
			} else {
				dy = -event.getY() + y + sy;
			}
		}
		// }
	}

	@Override
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode();
		switch (key) {
		case KeyEvent.VK_W:
			forward = true;
			break;
		case KeyEvent.VK_A:
			left = true;
			break;
		case KeyEvent.VK_S:
			back = true;
			break;
		case KeyEvent.VK_D:
			right = true;
			break;
		case KeyEvent.VK_SPACE:
			if (!MazeRunner.getCollision()) {
				up = true;
			}
			break;
		case KeyEvent.VK_SHIFT:
			if (!MazeRunner.getCollision()) {
				down = true;
			}
			break;
		case KeyEvent.VK_F1:
			MazeRunner.setCollision(!MazeRunner.getCollision());
			break;
		case KeyEvent.VK_ESCAPE:
			if (MainClass.getManager().getGameState() == 2) {
				MainClass.getManager().setGameState(1);
			}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		switch (event.getKeyCode()) {
		case KeyEvent.VK_W:
			forward = false;
			break;
		case KeyEvent.VK_A:
			left = false;
			break;
		case KeyEvent.VK_S:
			back = false;
			break;
		case KeyEvent.VK_D:
			right = false;
			break;
		case KeyEvent.VK_SPACE:
			up = false;
			break;
		case KeyEvent.VK_SHIFT:
			down = false;
			break;
		}
	}

	/*
	 * **********************************************
	 * * Unused event handlers * **********************************************
	 */

	@Override
	public void mouseMoved(MouseEvent event) {
	}

	@Override
	public void keyTyped(KeyEvent event) {
	}

	@Override
	public void mouseClicked(MouseEvent event) {
	}

	@Override
	public void mouseEntered(MouseEvent event) {
	}

	@Override
	public void mouseExited(MouseEvent event) {
	}

	@Override
	public void mouseReleased(MouseEvent event) {
	}

}
