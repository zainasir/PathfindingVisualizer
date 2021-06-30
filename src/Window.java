import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

 
/**
 * @author zainasir
 * This is the application window class.
 * Combines all panels into a single BorderLayout.
 */
public class Window extends JFrame {
	private JPanel menu;
	private Grid grid;
	
	public Window() {
		this.grid = new Grid();
		this.menu = new Menu(grid);
		
		this.setLayout(new BorderLayout());
		this.add(menu, BorderLayout.SOUTH);
		this.add(grid, BorderLayout.CENTER);
		this.setSize(1000,1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}