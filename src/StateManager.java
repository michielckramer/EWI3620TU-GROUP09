import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public class StateManager {
	private static int screenHeight;
	private static int screenWidth;

	public static boolean s1 = false;
	public static boolean s2 = false;
	public static boolean s3 = false;

	/*
	 * States: 1 = Startmenu, 2 = Running, 3 = Pause
	 */
	private static int gamestate = 2;

	public static int getGameState() {
		return gamestate;
	}

	public static void setGameState(int s) {
		switch (s) {
		case 1:
			s1 = true;
			break;
		case 2:
			s2 = true;
			break;
		case 3:
			s3 = true;
			break;
		}
		gamestate = s;
	}

	public static boolean getPause() {
		return gamestate == 3;
	}

	public static void setPause() {
		gamestate = 3;
	}

	public static void resume() {
		setGameState(2);
	}

	public static int getScreenHeight() {
		return screenHeight;
	}

	public static void setScreenHeight(int Height) {
		screenHeight = Height;
	}

	public static int getScreenWidth() {
		return screenWidth;
	}

	public static void setScreenWidth(int Width) {
		screenWidth = Width;
	}
}
