package pandas;

import java.text.NumberFormat;

/**
 * 
 * @author jholl
 * This class is used to package together all of the information that goes into a customer's bid
 */
public class Bid {

	/**
	 * This field contains the customer that made the bid
	 */
	private Customer cust;
	/**
	 * This field contains the numerical value of the bid at present
	 */
	private double bid;
	/**
	 * This field contains the maximum bid that the customer is willing to make
	 */
	private double maxBid;
	
	
	/**
	 * Empty constructor
	 */
	public Bid() {
		
	}
	
	/**
	 * Full constructor
	 * @param cust Customer that is making the bid
	 * @param bid Present numerical value of bid
	 * @param maxBid Maximum bid amount
	 */
	public Bid(Customer cust, double bid, double maxBid) {
		this.cust = cust;
		this.bid = bid;
		this.maxBid = maxBid;
	}

	/**
	 * Sets the cust field to a Customer parameter
	 * @param cust customer the bid is being set to
	 */
	public void setCust(Customer cust) {
		this.cust = cust;
	}

	/**
	 * Returns the cust field
	 * @return Customer that made the bid
	 */
	public Customer getCust() {
		return cust;
	}

	/**
	 * Returns the bid field
	 * @return Double value of the bid field
	 */
	public double getBid() {
		return bid;
	}

	/**
	 * Sets the bid field to a double parameter
	 * @param bid Double value that the bid field is being set to
	 */
	public void setBid(double bid) {
		this.bid = bid;
	}

	/**
	 * Returns the maxBid field
	 * @return Double value contained in the maxBid field
	 */
	public double getMaxBid() {
		return maxBid;
	}

	/**
	 * Sets the maxBid field to a double parameter
	 * @param maxBid Double value that the maxBid field is being set to
	 */
	public void setMaxBid(double maxBid) {
		this.maxBid = maxBid;
	}
	
	/* 
	 * Returns formatted string of relevant fields
	 */
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return cust.getName() + " || Bid: " + nf.format(bid) + " || Max Bid:" + nf.format(maxBid);
	}
	
}
