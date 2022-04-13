package Utilities;

import javafx.collections.ObservableList;

import java.util.Comparator;

/**
 * @author Gedvydas Jucius
 * Sorting Alogorithms used to sort various bids and bidders.
 */

public class SortingSearchMethods {

	public static <E> void SelectionSort(ObservableList<E> a, Comparator<E> c)
	{
		int n = a.size();

		// One by one move boundary of unsorted subarray
		for (int i = 0; i < n-1; i++)
		{
			// Find the minimum element in unsorted array
			int min_el = i;
			for (int j = i+1; j < n; j++)
				if (c.compare(a.get(j),(a.get(min_el))) < 0){
			min_el = j;

			// Swap the found minimum element with the first
			// element
			E temp = a.get(min_el);
			a.set(min_el, a.get(i));
			a.set(i, temp);
		}
		}
	}

		public static <E extends Comparable<E>> int binarySearch(CustomList<E> a, E k) {
				int res = -1, min = 0, max = a.size() - 1, pos;
				while( ( min <= max ) && ( res == -1 ) ) {
						pos = (min + max) / 2;
						int comparison = k.compareTo(a.get(pos));
						if( comparison == 0)
								res = pos;
						else if( comparison < 0)
								max = pos - 1;
						else
								min = pos + 1;
				}
				return res;
		}
}
