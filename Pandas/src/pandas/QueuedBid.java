package pandas;

/**
 * @author jholl
 * This class is used to distinguish bids that are being read in from those that are entered manually
 */
public class QueuedBid extends Bid {
	/**
	 * Item being queued
	 */
	private Item forItem;

	/**
	 * Empty constructor
	 */
	public QueuedBid() {
		
	}
	
	/**
	 * Full constructor
	 * @param forItem item to be set
	 * @param cust customer to be set
	 * @param bid bid to be set
	 * @param maxBid maximum bid to be set
	 */
	public QueuedBid(Item forItem, Customer cust, double bid, double maxBid) {
		super(cust,bid,maxBid);
		this.forItem=forItem;
	}
	
	/**
	 * Returns queued item
	 * @return queued item
	 */
	public Item getForItem() {
		return forItem;
	}

	/**
	 * Sets queued item
	 * @param forItem item to be set
	 */
	public void setForItem(Item forItem) {
		this.forItem = forItem;
	}
}
