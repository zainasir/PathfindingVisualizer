import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Menu extends JPanel {
	private JButton clear, start;
	private JSlider speedSlider;
	private Grid grid;
	
	public Menu(Grid newGrid) {
		this.grid = newGrid;
		
		// Clear grid button
		clear = new JButton("Reset");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grid.clearGrid();
			}
		});
		this.add(clear);
		
		// Start algorithm button
		start = new JButton("Start");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grid.runAlgorithm();
			}
		});
		this.add(start);
		
		// Speed slider to choose how fast algorithm runs
		speedSlider = new JSlider(10, 100);
		speedSlider.setMajorTickSpacing(10);
		speedSlider.setPaintTicks(true);
		speedSlider.setSnapToTicks(true);
		speedSlider.setPaintLabels(true);
		speedSlider.setFont(new Font("Serif", Font.PLAIN, 12));
		speedSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (((JSlider) e.getSource()).getValueIsAdjusting() == false) {
					grid.updateRefreshRate(((JSlider) e.getSource()).getValue());
				}
			}
		});
		this.add(speedSlider);
	}
}