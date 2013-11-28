import java.awt.Point;
import java.util.ArrayList;

public abstract class Dijkstra {
	private static ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	private static ArrayList<Vertex> unvisited = new ArrayList<Vertex>();

	public static void input() {
		for (int i = 0; i < Maze.MAZE_SIZE; i++) {
			for (int j = 0; j < Maze.MAZE_SIZE; j++) {
				if (!Maze.isWall(i, j)) {
					vertices.add(new Vertex(10 * i + 5, 10 * j + 5));
				}
			}
		}
	}

	public static ArrayList<Point> simplify(ArrayList<Point> points) {
		if (points.size() > 0) {
			for (int i = points.size() - 1; i > 0; i--) {
				if (points.get(i) == points.get(i - 1)) {
					points.remove(i);
				}
			}
		}
		return points;
	}

	public static ArrayList<Point> path(Point a, Point b) {
		Point s = round(a);
		Point t = round(b);
		Vertex source = new Vertex(s);
		Vertex target = new Vertex(t);
		int size = (int) Maze.MAZE_SIZE;
		if (vertices.size() == 0) {
			input();
		}
		for (Vertex i : vertices) {
			i.setDistance(size * size + 1);
			i.setVisited(false);
		}
		source.setDistance(0);
		unvisited.add(source);

		int least;
		int index;

		while (unvisited.size() > 0) {
			least = unvisited.get(0).getDistance() + 1;
			index = 0;
			for (int i = 0; i < unvisited.size(); i++) {
				if (unvisited.get(i).getDistance() < least
						&& !unvisited.get(i).getVisited()) {
					least = unvisited.get(i).getDistance();
					index = i;
				}
			}
			Vertex u = unvisited.get(index);
			if (u.equals(target)) {
				target.setPrevious(u.getPrevious());
				break;
			}

			unvisited.remove(unvisited.get(index));
			u.setVisited(true);
			for (Vertex v : vertices) {
				if (u.isNeighbor(v)) {
					int alt = u.getDistance() + 1;
					if (alt < v.getDistance()) {
						v.setDistance(alt);
						v.setPrevious(u);
						if (v.equals(target)) {
							target.setPrevious(u);
						}
						unvisited.add(v);
					}
				}
			}
		}
		ArrayList<Vertex> invert = new ArrayList<Vertex>();
		invert.add(target);
		while (invert.get(invert.size() - 1).hasPrevious()) {
			invert.add(invert.get(invert.size() - 1).getPrevious());
		}
		ArrayList<Point> route = new ArrayList<Point>();

		for (int i = invert.size() - 1; i >= 0; i--) {
			route.add(invert.get(i).getLocation());
		}

		return route;
	}

	public static ArrayList<Point> path(ArrayList<Point> points) {
		ArrayList<Point> route = new ArrayList<Point>();
		if (points.size() >= 2) {
			for (int i = 0; i < points.size() - 1; i++) {
				route.addAll(path(points.get(i), points.get(i + 1)));
			}
		}
		return route;
	}

	public static Point round(Point p) {
		int x = (int) p.getX();
		int xres = x % 10;
		if (xres < 5) {
			x += 5 - xres;
		} else {
			x -= xres - 5;
		}
		int y = (int) p.getY();
		int yres = y % 10;
		if (yres < 5) {
			y += 5 - yres;
		} else {
			y -= yres - 5;
		}
		return new Point(x, y);
	}
}
