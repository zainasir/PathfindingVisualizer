import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;



/**
 * @author zainasir
 * This is the center panel class. Controls the background grid layout and,
 * all graphics related to nodes.
 */
public class Grid extends JPanel implements MouseListener, MouseMotionListener, KeyListener, ActionListener {
	private Astar astar;
	private Timer timer;
	private Node parent;
	private final int SIZE = 30;
	private boolean startPressed, endPressed;
	
	public Grid() {
		// Initialize astar algroithm and timer for animation
		astar = new Astar(this);
		timer = new Timer(100, this);
		timer.setInitialDelay(0);
		
		
		// Initialize the JPanel
		setFocusable(true);
		requestFocus();
		requestFocusInWindow();
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
	}
	
	// Repaint settings to draw/erase nodes
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		// Draw open nodes
		g.setColor(new Color(255, 220, 168));
		if (astar.getOpen() != null && astar.getOpen().size() > 0) {
			for (Node node : astar.getOpen()) {
				g.fillRect(node.getX()*SIZE, node.getY()*SIZE, SIZE, SIZE);
			}
		}
		
		// Draw closed nodes
		g.setColor(new Color(255, 180, 69));
		if (astar.getClosed() != null && astar.getClosed().size() > 0) {
			for (Node node : astar.getClosed()) {
				if (node.getX() != astar.getStart().getX() || node.getY() != astar.getStart().getY()) {
					g.fillRect(node.getX()*SIZE, node.getY()*SIZE, SIZE, SIZE);					
				}
			}
		}
		
		// Draw path
		g.setColor(new Color(0, 204, 255));
		if (astar.getPath() != null && astar.getPath().size() > 0) {
			for (Node node : astar.getPath()) {
				g.fillRect(node.getX()*SIZE, node.getY()*SIZE, SIZE, SIZE);
			}
		}

		// Draw walls
		g.setColor(Color.black);
		if (astar.getBlocked() != null && astar.getBlocked().size() > 0) {
			for (Node node : astar.getBlocked()) {
				g.fillRect(node.getX()*SIZE, node.getY()*SIZE, SIZE, SIZE);
			}
		}	
		
		// Draw start node
		g.setColor(new Color(0, 255, 72));
		if (astar.getStart() != null) {
			g.fillRect(astar.getStart().getX() * SIZE, astar.getStart().getY() * SIZE, SIZE, SIZE);			
		}
		
		// Draw end node
		g.setColor(new Color(255, 0, 0));
		if (astar.getEnd() != null) {
			g.fillRect(astar.getEnd().getX() * SIZE, astar.getEnd().getY() * SIZE, SIZE, SIZE);
		}
		
		// Add borders
		g.setColor(Color.gray);
		for (int i = 0; i < this.getWidth()/SIZE; i++) {
			for (int j = 0; j < this.getHeight()/SIZE; j++) {
				g.drawRect(i*SIZE, j*SIZE, SIZE, SIZE);
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX()/SIZE;
		int y = e.getY()/SIZE;
		Node newNode = new Node(x, y);
		
		// If mouse goes out of bounds
		if (x < 0 || x >= this.getWidth()/SIZE || y < 0 || y >= this.getHeight()/SIZE) {
			return;
		}
		
		// Create start node
		if (startPressed == true && endPressed != true && astar.isRunning() == false) {
			astar.setStart(newNode);											
		}
		
		// Create end node
		if (endPressed == true && startPressed != true && astar.isRunning() == false) {
			astar.setEnd(newNode);											
		}
		
		// Create/Remove blocked node depending on mouse button
		if (startPressed == false && endPressed == false && astar.isRunning() == false) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				astar.addBlockedNode(x, y);				
			}
			if (SwingUtilities.isRightMouseButton(e)) {
				astar.removeBlockedNode(x, y);
			}
		}
		
		this.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		requestFocus();
		requestFocusInWindow();
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX()/SIZE;
		int y = e.getY()/SIZE;
		Node newNode = new Node(x, y);
		
		// Ignore if mouse goes out of bounds
		if (x < 0 || x >= this.getWidth()/SIZE || y < 0 || y >= this.getHeight()/SIZE) {
			return;
		}
		
		if (startPressed == true && endPressed != true && astar.isRunning() == false) {
			astar.setStart(newNode);											
		}
		
		if (endPressed == true && startPressed != true && astar.isRunning() == false) {
			astar.setEnd(newNode);											
		}
		
		if (startPressed == false && endPressed == false && astar.isRunning() == false) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				astar.addBlockedNode(x, y);				
			}
			if (SwingUtilities.isRightMouseButton(e)) {
				astar.removeBlockedNode(x, y);
			}
		}
		
		this.repaint();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// If 's' is pressed
		if (e.getKeyCode() == 83 && astar.isRunning() == false && astar.isRunning() == false) {
			startPressed = true;
		}
		// If 'e' is pressed
		if (e.getKeyCode() == 69 && astar.isRunning() == false) {
			endPressed = true;
		}
		// If 'c' is pressed
		if (e.getKeyCode() == 67 && astar.isRunning() == false) {
			astar.getBlocked().clear();
			astar.getPath().clear();
			astar.getOpen().clear();
			astar.getClosed().clear();
			this.repaint();
		}
		if (e.getKeyCode() == 82 && astar.isRunning() == false) {
			astar.setCompleted(false);
			timer.start();
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 83 && astar.isRunning() == false) {
			startPressed = false;
		}
		if (e.getKeyCode() == 69 && astar.isRunning() == false) {
			endPressed = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (astar.isCompleted()) {
			if (parent == null) {
				return;
			}
			while (parent.getParent() != null) {
				System.out.print(parent.getX() + ", " + parent.getY() + "; Parent = ");
				System.out.println(parent.getParent().getX() + ", " + parent.getParent().getY());
				astar.getPath().add(parent);
				parent = parent.getParent();
			}
			this.repaint();
			timer.stop();
			System.out.println("Timer stopped!");
		}
		else {
			parent = astar.moveStep();
			this.repaint();
		}
	}
}
