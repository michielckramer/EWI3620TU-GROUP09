import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class Items extends GameObject implements VisibleObject {

	private boolean picked = true;
	private boolean state;
	// 1 = speed
	private int type;

	public Items(double x, double y, double z) {
		super(x, y, z);
	}

	public Items(double x, double y, double z, int n) {
		super(x, y, z);
		this.type = n;
	}

	// setLocation
	public void setLocationX(double locationX) {
		this.locationX = locationX;
	}

	public void setLocationY(double locationY) {
		this.locationY = locationY;
	}

	public void setLocationZ(double locationZ) {
		this.locationZ = locationZ;
	}

	public boolean picked() {
		double x = MazeRunner.getPlayerLocationX();
		double z = MazeRunner.getPlayerLocationZ();
		double x1 = this.locationX;
		double z1 = this.locationZ;

		if (Math.abs(x - x1) < 3 && Math.abs(z - z1) < 3) {
			return true;

		}
		return false;
	}

	public void display(GL gl) {
		GLUT glut = new GLUT();

		if (this.picked() && picked) {
			picked = false;
			state = true;
			this.effect();
		}
		if (!state) {
			if (type != 0) {
				switch (this.type) {
				case 1:
					float color1[] = { 1.0f, 0.0f, 0.0f }; // Guard is green
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, color1, 0); // Set
																			// the
					gl.glPushMatrix();
					gl.glTranslated(locationX, locationY, locationZ);
					glut.glutSolidCube(1);
					gl.glPopMatrix();
					break;
				case 2:
					float color2[] = { 1.0f, 1.0f, 0.0f }; // Guard is green
					gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, color2, 0); // Set
																			// the
					gl.glPushMatrix();
					gl.glTranslated(locationX, locationY, locationZ);
					glut.glutSolidCube(1);
					gl.glPopMatrix();
					break;
				}
			} else {
				float color1[] = { 0.0f, 1.0f, 0.0f }; // Guard is green
				gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, color1, 0); // Set
																		// the
				gl.glPushMatrix();
				gl.glTranslated(locationX, locationY, locationZ);
				glut.glutSolidCube(1);
				gl.glPopMatrix();
			}
		}
	}

	public void effect() {
		switch (this.type) {
		case 0:
			System.out.println("Type 0 effect");
			break;
		case 1:
			System.out.println("Type 1 effect");
			MazeRunner.getPlayer().setSpeed(
					MazeRunner.getPlayer().getSpeed() + 0.02);
			break;
		}
	}
}
