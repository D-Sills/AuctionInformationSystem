package Utilities.Comparators;

import model.Bidder;

import java.util.Comparator;

public class BidderNameComparator implements Comparator<Bidder> {
		@Override
		public int compare(Bidder o1, Bidder o2) {
				return o1.getName().compareTo(o2.getName());
		}

		@Override
		public Comparator<Bidder> reversed() { return Comparator.super.reversed(); }
}
