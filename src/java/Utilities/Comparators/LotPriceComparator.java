package Utilities.Comparators;

import model.AuctionLot;

import java.util.Comparator;

public class LotPriceComparator implements Comparator<AuctionLot> {
		@Override
		public int compare(AuctionLot o1, AuctionLot o2) {
				if (o1.getBids().isEmpty() && !o2.getBids().isEmpty())
						return (int) (o1.getStartingPrice()-o2.getLargestBid().getAmount());
				if (o2.getBids().isEmpty() && !o1.getBids().isEmpty())
						return (int) (o1.getLargestBid().getAmount()-o2.getStartingPrice());
				if (o1.getBids().isEmpty() && o2.getBids().isEmpty())
						return (int) (o1.getStartingPrice()-o2.getStartingPrice());

				else return (int) (o1.getLargestBid().getAmount()-o2.getLargestBid().getAmount());
		}

		@Override
		public Comparator<AuctionLot> reversed() { return Comparator.super.reversed(); }
}
