import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel {
	private JButton clear;
	
	public Menu() {
		clear = new JButton("Clear nodes");
		this.add(clear);
	}
}