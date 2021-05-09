package pandas;

import java.util.ArrayList;

/**
 * @author jholl
 * This class contains all of the necessary information for a particular item
 */
public class Item {

	/**
	 * Stack of bids on the item
	 */
	private Stack<Bid> bids = new Stack<Bid>();

	/**
	 * Name of the item
	 */
	private String name;
	/**
	 * Minimum allowed bid on the item
	 */
	private double minimumBid;
	/**
	 * Increment between bids
	 */
	private double increment;
	/**
	 * Number of bids on the item
	 */
	private int bidCount;
	
	/**
	 * Empty constructor
	 */
	public Item() {
		
	}
	
	/**
	 * Full constructor
	 * @param name name to be set
	 * @param minimumBid minimum bid to be set
	 * @param increment increment to be set
	 */
	public Item(String name, double minimumBid, double increment) {
		this.name=name;
		this.minimumBid=minimumBid;
		this.increment=increment;
	}
	
	/**
	 * Displays bids of the item
	 */
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
	
	/**
	 * Returns minimum bid
	 * @return minimum bid value
	 */
	public double getMinimumBid() {
		return minimumBid;
	}

	/**
	 * Sets minimum bid
	 * @param minimumBid minimum bid to be set
	 */
	public void setMinimumBid(double minimumBid) {
		this.minimumBid = minimumBid;
	}

	/**
	 * Returns increment
	 * @return increment between bids
	 */
	public double getIncrement() {
		return increment;
	}

	/**
	 * Sets increment
	 * @param increment increment to be set
	 */
	public void setIncrement(double increment) {
		this.increment = increment;
	}	

	/**
	 * Returns stack of bids
	 * @return Stack of bids for the item
	 */
	public Stack<Bid> getBids() {
		return bids;
	}

	/**
	 * Sets stack of bids
	 * @param bids Stack of bids to be set
	 */
	public void setBids(Stack<Bid> bids) {
		this.bids = bids;
	}

	/**
	 * Returns number of bids on item
	 * @return number of bids on item
	 */
	public int getBidCount() {
		return bidCount;
	}

	/**
	 * Sets number of bids on the item
	 * @param bidCount number of bids to be set
	 */
	public void setBidCount(int bidCount) {
		this.bidCount = bidCount;
	}

	/**
	 * Returns name of the item
	 * @return name of the item
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name of the item
	 * @param name Name to be set
	 */
	public void setName(String name) {
		this.name = name;
	}

/*
	public void addBid(Bid bid) {
		this.bids.add(bid);
		bidHistory.push(bid);
*/
  
	/**
	 * Adds bid to stack
	 * @param bid Bid to be added
	 */
	public void addBid(Bid bid) 
	{
		this.bids.push(bid);
		this.bidCount++;

	}

	/* 
	 * Returns formatted string containing relevant fields
	 */
	public String toString() {
		
		return "The item " + this.name + " is currently being auctioned and has " + bidCount + " bids";
	}
}
