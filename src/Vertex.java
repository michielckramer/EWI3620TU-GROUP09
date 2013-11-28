import java.awt.Point;

public class Vertex {
	private Point location;
	private boolean visited;
	private int distance;
	private Vertex previous;

	public Vertex(int x, int y) {
		this.location = new Point(x, y);
	}

	public Vertex(Point loc) {
		this.location = loc;
	}

	public Vertex(Point loc, boolean vis, int dis, Vertex pre) {
		this.location = loc;
		this.visited = vis;
		this.distance = dis;
		this.previous = pre;
	}

	public void setLocation(Point p) {
		this.location = p;
	}

	public void setVisited(Boolean p) {
		this.visited = p;
	}

	public void setDistance(int p) {
		this.distance = p;
	}

	public void setPrevious(Vertex p) {
		this.previous = p;
	}

	public Point getLocation() {
		return this.location;
	}

	public boolean getVisited() {
		return this.visited;
	}

	public int getDistance() {
		return this.distance;
	}

	public Vertex getPrevious() {
		return this.previous;
	}

	public int getX() {
		return (int) this.getLocation().getX();
	}

	public int getY() {
		return (int) this.getLocation().getY();
	}

	public boolean isNeighbor(Vertex a) {
		if (this.getX() == a.getX() && this.getY() == a.getY() + 10) {
			return true;
		}
		if (this.getX() == a.getX() && this.getY() == a.getY() - 10) {
			return true;
		}
		if (this.getX() == a.getX() + 10 && this.getY() == a.getY()) {
			return true;
		}
		if (this.getX() == a.getX() - 10 && this.getY() == a.getY()) {
			return true;
		}
		return false;
	}

	public boolean equals(Vertex a) {
		return this.location.equals(a.location);
	}

	public boolean hasPrevious() {
		return previous != null;
	}

	public String print() {
		String res = "(" + this.getX() + "," + this.getY() + ")";
		return res;
	}
}
