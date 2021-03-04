package pandas;

import java.util.ArrayList;

public class Item {
	private ArrayList<Bid> bids = new ArrayList<Bid>();
	private String name;
	private double minimumBid;
	private double increment;
	
	public Item() {
		
	}
	
	public Item(String name, double minimumBid, double increment) {
		this.name=name;
		this.minimumBid=minimumBid;
		this.increment=increment;
	}
	
	public double getMinimumBid() {
		return minimumBid;
	}

	public void setMinimumBid(double minimumBid) {
		this.minimumBid = minimumBid;
	}

	public double getIncrement() {
		return increment;
	}

	public void setIncrement(double increment) {
		this.increment = increment;
	}

	public ArrayList<Bid> getBids() {
		return bids;
	}

	public void setBids(ArrayList<Bid> bids) {
		bids = bids;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addBid(Bid bid) {
		this.bids.add(bid);
	}

	public String toString() {
		
		return "The item " + this.name + " currently has " + this.bids.size() + " bids" ;
	}
}
