package Utilities.Comparators;

import model.Bid;

import java.util.Comparator;

public class BidDateComparator implements Comparator<Bid> {
		@Override
		public int compare(Bid o1, Bid o2) {
				return o1.getDateTime().compareTo(o2.getDateTime());
		}

		@Override
		public Comparator<Bid> reversed() {
				return Comparator.super.reversed();
		}
}
