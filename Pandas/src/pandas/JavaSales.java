package pandas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class JavaSales {

	public static void main(String[] args) 
	{		
		ArrayList<Item> paint = new ArrayList<>();
		ArrayList<Item> completedAuctions = new ArrayList<Item>();
		ArrayList<Customer> clients = new ArrayList<>();

		Credentials adminLogin = new Credentials("admin", "password");

		int sentinel = 0;
		while (sentinel != 7) {
			int selection = mainMenu();
			if (selection == 1) {
				auctionSetup(paint, clients);
				System.out.println("Sample data loaded.");
			} else if (selection == 2) {
				System.out.println("Not Implemented Yet.");
			} else if (selection == 3) {
				boolean sLogin = attemptAdminLogIn(adminLogin);
				if (sLogin) {
					adminMenu(paint, clients, completedAuctions);
				}
			} else if (selection == 4) {
				custLMenu(clients, paint);
			}
			else if(selection == 5)
			{
				loadDatabase(clients, paint);
			}
			else if(selection == 6)
			{
				saveDatabase(clients, paint);
			}

			System.out.println();
		}
	}

	public static Connection connectToDatabase()
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("What is the username for the database?");
		String user = scan.nextLine();
		System.out.println("What is the password?");
		String pass = scan.nextLine();
		return JDBCConnection.connect(JDBCConnection.MYSQLLOCAL,user,pass);
	}
	
	public static void loadDatabase(ArrayList<Customer> custs, ArrayList<Item> auctions)
	{
		Connection con = connectToDatabase();
		Statement stmt;
		try {
			stmt = con.createStatement();
			
			String queryCustomer = "select * from customers";
			ResultSet rs = stmt.executeQuery(queryCustomer);
			
			while(rs.next())
			{
				int custId = rs.getInt(1);
				String name = rs.getString(2);
				String username = rs.getString(3);
				String password = rs.getString(4);
				custs.add(new Customer(name, username, password, custId));
			}
			
			String queryItems = "select * from items";
			ResultSet rs2 = stmt.executeQuery(queryItems);
			while(rs2.next())
			{
				String name = rs2.getString(1);
				double minBid = rs2.getDouble(2);
				double increment = rs2.getDouble(3);
				auctions.add(new Item(name, minBid, increment));
			}
			
			String queryBids = "select * from bids";
			ResultSet rs3 = stmt.executeQuery(queryBids);
			while(rs3.next())
			{
				double bid = rs3.getDouble(1);
				double maxBid = rs3.getDouble(2);
				int custId = rs3.getInt(3);
				String itemName = rs3.getString(4);
				
				Bid newBid = null;
				for(int i = 0; i < custs.size(); i++)
				{
					if(custs.get(i).getCustID() == custId)
					{
						newBid = new Bid(custs.get(i), bid, maxBid);
						custs.get(i).getBids().add(newBid);
					}
				}
				
				for(int i = 0; i < auctions.size(); i++)
				{
					if(auctions.get(i).getName().equals(itemName) && newBid != null)
					{
						auctions.get(i).addBid(newBid);
					}
				}
			}
			
			con.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveDatabase(ArrayList<Customer> custs, ArrayList<Item> auctions)
	{
		Connection con = connectToDatabase();
		
		Statement stmt;
		try 
		{
			stmt = con.createStatement();

			stmt.execute("delete * from customers");
			stmt.execute("delete * from bids");
			stmt.execute("delete * from auctions");
			
			for(int i = 0; i < custs.size(); i++)
			{
				Customer cust = custs.get(i);
				String insert = "insert into customers values(" + cust.getCustID() + "," + cust.getName() + "," + cust.getLogin().getU() + "," + cust.getLogin().getP() + ")";
				stmt.execute(insert);
			}
			
			for(int i = 0; i < auctions.size(); i++)
			{
				Item item = auctions.get(i);
				String insert = "insert into items values(" + item.getName() + "," + item.getMinimumBid() + "," + item.getIncrement() + ")";
				stmt.execute(insert);
				
				Stack<Bid> bids = item.getBids().clone();
				while(bids.isEmpty() == false)
				{
					Bid bid = bids.pop();
					insert = "insert into bids values(" + bid.getBid() + "," + bid.getMaxBid() + "," + bid.getCust().getCustID() + "," + item.getName() + ")";
					stmt.execute(insert);
				}
			}
			con.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void auctionSetup(ArrayList<Item> p, ArrayList<Customer> clients) {

		System.out.println("This method is deprecated, please load data from the database instead.");
		Customer testCustomer = new Customer("John Doe", "jdoe", "password");

		clients.add(testCustomer);
		p.add(new Item("The Starry Night", 12000, 500));
		p.get(0).addBid(new Bid(clients.get(0), 24000, 32000));
		p.get(0).addBid(new Bid(clients.get(0), 35000, 36000));
		p.get(0).addBid(new Bid(clients.get(0), 40000, 45000));
		p.add(new Item("Mona Lisa", 18000, 1000));
		p.add(new Item("American Gothic", 9000, 200));
		p.add(new Item("The Storm on the Sea of Galilee", 6000, 100));
		p.add(new Item("The Last Supper", 50000, 5000));

	}

	public static int mainMenu() {
		Scanner scan = new Scanner(System.in);

		while (true) {
			System.out.println("Please enter the number that corresponds with the action you would like to perform");
			System.out.println("1: Load sample data (deprecated)");
			System.out.println("2: Process the backlogged data");
			System.out.println("3: Log in as administrator");
			System.out.println("4: Log in as customer");
			System.out.println("5: Load Database");
			System.out.println("6: Save Database");
			System.out.println("7: Exit the application");
			int select = -1;
			try {
				select = scan.nextInt();
			} catch (InputMismatchException ime) {
				System.out.println("You did not enter an integer value");
			}
			if (select == 1 || select == 2 || select == 3 || select == 4 || select == 5)
				return select;
			else
				System.out.println("You did not enter a valid selection");
		}
	}

	public static void custLMenu(ArrayList<Customer> clients, ArrayList<Item> paint) {
		Scanner scan = new Scanner(System.in);

		int select = -1;
		while (select != 3) {
			System.out.println("Please enter the number that corresponds with the action you would like to perform");
			System.out.println("1: Returning customer");
			System.out.println("2: New customer");
			System.out.println("3: Return to main menu");
			try {
				select = scan.nextInt();
			} catch (InputMismatchException ime) {
				System.out.println("You did not enter an integer value");
			}
			if (select == 1)
			{
				Customer cust = attemptLogIn(clients);
				if(cust != null)
				{
					custMenu(cust, paint);
					select = 3;
				}
			}
			else if(select == 2)
			{
				Customer cust = newCustomer();
				if(cust != null)
				{
					clients.add(cust);
					custMenu(cust, paint);
					select = 3;
				}
			}
			else if(select != 3)
			{
				System.out.println("You did not enter a valid selection");
			}
			
		}
	}

	public static void adminMenu(ArrayList<Item> paint, ArrayList<Customer> clients, ArrayList<Item> completedAuctions) {
		Scanner scan = new Scanner(System.in);

		int select = -1;
		while(select != 6)
		{
			System.out.println("Please enter the number that corresponds with the action you would like to perform");
			System.out.println("1: List current ongoing auctions");
			System.out.println("2: Choose an ongoing auction and check the bidding history");
			System.out.println("3: List information about completed auctions");
			System.out.println("4: Summary data of winning bids");
			System.out.println("5: Add and activate a new auction");
			System.out.println("6: Return to main menu");
			
			try {
				select = scan.nextInt();
			} catch (InputMismatchException ime) {
				System.out.println("You did not enter an integer value");
			}
			if (select == 1)
			{
				listAuctions(paint);
			}
			else if(select == 2)
			{
				Item item = selectAuction(paint);
				item.checkBiddingHistory();
			}
			else if(select == 3)
			{
				listCompletedAuctions(completedAuctions);
			}
			else if(select == 4)
			{
				listWinningBids(paint);
			}
			else if(select == 5)
			{
				Item item = newAuction();
				if(item != null)
				{
					paint.add(item);
					System.out.println("Auction successfully created");
				}
			}
			else if(select != 6)
			{
				System.out.println("You did not enter a valid selection");
			}
		}
	}

	public static void custMenu(Customer cust, ArrayList<Item> paint) {
		Scanner scan = new Scanner(System.in);

		int select = -1;
		while(select != 5)
		{
			System.out.println("Please enter the number that corresponds with the action you would like to perform");
			System.out.println("1: Check my active bids");
			System.out.println("2: Check my winning bids");
			System.out.println("3: Bid on an item");
			System.out.println("4: Return to main menu (log out)");
			try {
				select = scan.nextInt();
			} catch (InputMismatchException ime) {
				System.out.println("You did not enter an integer value");
			}
			if (select == 1)
			{
				listCustomerBids(paint, cust);
			}
			else if(select == 2)
			{
				checkWinningBids(paint, cust);
			}
			else if(select == 3)
			{
				bid(paint, cust);
			}
			else if(select != 4)
			{
				System.out.println("You did not enter a valid selection");
			}
		}
	}
	
	public static boolean attemptAdminLogIn(Credentials adminPassword) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please input the admin password.");
		if (scan.nextLine().equals(adminPassword.getP())) {
			System.out.println("You have successfully logged in as an admin.");
			return true;
		} else {
			System.out.println("Invalid password.");
			return false;
		}
	}

	public static Customer attemptLogIn(ArrayList<Customer> clients) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please input your username.");
		String userName = scan.nextLine();
		System.out.println("Please input your password.");
		String password = scan.nextLine();

		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).getLogin().getU().equals(userName) && clients.get(i).getLogin().getP().equals(password)) {
				System.out.println("You have succesfully logged in.");
				return clients.get(i);
			}
		}

		System.out.println("Invalid credentials.");
		return null;
	}
	
	public static Customer newCustomer()
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Input your first and last name:");
		String name = scan.nextLine();
		scan.nextLine();
		System.out.println("Input your username:");
		String username = scan.nextLine();
		System.out.println("Input your password:");
		String password = scan.nextLine();
		
		return new Customer(name, username, password);
	}
	
	public static void bid(ArrayList<Item> paint, Customer cust)
	{
		Scanner scan = new Scanner(System.in);

		System.out.println("Select an item to bid on:");
		Item painting = selectAuction(paint);
		ArrayList<Bid> uniqueCustomerBids = new ArrayList<Bid>();

		Stack<Bid> temp = painting.getBids().clone();
		
		while(temp.isEmpty() == false)
		{
			Bid bid = painting.getBids().pop();
			if(bid.getCust().getCustID() == cust.getCustID())
			{
				uniqueCustomerBids.add(bid);
			}
		}

		String bidText = "No bid";

		if (uniqueCustomerBids.size() > 0) {
			bidText = "" + uniqueCustomerBids.get(uniqueCustomerBids.size() - 1).getBid();
		}

		System.out.println("Bid Menu:");

		System.out.println("Your Current Bid: " + bidText);
		System.out.println("---------------------------------------------");
		System.out.println("Current highest bid on item:");

		double minBid = painting.getMinimumBid();
		if (painting.getBidCount() > 0) {
			Bid highestBid = painting.getBids().peek();
			System.out.println(highestBid.toString());

			System.out.println("---------------------------------------------");
			System.out.println("You must bid at least " + NumberFormat.getCurrencyInstance()
					.format(highestBid.getBid() + painting.getIncrement()));
			minBid = highestBid.getBid() + painting.getIncrement();
		} else {
			System.out.println("You must bid at least " + painting.getMinimumBid());
		}
		System.out.println("Would you like to make a bid on this item?");

		System.out.println("1) Make bid on item");
		System.out.println("2) Return to customer menu");

		int bidSelection = scan.nextInt();
		if (bidSelection == 1) 
		{
			System.out.println("Enter a legal bid value:");
			double myBid = scan.nextDouble();
			
			Bid topBid = null;
			if(painting.getBids().isEmpty() == false)
			{
				topBid = painting.getBids().peek();
			}
			
			if(myBid < minBid)
			{
				System.out.println("Your bid was not added because it was lower than the highest bid");
			}
			else if(topBid != null && myBid < topBid.getMaxBid())
			{
				System.out.println("Your bid was not added because it was lower than the highest bid's maximum bid.");
				painting.addBid(new Bid(topBid.getCust(), myBid, topBid.getMaxBid()));
			}
			else
			{
				System.out.println("What is the maximum bid you would like to pay:");
				double maxBid = scan.nextDouble();
				System.out.println("Your bid was successfully added.");
				painting.addBid(new Bid(cust, myBid, maxBid));
			}
		}
	}
	
	public static void listAuctions(ArrayList<Item> paint)
	{
		if(paint.isEmpty())
		{
			System.out.println("There are no ongoing auctions.");
		}
		
		for (int i = 0; i < paint.size(); i++) {
			System.out.println(paint.get(i).toString());
		}
	}
	
	public static Item selectAuction(ArrayList<Item> paint)
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Choose an item:");
		for (int i = 0; i < paint.size(); i++) {
			System.out.println((i + 1) + ") " + paint.get(i).getName());
		}
		int paintSelection = scan.nextInt();

		if (paintSelection > 0 && paintSelection < paint.size() + 1) {
			return paint.get(paintSelection - 1);
		}
		else
		{
			System.out.println("That is not a valid selection.");
			return null;
		}
	}
	
	public static void listCustomerBids(ArrayList<Item> paint, Customer cust)
	{
		System.out.println("Your current bids are:");
		if(paint.isEmpty())
		{
			System.out.println("There are no ongoing auctions.");
		}
		if(cust.getBids().isEmpty())
		{
			System.out.println("You currently have no bids.");
		}
		
		for(int i = 0; i < paint.size(); i++)
		{
			System.out.println(paint.get(i).getName());
			Stack<Bid> bids = paint.get(i).getBids().clone();
			while(bids.isEmpty() == false)
			{
				Bid bid = bids.pop();
				if(cust.getCustID() == bid.getCust().getCustID())
				{
					System.out.println(bid.toString());
					System.out.println("");
				}
			}
		}
	}
	
	public static void listCompletedAuctions(ArrayList<Item> auctions)
	{
		if(auctions.isEmpty())
		{
			System.out.println("No auctions have been completed yet.");
		}
		
		for(int i = 0; i < auctions.size(); i++)
		{
			System.out.println(auctions.get(i).toString());
			auctions.get(i).checkBiddingHistory();
			System.out.println();
		}
	}
	
	public static void checkWinningBids(ArrayList<Item> auctions, Customer cust)
	{
		System.out.println("Your winning bids are:");
		if(auctions.isEmpty())
		{
			System.out.println("There are no ongoing auctions.");
		}
		if(cust.getBids().isEmpty())
		{
			System.out.println("You currently have no bids.");
		}
		
		for(int i = 0; i < auctions.size(); i++)
		{
			Stack<Bid> bids = auctions.get(i).getBids();
			if(bids.isEmpty() == false && cust.getCustID() == bids.peek().getCust().getCustID())
			{
				System.out.println(auctions.get(i).getName());
				System.out.println(bids.peek().toString());
				System.out.println("");
			}
		}
	}
	
	public static void listWinningBids(ArrayList<Item> auctions)
	{
		if(auctions.isEmpty())
		{
			System.out.println("There are no auctions currently ongoing.");
		}
		
		for(int i = 0; i < auctions.size(); i++)
		{
			System.out.println(auctions.get(i).toString());
			Stack<Bid> bids = auctions.get(i).getBids();
			if(bids.isEmpty() == false)
			{
				Bid bid = bids.peek();
				System.out.println("The winning bid for this auction is " + bid.toString());
			}
			System.out.println("");
		}
	}
		
	public static Item newAuction()
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Input the name of the item:");
		String name = scan.nextLine();
		System.out.println("Input the minimum bid:");
		double minBid = scan.nextDouble();
		System.out.println("Input the increment:");
		double increment = scan.nextDouble();
		
		return new Item(name, minBid, increment);
	}
	
	//overload for junit test
	public static Item newAuction(String name, double minBid, double increment)
	{		
		return new Item(name, minBid, increment);
	}
}
