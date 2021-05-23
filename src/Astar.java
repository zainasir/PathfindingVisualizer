import java.util.ArrayList;

public class Astar {
	private ArrayList<Node> open, closed, blocked;
	private Node start, end;
	private int rows, cols;
	
	public Astar(int rows, int cols) {
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
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getCols() {
		return cols;
	}
	public void setCols(int cols) {
		this.cols = cols;
	}

	public Node getStart() {
		return start;
	}

	public void setStart(Node start) {
		// Set start node only if different from end node
		if (this.end.getX() != start.getX() || this.end.getY() != start.getY()) {
			this.start = start;			
		}
	}

	public Node getEnd() {
		return end;
	}

	public void setEnd(Node end) {
		// Set end node only if different from start node
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
