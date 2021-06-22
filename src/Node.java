
/**
 * @author zainasir
 * This is the Node class for nodes used in all pathfinding algorithms.
 */
public class Node {
	private int x, y, g, h, f;
	private Node parent;

	public Node(int x, int y) {
		super();
		this.setX(x);
		this.setY(y);
		this.g = 0;
		this.h = 0;
		this.f = 0;
		this.parent = null;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
}
