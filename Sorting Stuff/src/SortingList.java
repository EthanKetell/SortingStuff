import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SortingList {

	@SuppressWarnings("serial")
	private class SortPanel extends JPanel {
		private SortPanel() {
			this.setBackground(Color.BLACK);
			this.setPreferredSize(new Dimension((640/length)*length, 480));
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2 = (Graphics2D)g.create();
			g2.translate(0, getHeight());
			g2.scale((double)getWidth()/(length*10), -0.9*getHeight()/(length+1));
			
			for(int i = 0; i < length; i++) {
				if(i == i1 || i == i2) {
					g2.setColor(color);
				} else {
					g2.setColor(Color.WHITE);
				}
				g2.fillRect(10*i, 0, 10, data[i]);
			}
		}
	}
	
	public final int length;	// The size of the dataset
	private final int[] data;	// The dataset to be sorted
	private int i1, i2;			// The indices of the items to be colored (when they are compared or swapped)
	private Color color;		// The color to draw i1 and i2
	private SortPanel panel;	// The panel displaying the data
	private int delay;			// How long to display each operation, in milliseconds
	
	public SortingList(int length) {
		this.length = length;
		data = new int[length];
		for(int i = 0; i < length; i++) {
			data[i] = i+1;
		}
		i1 = -1;
		i2 = -1;
		delay = 10;
		show();
	}
	
	private void show() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		panel = new SortPanel();
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void pause(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public void shuffle() {
		Random r = new Random();
		for(int j = 0; j < length; j++) {
			swap(j,r.nextInt(length));
		}
		refresh();
	}
	
	private void refresh() {
		i1 = -1;
		i2 = -1;
		panel.repaint();
	}

	public int compare(int index1, int index2) {
		i1 = index1;
		i2 = index2;
		color = Color.RED;
		panel.repaint();
		pause(delay);
		refresh();
		if(data[index1] < data[index2]) {
			return -1;
		} else if(data[index1] > data[index2]) {
			return 1;
		} else {
			return 0;
		}
	}

	public void swap(int index1, int index2) {
		i1 = index1;
		i2 = index2;
		color = Color.GREEN;
		panel.repaint();
		pause(delay);
		refresh();
		int temp = data[index1];
		data[index1] = data[index2];
		data[index2] = temp;
	}

}
