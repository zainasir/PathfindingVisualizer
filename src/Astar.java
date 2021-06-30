import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author zainasir
 * This is the A* algorithm implementation.
 */
public class Astar {
	private ArrayList<Node> open, closed, blocked, path, neighbors;
	private Node start, end;
	private Grid grid;
	private boolean completed, initialized, running;
	
	public Astar(Grid grid) {
		this.open = new ArrayList<Node>();
		this.closed = new ArrayList<Node>();
		this.blocked = new ArrayList<Node>();
		this.neighbors = new ArrayList<Node>();
		this.setPath(new ArrayList<Node>());
		this.start = new Node(5, 5);
		this.end = new Node(10, 10);
		this.setGrid(grid);
		this.completed = false;
		this.initialized = false;
	}
	
	// Initialize algorithm
	public void initAlgorithm() {
		open.clear();
		closed.clear();
		path.clear();
		open.add(start);
		start.setG(0);
		start.setH(calcH(start, end));
		start.setF(start.getG() + start.getH());		
	}
	
	// Find complete path
	public Node findPath() {
		// Initialize
		initAlgorithm();
		
		neighbors.clear();
		// Algorithm
		while (open.size() > 0) {
			Node currNode = findLeastF();
			if (currNode.getX() == end.getX() && currNode.getY() == end.getY()) {
				System.out.println("End node reached!");
				grid.repaint();
				return currNode;
			}
			// Add neighbors to neighbors list
			neighbors.add(new Node(currNode.getX() - 1, currNode.getY() + 1));
			neighbors.add(new Node(currNode.getX(), currNode.getY() + 1));
			neighbors.add(new Node(currNode.getX() + 1, currNode.getY() + 1));
			neighbors.add(new Node(currNode.getX() - 1, currNode.getY()));
			neighbors.add(new Node(currNode.getX() + 1, currNode.getY()));
			neighbors.add(new Node(currNode.getX() - 1, currNode.getY() - 1));
			neighbors.add(new Node(currNode.getX(), currNode.getY() - 1));
			neighbors.add(new Node(currNode.getX() + 1, currNode.getY() - 1));
			
			for (Node neighbor : neighbors) {
				// Calculate total weight
				int totalWeight;
				if (currNode.getX() == neighbor.getX() || currNode.getY() == neighbor.getY()) {
					totalWeight = currNode.getG() + 10;
				}
				else {
					totalWeight = currNode.getG() + 14;
				}
				if (!isOpen(neighbor) && !isClosed(neighbor) && !isBlocked(neighbor)) {
					open.add(neighbor);
					neighbor.setParent(currNode);
					neighbor.setG(totalWeight);
					neighbor.setH(calcH(neighbor, end));
					neighbor.setF(neighbor.getG() + neighbor.getH());
				}
				else {
					if (totalWeight < neighbor.getG()) {
						neighbor.setParent(currNode);
						neighbor.setG(totalWeight);
						neighbor.setF(neighbor.getG() + calcH(neighbor, end));
						if (isClosed(neighbor)) {
							closed.remove(neighbor);
							open.add(neighbor);
						}
					}
				}
			}
			open.remove(currNode);
			closed.add(currNode);
			neighbors.clear();
		}
		System.out.println("Path not found!");
		return null;
	}
	
	// Find path step by step
	public Node moveStep() {
		running = true;
		
		// Initialize algorithm if first step
		if (initialized == false) {
			initAlgorithm();
			initialized = true;
		}
		neighbors.clear();	
		
		Node currNode = findLeastF();
		if (currNode == null) {
			initialized = false;
			running = false;
			completed = true;
			return null;
		}

		// Add neighbors to neighbors list
		neighbors.add(new Node(currNode.getX() - 1, currNode.getY() + 1));
		neighbors.add(new Node(currNode.getX(), currNode.getY() + 1));
		neighbors.add(new Node(currNode.getX() + 1, currNode.getY() + 1));
		neighbors.add(new Node(currNode.getX() - 1, currNode.getY()));
		neighbors.add(new Node(currNode.getX() + 1, currNode.getY()));
		neighbors.add(new Node(currNode.getX() - 1, currNode.getY() - 1));
		neighbors.add(new Node(currNode.getX(), currNode.getY() - 1));
		neighbors.add(new Node(currNode.getX() + 1, currNode.getY() - 1));
		
		for (Node neighbor : neighbors) {
			// Return neighbor if neighbor is end node
			if (neighbor.getX() == end.getX() && neighbor.getY() == end.getY()) {
				System.out.println("End node reached!");
				initialized = false;
				running = false;
				completed = true;
				open.remove(currNode);
				closed.add(currNode);
				neighbor.setParent(currNode);
				return neighbor;
			}
			// Calculate total weight
			int totalWeight;
			if (currNode.getX() == neighbor.getX() || currNode.getY() == neighbor.getY()) {
				totalWeight = currNode.getG() + 10;
			}
			else {
				totalWeight = currNode.getG() + 14;
			}
			if (!isOpen(neighbor) && !isClosed(neighbor) && !isBlocked(neighbor)) {
				open.add(neighbor);
				neighbor.setParent(currNode);
				neighbor.setG(totalWeight);
				neighbor.setH(calcH(neighbor, end));
				neighbor.setF(neighbor.getG() + neighbor.getH());
			}
			else {
				if (totalWeight < neighbor.getG()) {
					neighbor.setParent(currNode);
					neighbor.setG(totalWeight);
					neighbor.setF(neighbor.getG() + calcH(neighbor, end));
					if (isClosed(neighbor)) {
						closed.remove(neighbor);
						open.add(neighbor);
					}
				}
			}
		}
		open.remove(currNode);
		closed.add(currNode);
		neighbors.clear();
		return null;
	}
	
	// Heuristic function 
	public int calcH(Node a, Node b) {
		int x = Math.abs(a.getX() - b.getX());
		int y = Math.abs(a.getY() - b.getY());
		return ((x*x) + (y*y));
	}
	
	// Find node with least f cost
	public Node findLeastF() {
		if (open.size() <= 0) {
			System.out.println("Open list is empty!");
			return null;
		}
		
		Node min = open.get(0);
		for (Node node : open) {
			if (node.getF() < min.getF()) {
				min = node;
			}
		}
		return min;
	}
	
	// Functions to check if node is open/closed/blocked
	public boolean isOpen(Node n) {
		for (Node node : open) {
			if (node.getX() == n.getX() && node.getY() == n.getY()) {
				return true;
			}
		}
		return false;
	}
	public boolean isClosed(Node n) {
		for (Node node : closed) {
			if (node.getX() == n.getX() && node.getY() == n.getY()) {
				return true;
			}
		}
		return false;
	}
	public boolean isBlocked(Node n) {
		for (Node node : blocked) {
			if (node.getX() == n.getX() && node.getY() == n.getY()) {
				return true;
			}
		}
		return false;
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

	
	public Grid getGrid() {
		return grid;
	}
	

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public ArrayList<Node> getPath() {
		return path;
	}

	public void setPath(ArrayList<Node> path) {
		this.path = path;
	}

	public ArrayList<Node> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(ArrayList<Node> neighbors) {
		this.neighbors = neighbors;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
