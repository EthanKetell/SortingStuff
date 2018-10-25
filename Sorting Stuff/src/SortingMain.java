
public class SortingMain {
	
	static SortingList list;

	public static void main(String[] args) {
		list = new SortingList(100);
		list.setDelay(1);
		list.shuffle();
		bubbleSort();
		list.shuffle();
		selectionSort();
	}
	
	public static void bubbleSort() {
		int lastSwap = list.length-1;
		while(lastSwap > 0) {
			int currentSwap = 0;
			for(int i = 0; i < lastSwap; i++) {
				if(list.compare(i, i+1) > 0) {
					list.swap(i, i+1);
					currentSwap = i;
				}
			}
			lastSwap = currentSwap;
		}
	}
	
	public static void selectionSort() {
		int unsortedStart = 0;
		while(unsortedStart < list.length) {
			int smallest = unsortedStart;
			for(int i = unsortedStart; i < list.length; i++) {
				if(list.compare(i, smallest) < 0) {
					smallest = i;
				}
			}
			list.swap(unsortedStart, smallest);
			unsortedStart++;
		}
	}

}
