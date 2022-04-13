package Utilities.Comparators;

import model.Bid;

import java.util.Comparator;

public class BidAmountComparator implements Comparator<Bid> {
		@Override
		public int compare(Bid o1, Bid o2) {
				return (int) (o1.getAmount()-o2.getAmount());
		}

		@Override
		public Comparator<Bid> reversed() { return Comparator.super.reversed(); }
}
