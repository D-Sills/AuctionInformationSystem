package model;

import java.time.LocalDateTime;

public class WinningBid extends Bid {
		/**
		 * Constructor for objects of class Bid
		 * @param bidder
		 * @param auctionLot
		 * @param amount   Patient's phone number
		 * @param dateTime Patient's email
		 */
		public WinningBid(Bidder bidder, AuctionLot auctionLot, Double amount, LocalDateTime dateTime) {
				super(bidder, auctionLot, amount, dateTime);
		}
}
