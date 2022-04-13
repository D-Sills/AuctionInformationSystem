package model;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Darren Sills & Gedvydas Jucius
 * SoldAuctionLot class used to store all data related to each Sold Auction Lot.
 */
public class SoldAuctionLot extends AuctionLot{
    private LocalDateTime dateOfSale;
    private Double finalPrice;

    /**
     * Constructor for objects of class AuctionLot
     * @param name         Auction name
     * @param description  Auction PPSN
     * @param type         Auction type
     * @param dateOfOrigin Auction Date of origin
     * @param startingPrice  Auction Starting Price
     * @param imageURL     Auction imageUrl
     * @param finalPrice  Auction Final price
     * @param dateOfSale Auction date of sale
     */
    public SoldAuctionLot(String name, String description, String type, LocalDate dateOfOrigin, Double startingPrice, URL imageURL, Double finalPrice, LocalDateTime dateOfSale) {
        super(name,description,type,dateOfOrigin, startingPrice, imageURL);
        this.finalPrice = finalPrice;
        this.dateOfSale = dateOfSale;
    }

    public LocalDateTime getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(LocalDateTime dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

}
