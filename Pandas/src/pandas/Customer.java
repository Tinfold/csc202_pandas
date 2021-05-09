package pandas;

import java.util.ArrayList;

/**
 * @author jholl
 * This class contains all of necessary information for a particular customer
 */
public class Customer 
{
 
	/**
	 * field for generating unique customer IDs
	 */
	private static int currentCustID = 1000;
	/**
	 * unique customer ID
	 */
	private int custID;
	/**
	 * Customer's name
	 */
	private String name;
	/**
	 * ArrayList of bids that the customer has made
	 */
	private ArrayList<Bid> bids = new ArrayList<Bid>();
	/**
	 * Customer's login information
	 */
	private Credentials login;
	
	/**
	 * Empty constructor
	 */
	public Customer()
	{
		custID = currentCustID;
		currentCustID++;
	}
	
	/**
	 * Full constructor
	 * @param name Name to be set
	 * @param username Username to be set
	 * @param password Password to be set
	 */
	public Customer(String name, String username, String password)
	{
		this.name=name;
		login = new Credentials(username, password);
		custID = currentCustID;
		currentCustID++;
	}

	/**
	 * Full constructor
	 * @param name Name to be set
	 * @param username Username to be set
	 * @param password Password to be set
	 * @param custId Customer ID to be set
	 */
	public Customer(String name, String username, String password, int custId)
	{
		this.name = name;
		this.login = new Credentials(username, password);
		this.custID = custId;
		currentCustID = custId + 1;
	}
	
	/**
	 * Returns customer login
	 * @return customer login info
	 */
	public Credentials getLogin() {
		return login;
	}

	/**
	 * Sets customer login info
	 * @param login Login info to be set
	 */
	public void setLogin(Credentials login) {
		this.login = login;
	}

	/* 
	 * Returns formatted string containing relevant fields
	 */
	public String toString()
	{
		return "Customer " + name + " with an id " + custID;
	}
		
	/**
	 * Returns customer id
	 * @return customer id
	 */
	public int getCustID() {
		return custID;
	}

	/**
	 * Sets customer id
	 * @param custID customer id to be set
	 */
	public void setCustID(int custID) {
		this.custID = custID;
	}

	/**
	 * Returns customer name
	 * @return customer name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets customer name
	 * @param name Name to be set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns current customer id
	 * @return current customer id
	 */
	public static int getCurrentCustID() {
		return currentCustID;
	}

	/**
	 * Sets current customer id
	 * @param currentCustID current customer id to be set
	 */
	public static void setCurrentCustID(int currentCustID) {
		Customer.currentCustID = currentCustID;
	}

	/**
	 * Returns ArrayList of customer's bids
	 * @return ArrayList of customer's bids
	 */
	public ArrayList<Bid> getBids() {
		return bids;
	}

	/**
	 * Sets customer's bids
	 * @param bids Bids to be set
	 */
	public void setBids(ArrayList<Bid> bids) {
		this.bids = bids;
	}
	
	
}
   