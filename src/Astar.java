import java.util.ArrayList;


/**
 * @author zainasir
 * This is the A* algorithm implementation.
 */
public class Astar {
	private ArrayList<Node> open, closed, blocked;
	private Node start, end;
	
	public Astar() {
		open = new ArrayList<Node>();
		closed = new ArrayList<Node>();
		blocked = new ArrayList<Node>();
		start = new Node(5, 5);
		end = new Node(10, 10);
	}
	
	// Custom add/remove functions to prevent duplicates and overwrites
	public void addBlockedNode(int x, int y) {
		// Ignore if blocked same as start/end node
		if (x == start.getX() && y == start.getY()) {
			return;
		}
		if (x == end.getX() && y == end.getY()) {
			return;
		}
		
		// Ignore if already exists
		for (Node node : blocked) {
			if (x == node.getX() && y == node.getY()) {
				return;
			}
		}
		
		// Add blocked node
		blocked.add(new Node(x, y));
	}
	
	public void removeBlockedNode(int x, int y) {
		for (int i = 0; i < blocked.size(); i++) {
			if (blocked.get(i).getX() == x && blocked.get(i).getY() == y) {
				blocked.remove(i);
				return;
			}
		}
	}
	
	// Setters and Getters
	public ArrayList<Node> getOpen() {
		return open;
	}
	public ArrayList<Node> getClosed() {
		return closed;
	}

	public Node getStart() {
		return start;
	}

	public void setStart(Node start) {
		// Ignore if start node same as blocked node
		for (Node node : blocked) {
			if (node.getX() == start.getX() && node.getY() == start.getY()) {
				return;
			}
		}
		
		// Set if different from end node
		if (this.end.getX() != start.getX() || this.end.getY() != start.getY()) {
			this.start = start;			
		}
	}

	public Node getEnd() {
		return end;
	}

	public void setEnd(Node end) {
		// Ignore if end node same as blocked node
		for (Node node : blocked) {
			if (node.getX() == end.getX() && node.getY() == end.getY()) {
				return;
			}
		}
		
		// Set end node only different from start node
		if (this.start.getX() != end.getX() || this.start.getY() != end.getY()) {
			this.end = end;			
		}
	}

	public ArrayList<Node> getBlocked() {
		return blocked;
	}

	public void setBlocked(ArrayList<Node> blocked) {
		this.blocked = blocked;
	}
}
