import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class StateManager implements GLEventListener {
	private int screenHeight;
	private int screenWidth;

	/*
	 * States: 1 = Startmenu, 2 = Running, 3 = Pause
	 */
	private int gamestate;

	public StateManager(int n) {
		this.gamestate = n;
	}

	public int getGameState() {
		return this.gamestate;
	}

	public void setGameState(int s) {
		switch (s) {
		case 1:
			StartMenu.init = true;
			break;
		case 2:
			MainClass.canvas.removeGLEventListener(this);
			MainClass.canvas.addGLEventListener(MainClass.getRunner());
			MazeRunner.init = true;
			break;
		}
		gamestate = s;
	}

	@Override
	public void display(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
		// TODO Auto-generated method stub

	}
}
