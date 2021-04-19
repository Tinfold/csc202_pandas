package pandas;

import java.text.NumberFormat;

public class Bid {

	private Customer cust;
	private double bid;
	private double maxBid;
	
	
	public Bid() {
		
	}
	
	public Bid(Customer cust, double bid, double maxBid) {
		this.cust = cust;
		this.bid = bid;
		this.maxBid = maxBid;
	}

	public void setCust(Customer cust) {
		this.cust = cust;
	}

	public Customer getCust() {
		return cust;
	}

	public void setId(Customer cust) {
		this.cust = cust;
	}

	public double getBid() {
		return bid;
	}

	public void setBid(double bid) {
		this.bid = bid;
	}

	public double getMaxBid() {
		return maxBid;
	}

	public void setMaxBid(double maxBid) {
		this.maxBid = maxBid;
	}
	
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return cust.getName() + " || Bid: " + nf.format(bid) + " || Max Bid:" + nf.format(maxBid);
	}
	
}
