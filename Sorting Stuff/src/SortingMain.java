
public class SortingMain {

	public static void main(String[] args) {
		SortableList test = new SortableList(50);
		test.setDelay(10);
		test.show();
		bubbleSort(test);
		test.shuffle();
		insertionSort(test);
		test.shuffle();
		selectionSort(test);
	}

	private static void bubbleSort(SortableList target) {
		int sortedBelow = Integer.MAX_VALUE;
		while(sortedBelow > 0) {
			int swapIndex = 0;
			for(int i = 0; i < sortedBelow && i < target.size()-1; i++) {
				if(target.get(i).compareTo(target.get(i+1)) > 0) {
					target.swap(i, i+1);
					swapIndex = i;
				}
			}
			sortedBelow = swapIndex;
		}
		target.finish();
	}
	
	private static void insertionSort(SortableList target) {
		while(target.right.size() > 0) {
			int i = 0;
			while(i < target.left.size() && target.right.get(0).compareTo(target.left.get(i)) > 0) {
				i++;
			}
			target.left.add(i, target.right.remove(0));
		}
		target.finish();
	}
	
	private static void selectionSort(SortableList target) {
		while(target.right.size() > 0) {
			int smallest = 0;
			for(int i = 0; i < target.right.size(); i++) {
				if(target.right.get(smallest).compareTo(target.right.get(i)) > 0) {
					smallest = i;
				}
			}
			target.left.add(target.right.remove(smallest));
		}
		target.finish();
	}

}
