package pandas;

import java.util.ArrayList;

public class Customer 
{
 
	private static int currentCustID = 1000;
	private int custID;
	private String name;
	private double money;
	private ArrayList<Bid> bids = new ArrayList<Bid>();
	private String password;
	private String userName;
	
	
	

	public Customer()
	{
		custID = currentCustID;
		currentCustID++;
	}
	
	public Customer(String name, double money, String userName, String password)
	{
		this.name=name;
		this.money = money;
		this.userName = userName;
		this.password = password;
		custID = currentCustID;
		currentCustID++;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
		if(bids.isEmpty())
		{
			System.out.println("You have no bids.");
		}
		else
		{
			for(int i = 0; i < bids.size(); i++)
			{
				System.out.println(bids.get(i).toString());
			}
		}
	}
	
	public void listWinningBids()
	{
		for(int i = 0; i < bids.size(); i++)
		{
			
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
   