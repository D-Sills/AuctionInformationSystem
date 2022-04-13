package model;

import java.time.LocalDateTime;

/**
 * @author Darren Sills & Gedvydas Jucius
 * Bid class used to store all data related to each Bidding.
 */
public class Bid {
    private final Bidder bidder;
    private final AuctionLot auctionLot;
    private Double amount;
    private LocalDateTime dateTime;

    /**
     * Constructor for objects of class Bid
     * @param bidder Bids Bidder
     * @param auctionLot Bids AuctionLot
     * @param amount Bids Amount
     * @param dateTime Bids dateTime
     */
    public Bid(Bidder bidder, AuctionLot auctionLot, Double amount, LocalDateTime dateTime) {
        this.bidder = bidder;
        this.auctionLot = auctionLot;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    //---------------------------------------------------------------//
    //Getters                                                        //
    //---------------------------------------------------------------//
    /**
     * Returns the Amount of the Bid
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * Returns the DateTime of the Bid
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Bidder getBidder() {
        return bidder;
    }

    public AuctionLot getAuctionLot() {
        return auctionLot;
    }

    //---------------------------------------------------------------//
    //Setters                                                        //
    //---------------------------------------------------------------//
    /**
     * Updates the Bids amount to the value passed
     * @param amount Bids Amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * Updates the Bids DateTime to the value passed
     * @param dateTime Bids DateTime
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    //---------------------------------------------------------------//
    //Misc Methods                                                   //
    //---------------------------------------------------------------//
    /**
     * Builds a String representing a user friendly representation of the object state
     * @return Details of the specific Patient
     */
    @Override
    public String toString() {
        return  bidder.getName() + "/n" +
                dateTime.toString() + " - €"+getAmount().toString();
    }


}
