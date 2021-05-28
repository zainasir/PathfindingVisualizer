
/**
 * @author zainasir
 * This is the Node class for nodes used in all pathfinding algorithms.
 */
public class Node {
	private int x, y;

	public Node(int x, int y) {
		super();
		this.setX(x);
		this.setY(y);
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
}
