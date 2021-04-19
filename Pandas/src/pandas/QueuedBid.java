package pandas;

public class QueuedBid extends Bid {
	private Item forItem;

	public QueuedBid() {
		
	}
	
	public QueuedBid(Item forItem, Customer cust, double bid, double maxBid) {
		super(cust,bid,maxBid);
		this.forItem=forItem;
	}
	
	public Item getForItem() {
		return forItem;
	}

	public void setForItem(Item forItem) {
		this.forItem = forItem;
	}
}
