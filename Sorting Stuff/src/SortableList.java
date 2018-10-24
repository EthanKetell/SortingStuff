import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SortableList implements Iterable<SortableList.Item> {
	
	@SuppressWarnings("serial")
	private class SortPanel extends JPanel {
		
		private int columnWidth() {
			return Math.min(25, 640/size);
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(columnWidth()*size,500);
		}
		
		public SortPanel() {
			this.setBackground(Color.BLACK);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g.create();
			g2.translate(0, getHeight());
			g2.scale(1, -1);
			g2.scale(1, 0.9*getHeight()/size);
			int columnWidth = columnWidth();
			for(int i = 0; i < size; i++) {
				Item current = get(i);
				g2.setColor(current.color);
				current.color = Color.WHITE;
				g2.fillRect(1, 0, columnWidth-2, current.value);
				g2.translate(columnWidth,0);
			}
		}
	}
	
	public class Item implements Comparable<Item> {
		Color color;
		int value;
		
		private Item(int value) {
			this.color = Color.WHITE;
			this.value = value;
		}
		
		@Override
		public int compareTo(Item other) {
			this.color = Color.RED;
			other.color = Color.RED;
			panel.repaint();
			pause(delay);
			return value - other.value;
		}
		
	}
	
	public ArrayList<Item> left, right;
	private int size, delay;
	private SortPanel panel;

	public SortableList(int size) {
		this.size = size;
		this.delay = 250;
		shuffle();
	}
	
	public void shuffle() {
		if(left == null || right == null) {
			left = new ArrayList<Item>(size);
			right = new ArrayList<Item>(size);
			for(int i = 0; i < size; i++) {
				left.add(new Item(i+1));
			}
		} else {
			left.addAll(right);
			right.clear();
		}
		Random r = new Random();
		while(left.size() > 0) {
			right.add(left.remove(r.nextInt(left.size())));
		}
	}
	
	private void pause(int delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new Iterator<Item>() {
			Iterator<Item>
					lhs = left.iterator(),
					rhs = right.iterator();
			
			public boolean hasNext() {
				return lhs.hasNext() || rhs.hasNext();
			}

			public Item next() {
				if(lhs.hasNext()) {
					return lhs.next();
				} else {
					return rhs.next();
				}
			}
			
		};
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public void show() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new SortPanel();
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public int size() {
		return size;
	}
	
	public Item get(int index) {
		if(index < left.size()) {
			return left.get(index);
		} else {
			return right.get(index-left.size());
		}
	}

	public void swap(int index1, int index2) {
		if(index1 >= size || index2 >= size) {
			throw new IndexOutOfBoundsException("swap index out of range");
		}
		get(index1).color = Color.GREEN;
		get(index2).color = Color.GREEN;
		panel.repaint();
		pause(delay/2);
		if(index1 < left.size()) {
			if(index2 < left.size()) {
				left.set(index1, left.set(index2, left.get(index1)));
			} else {
				index2 -= left.size();
				left.set(index1, right.set(index2, left.get(index1)));
			}
		} else {
			index1 -= left.size();
			if(index2 < left.size()) {
				right.set(index1, left.set(index2, right.get(index1)));
			} else {
				index2 -= left.size();
				right.set(index1, right.set(index2, right.get(index1)));
			}
		}
		get(index1).color = Color.GREEN;
		get(index2).color = Color.GREEN;
		panel.repaint();
		pause(delay/2);
	}

	public int indexOf(Item item) {
		if(left.contains(item)) {
			return left.indexOf(item);
		} else if(right.contains(item)) {
			return right.indexOf(item)+left.size();
		} else {
			return -1;
		}
	}
	
	public void finish() {
		for(int i = 0; i < size; i++) {
			if(get(i).value != i+1) {
				get(i).color = Color.MAGENTA;
			}
		}
		panel.repaint();
		pause(1000);
	}
}
