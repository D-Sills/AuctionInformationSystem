package model;

import Utilities.CustomList;

import java.net.URL;
import java.time.LocalDate;

/**
 * @author Darren Sills & Gedvydas Jucius
 * AuctionLot class used to store all data related to each Auction, as well as the list of Bids.
 */
public class AuctionLot {
    private String name;
    private String description;
    private String type;
    private LocalDate dateOfOrigin;
    private Double startingPrice;
    private URL imageURL;
    public CustomList<Bid> bids;

    /**
     * Constructor for objects of class AuctionLot
     * @param name         AuctionLot name
     * @param description  AuctionLot description
     * @param type         AuctionLot type
     * @param dateOfOrigin AuctionLot dateOfOrigin
     * @param startingPrice  AuctionLot startingPrice
     * @param imageURL     AuctionLot imageURL
     */
    public AuctionLot(String name, String description, String type, LocalDate dateOfOrigin, Double startingPrice, URL imageURL) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.dateOfOrigin = dateOfOrigin;
        this.startingPrice = startingPrice;
        this.imageURL = imageURL;
        bids = new CustomList<>();
    }

    //---------------------------------------------------------------//
    //Getters                                                        //
    //---------------------------------------------------------------//
    /**
     * Returns the name of the AuctionLot
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the Description of the AuctionLot
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the Type of the AuctionLot
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the DateOfOrigin of the AuctionLot
     */
    public LocalDate getDateOfOrigin() {
        return dateOfOrigin;
    }

    /**
     * Returns the Starting Price of the AuctionLot
     */
    public Double getStartingPrice() {
        return startingPrice;
    }

    /**
     * Returns the ImageURL of the AuctionLot
     */
    public URL getImageURL() {
        return imageURL;
    }

    /**
     * Returns the list of Bids for the AuctionLot
     */
    public CustomList<Bid> getBids() {
        return bids;
    }

    //---------------------------------------------------------------//
    //Setters                                                        //
    //---------------------------------------------------------------//
    /**
     * Updates the Auction name to the value passed
     *
     * @param name Auction name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the Auction Description to the value passed
     *
     * @param description Auction Desccription
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Updates the Auction Type to the value passed
     *
     * @param type Auction Type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Updates the Date of Origin to the value passed
     *
     * @param dateOfOrigin Auctions Date of origin
     */
    public void setDateOfOrigin(LocalDate dateOfOrigin) {
        this.dateOfOrigin = dateOfOrigin;
    }

    /**
     * Updates the Starting Price to the value passed
     *
     * @param startingPrice sets starting price
     */
    public void setStartingPrice(Double startingPrice) {
        this.startingPrice = startingPrice;
    }

    /**
     * Updates the Image Url to the URL passed
     *
     * @param imageURL sets image url
     */
    public void setImageURL(URL imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * Updates the list of Bids for the Customer to the value passed
     *
     * @param bids The list of Bids for the Customer
     */
    public void setBids(CustomList<Bid> bids) {
        this.bids = bids;
    }

    //---------------------------------------------------------------//
    //Misc Methods                                                   //
    //---------------------------------------------------------------//
    public Bid getLargestBid() {
        if (!this.getBids().isEmpty()) {
            Bid bigBid = this.getBids().get(0);
            Double bigBidNum = bigBid.getAmount();
            for (Bid bid : this.getBids())
                if (bid instanceof WinningBid) return bid;
                else
                if (bigBidNum < bid.getAmount()) {
                    bigBidNum = bid.getAmount();
                    bigBid = bid;
                }
            return bigBid;
        }
        return null;
    }

    /**
     * Builds a String representing a user friendly representation of the object state
     * @return Details of the specific Patient
     */
    @Override
    public String toString() {
        return  getName();
    }
}
