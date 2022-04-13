package Utilities.Comparators;

import model.AuctionLot;

import java.util.Comparator;

public class LotNameComparator implements Comparator<AuctionLot> {
		@Override
		public int compare(AuctionLot o1, AuctionLot o2) {
				return o1.getName().compareTo(o2.getName());
		}

		@Override
		public Comparator<AuctionLot> reversed() { return Comparator.super.reversed(); }
}
