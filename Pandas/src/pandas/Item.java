package pandas;

import java.util.ArrayList;

public class Item {
	private Stack<Bid> bids = new Stack<Bid>();
	private String name;
	private double minimumBid;
	private double increment;
	private int bidCount;
	
	public Item() {
		
	}
	
	public Item(String name, double minimumBid, double increment) {
		this.name=name;
		this.minimumBid=minimumBid;
		this.increment=increment;
	}
	
	public void checkBiddingHistory()
	{
		if(bids.isEmpty())
		{
			System.out.println("This auction has no bids.");
		}
		
		Stack<Bid> temp = bids.clone();
		while(temp.isEmpty() == false)
		{
			System.out.println(temp.pop().toString());
		}
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

	public Stack<Bid> getBids() {
		return bids;
	}

	public void setBids(Stack<Bid> bids) {
		this.bids = bids;
	}

	public int getBidCount() {
		return bidCount;
	}

	public void setBidCount(int bidCount) {
		this.bidCount = bidCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addBid(Bid bid) 
	{
		bids.push(bid);
		bidCount++;
	}

	public String toString() {
		
		return "The item " + this.name + " is currently being auctioned and has " + bidCount + " bids";
	}
}
