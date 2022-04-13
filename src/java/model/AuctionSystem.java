package model;

import Utilities.CustomList;

/**
 * @author Darren Sills & Gedvydas Jucius
 * Auction system class used to store all data in the system, containing both the list of auctionLots and bidders.
 */
public class AuctionSystem {
    public CustomList<AuctionLot> auctionLots;
    public CustomList<Bidder> bidders;

    /**
     * Constructor for objects of class Auction System
     */
    public AuctionSystem() {
        auctionLots = new CustomList<>();
        bidders = new CustomList<>();
    }

    //---------------------------------------------------------------//
    //Getters                                                        //
    //---------------------------------------------------------------//
    /**
     * Returns the list of Auction Lots in the System
     */
    public CustomList<AuctionLot> getAuctionLots() {
        return auctionLots;
    }

    /**
     * Returns the list of Bidders in the System
     */
    public CustomList<Bidder> getBidders() {
        return bidders;
    }

    //---------------------------------------------------------------//
    //Setters                                                        //
    //---------------------------------------------------------------//
    /**
     * Updates the list of Auction Lots in the System to the value passed
     * @param auctionLots The list of Auction Lots in the System
     */
    public void setAuctionLots(CustomList<AuctionLot> auctionLots) {
        this.auctionLots = auctionLots;
    }

    /**
     * Updates the list of Bidders in the System to the value passed
     * @param bidders The list of Bidders in the System
     */
    public void setBidders(CustomList<Bidder> bidders) {
        this.bidders = bidders;
    }
}
