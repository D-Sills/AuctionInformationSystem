package Utilities.Comparators;

import model.Bidder;

import java.util.Comparator;

public class BidderAddressComparator implements Comparator<Bidder> {
		@Override
		public int compare(Bidder o1, Bidder o2) {
				return o1.getAddress().compareTo(o2.getAddress());
		}

		@Override
		public Comparator<Bidder> reversed() { return Comparator.super.reversed(); }
}
