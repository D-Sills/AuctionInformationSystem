package Utilities.Comparators;

import model.AuctionLot;

import java.util.Comparator;

public class LotDateComparator implements Comparator<AuctionLot> {
		@Override
		public int compare(AuctionLot o1, AuctionLot o2) {
				return o1.getDateOfOrigin().compareTo(o2.getDateOfOrigin());
		}

		@Override
		public Comparator<AuctionLot> reversed() { return Comparator.super.reversed(); }
}

