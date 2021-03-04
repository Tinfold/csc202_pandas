package pandas;

import java.util.ArrayList;

public class Customer 
{
 
	private static int currentCustID = 1000;
	private int custID;
	private String name;
	private double money;
	private ArrayList<Bid> bids = new ArrayList<Bid>();
	
	public Customer()
	{
		custID = currentCustID;
		currentCustID++;
	}
	
	public Customer(String name)
	{
		custID = currentCustID;
		currentCustID++;
	}
	
	public String toString()
	{
		return "Customer " + name + " with an id " + custID + " has $" + money + " dollars in their account";
	}
	
	public void addBid(Bid bid)
	{
		bids.add(bid);
	}
	
	public void removeBid(Bid bid)
	{
		bids.remove(bids.indexOf(bid));
	}

	public void listBids()
	{
		for(int i = 0; i < bids.size(); i++)
		{
			System.out.println(bids.get(i).toString());
		}
	}
	
	public int getCustID() {
		return custID;
	}

	public void setCustID(int custID) {
		this.custID = custID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static int getCurrentCustID() {
		return currentCustID;
	}

	public static void setCurrentCustID(int currentCustID) {
		Customer.currentCustID = currentCustID;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public ArrayList<Bid> getBids() {
		return bids;
	}

	public void setBids(ArrayList<Bid> bids) {
		this.bids = bids;
	}
	
	
}
   