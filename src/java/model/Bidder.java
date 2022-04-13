package model;

/**
 * @author Darren Sills & Gedvydas Jucius
 * Bidder class used to store all data related to each Bidder.
 */
public class Bidder implements Comparable<Bidder>{
    private String name;
    private String address;
    private String phone;
    private String email;

    /**
     * Constructor for objects of class Bidder
     * @param name Bidders name
     * @param address Bidders address
     * @param phone Bidders phone number
     * @param email Bidders email
     */
    public Bidder(String name, String address, String phone, String email) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    //---------------------------------------------------------------//
    //Getters                                                        //
    //---------------------------------------------------------------//
    /**
     * Returns the name of the Bidder
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the address of the Bidder
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the phone number of the Bidder
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Returns the email of the Bidder
     */
    public String getEmail() {
        return email;
    }

    //---------------------------------------------------------------//
    //Setters                                                        //
    //---------------------------------------------------------------//
    /**
     * Updates the Bidders name to the value passed
     * @param name Bidders name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the Bidders address to the value passed
     * @param address Bidders address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Updates the Bidders phone number to the value passed
     * @param phone Bidders phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Updates the Bidders email to the value passed
     * @param email Bidders email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    //---------------------------------------------------------------//
    //Misc Methods                                                   //
    //---------------------------------------------------------------//
    /**
     * Checks equality between Bidders by comparing the parameters of two different Bidders objects
     */
    public boolean equals(Bidder otherBidder){
        return  (this.name.equals(otherBidder.getName()) && //checks if the first names are the same
                this.address.equals(otherBidder.getAddress())  &&
                this.phone.equals(otherBidder.getPhone())  &&//checks if the Phone Numbers are the same
                this.email.equals(otherBidder.getEmail())); //checks if the Emails are the same
    }  //all need to be the same for the objects to be equivalent


    /**
     * Builds a String representing a user friendly representation of the object state
     * @return Details of the specific Patient
     */
    @Override
    public String toString() {
        return  getName();
    }

    @Override
    public int compareTo(Bidder b) {
        return this.name.compareTo(b.name);
    }
}
