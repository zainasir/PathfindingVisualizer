import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Frame extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	private JFrame frame;
	private Astar astar;
	private final int SIZE = 30;
	private boolean startPressed, endPressed;
	
	public Frame(int rows, int cols) {
		startPressed = false;
		endPressed = false;
		
		// Initialize frame
		frame = new JFrame("Pathfinding Visualizer");
		frame.setContentPane(this);
		frame.setLayout(new GridLayout(rows, cols));
		
		// Initialize Astar algorithm
		astar = new Astar(rows, cols);

		// Set up the frame
		setFocusable(true);
		requestFocus();
		requestFocusInWindow();
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		frame.setSize(1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Draw start node
		g.setColor(Color.green);
		if (astar.getStart() != null) {
			g.fillRect(astar.getStart().getX() * SIZE, astar.getStart().getY() * SIZE, SIZE, SIZE);			
		}
		
		// Draw end node
		g.setColor(Color.red);
		if (astar.getEnd() != null) {
			g.fillRect(astar.getEnd().getX() * SIZE, astar.getEnd().getY() * SIZE, SIZE, SIZE);
		}
		
		// Draw walls
		g.setColor(Color.black);
		if (astar.getBlocked() != null && astar.getBlocked().size() > 0) {
			for (Node node : astar.getBlocked()) {
				g.fillRect(node.getX()*SIZE, node.getY()*SIZE, SIZE, SIZE);
			}
		}
			
		// Add borders
		g.setColor(Color.gray);
		for (int i = 0; i < this.getWidth()/SIZE + 1; i++) {
			for (int j = 0; j < this.getHeight()/SIZE + 1; j++) {
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
		if (x < 0 || x > this.getWidth()/SIZE || y < 0 || y > this.getHeight()/SIZE) {
			return;
		}
		
		// Create start node
		if (startPressed == true && endPressed != true) {
			astar.setStart(newNode);											
		}
		
		// Create end node
		if (endPressed == true && startPressed != true) {
			astar.setEnd(newNode);											
		}
		
		// Create/Remove blocked node depending on mouse button
		if (startPressed == false && endPressed == false) {
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
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX()/SIZE;
		int y = e.getY()/SIZE;
		System.out.println(x + ", " + y);
		Node newNode = new Node(x, y);
		
		// Ignore if mouse goes out of bounds
		if (x < 0 || x > this.getWidth()/SIZE || y < 0 || y > this.getHeight()/SIZE) {
			return;
		}
		
		if (startPressed == true && endPressed != true) {
			astar.setStart(newNode);											
		}
		
		if (endPressed == true && startPressed != true) {
			astar.setEnd(newNode);											
		}
		
		if (startPressed == false && endPressed == false) {
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
		if (e.getKeyCode() == 83) {
			startPressed = true;
		}
		// If 'e' is pressed
		if (e.getKeyCode() == 69) {
			endPressed = true;
		}
		// If 'c' is pressed
		if (e.getKeyCode() == 67) {
			astar.getBlocked().clear();
			this.repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 83) {
			startPressed = false;
		}
		if (e.getKeyCode() == 69) {
			endPressed = false;
		}
	}
}