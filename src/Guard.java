import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

import java.util.ArrayList;
import java.awt.Point;

public class Guard extends GameObject implements VisibleObject {
	private double speed;
	double SQUARE_SIZE = Maze.getSquare();
	public ArrayList<Point> points = new ArrayList<Point>();
	public int counter = 0;
	private int proximity = 20;
	private String id;

	public Guard(double x, double y, double z) {
		// Set the starting position and speed of the guard
		super(x, y, z);
		speed = 0.2;
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

	public void setRoute(ArrayList<Point> points) {
		this.points = points;
	}

	public void setProximity(int i) {
		this.proximity = i;
	}

	public void setString(String i) {
		this.id = i;
	}

	public void moveGuard() {
		// Movement of the Guard
		if (counter < (points.size() - 1)) {
			if ((points.get(counter).getX() - points.get(counter + 1).getX()) == 0) {
				if ((points.get(counter).getY() - points.get(counter + 1)
						.getY()) > 0) {
					this.setLocationZ(locationZ - speed);
				}
				if ((points.get(counter).getY() - points.get(counter + 1)
						.getY()) < 0) {
					this.setLocationZ(locationZ + speed);
				}
			}

			if ((points.get(counter).getY() - points.get(counter + 1).getY()) == 0) {
				if ((points.get(counter).getX() - points.get(counter + 1)
						.getX()) > 0) {
					this.setLocationX(locationX - speed);
				}
				if ((points.get(counter).getX() - points.get(counter + 1)
						.getX()) < 0) {
					this.setLocationX(locationX + speed);
				}
			}

			// Afstand p1 -- p2
			double d1 = Math.sqrt(Math.pow(points.get(counter).getX()
					- points.get(counter + 1).getX(), 2)
					+ Math.pow(
							points.get(counter).getY()
									- points.get(counter + 1).getY(), 2));

			// Afstand p1 -- huidige positie
			double d2 = Math.sqrt(Math.pow(points.get(counter).getX()
					- locationX, 2)
					+ Math.pow(points.get(counter).getY() - locationZ, 2));

			if (d2 > d1) {

				counter++;
			}

			if (counter == (points.size() - 1)) {
				counter = 0;
			}
		}

	}

	public void look(int dir, double x, double y) {
		switch (dir) {
		case 0:
			if (isPlayer(x, y)) {
				System.out.println("Player spotted by guard " + id);
			}
		case 1:
			if (!Maze.isWall(x + 10, y)) {
				if (isPlayer(x + 10, y)) {
					System.out.println("Player spotted by guard " + id);
				} else {
					look(dir, x + 10, y);
				}
			}
			break;
		case 2:
			if (!Maze.isWall(x - 10, y)) {
				if (isPlayer(x - 10, y)) {
					System.out.println("Player spotted by guard " + id);
				} else {
					look(dir, x - 10, y);
				}
			}
			break;
		case 3:
			if (!Maze.isWall(x, y + 10)) {
				if (isPlayer(x, y + 10)) {
					System.out.println("Player spotted by guard " + id);
				} else {
					look(dir, x, y + 10);
				}
			}
			break;
		case 4:
			if (!Maze.isWall(x, y - 10)) {
				if (isPlayer(x, y - 10)) {
					System.out.println("Player spotted by guard " + id);
				} else {
					look(dir, x, y - 10);
				}
			}
			break;

		case 5:
			if (!Maze.isWall(x - 10, y) && !Maze.isWall(x, y + 10)) {
				if (isPlayer(x - 10, y + 10)) {
					System.out.println("Player spotted by guard " + id);
				} else {
					look(dir, x - 10, y + 10);
				}
			}
			break;

		case 6:
			if (!Maze.isWall(x + 10, y) && !Maze.isWall(x, y + 10)) {
				if (isPlayer(x + 10, y + 10)) {
					System.out.println("Player spotted by guard " + id);
				} else {
					look(dir, x + 10, y + 10);
				}
			}
			break;

		case 7:
			if (!Maze.isWall(x + 10, y) && !Maze.isWall(x, y - 10)) {
				if (isPlayer(x + 10, y - 10)) {
					System.out.println("Player spotted by guard " + id);
				} else {
					look(dir, x + 10, y - 10);
				}
			}
			break;

		case 8:
			if (!Maze.isWall(x - 10, y) && !Maze.isWall(x, y - 10)) {
				if (isPlayer(x - 10, y - 10)) {
					System.out.println("Player spotted by guard " + id);
				} else {
					look(dir, x - 10, y - 10);
				}
			}
			break;

		}
	}

	public boolean isPlayer(double x, double z) {
		double playerX = MazeRunner.getPlayerLocationX();
		double playerZ = MazeRunner.getPlayerLocationZ();

		if (playerX <= x + 5 && playerX >= x - 5 && playerZ <= z + 5
				&& playerZ >= z - 5) {
			return true;
		}
		return false;
	}

	public void display(GL gl) {
		GLUT glut = new GLUT();

		// Setting the guard colour and material
		float guardColour[] = { 0.0f, 1.0f, 0.0f }; // Guard is green

		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, guardColour, 0); // Set the
																		// materials

		// Movements of the guards
		moveGuard();

		// Positie player
		double playerX = MazeRunner.getPlayerLocationX();
		double playerZ = MazeRunner.getPlayerLocationZ();

		// Positie guards
		double guardX = getLocationX();
		double guardZ = getLocationZ();

		// Detectie by look van player
		look(0, guardX, guardZ);
		look(1, guardX, guardZ);
		look(2, guardX, guardZ);
		look(3, guardX, guardZ);
		look(4, guardX, guardZ);
		look(5, guardX, guardZ);
		look(5, guardX, guardZ);
		look(6, guardX, guardZ);
		look(7, guardX, guardZ);
		look(8, guardX, guardZ);

		// //Detectie by hearing van player
		// if(Math.abs(guardX - playerX) <= this.proximity && Math.abs(guardZ -
		// playerZ) <= this.proximity ){
		// System.out.println("Player gehoord door guard " + id);
		// }

		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, guardColour, 0);
		gl.glPushMatrix();
		gl.glTranslated(locationX, locationY, locationZ);
		glut.glutSolidCube((float) SQUARE_SIZE / 4);
		gl.glPopMatrix();

	}
}