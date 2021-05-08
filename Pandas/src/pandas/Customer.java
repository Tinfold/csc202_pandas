package pandas;

import java.util.ArrayList;

public class Customer 
{
 
	private static int currentCustID = 1000;
	private int custID;
	private String name;
	private ArrayList<Bid> bids = new ArrayList<Bid>();
	private Credentials login;
	
	public Customer()
	{
		custID = currentCustID;
		currentCustID++;
	}
	
	public Customer(String name, String username, String password)
	{
		this.name=name;
		login = new Credentials(username, password);
		custID = currentCustID;
		currentCustID++;
	}

	public Customer(String name, String username, String password, int custId)
	{
		this.name = name;
		this.login = new Credentials(username, password);
		this.custID = custId;
	}
	
	public Credentials getLogin() {
		return login;
	}

	public void setLogin(Credentials login) {
		this.login = login;
	}

	public String toString()
	{
		return "Customer " + name + " with an id " + custID;
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

	public ArrayList<Bid> getBids() {
		return bids;
	}

	public void setBids(ArrayList<Bid> bids) {
		this.bids = bids;
	}
	
	
}
   