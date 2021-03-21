package pandas;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class JavaSales {

	public static void main(String[] args) {

		ArrayList<Item> paint = new ArrayList<>();
		ArrayList<Customer> clients = new ArrayList<>();

		String adminPassword = "password";

		Customer testCustomer = new Customer("John Doe", 99999, "jdoe", "password");

		clients.add(testCustomer);

		int sentinel = 0;
		while (sentinel != 5) {
			int selection = mainMenu();
			if (selection == 1) {
				auctionSetup(paint);
				System.out.println("Sample data loaded.");
			} else if (selection == 2) {
				System.out.println("Not Implemented Yet.");
			} else if (selection == 3) {
				boolean sLogin = attemptAdminLogIn(adminPassword);
				if (sLogin) {
					adminMenu(paint, clients);
				}
			} else if (selection == 4) {
				custLMenu(clients, paint);
			}

			System.out.println();
		}
	}

	public static void auctionSetup(ArrayList<Item> p) {

		p.add(new Item("The Starry Night", 12000, 500));
		p.add(new Item("Mona Lisa", 18000, 1000));
		p.add(new Item("American Gothic", 9000, 200));
		p.add(new Item("The Storm on the Sea of Galilee", 6000, 100));
		p.add(new Item("The Last Supper", 50000, 5000));

	}

	public static int mainMenu() {
		Scanner scan = new Scanner(System.in);

		while (true) {
			System.out.println("Please enter the number that corresponds with the action you would like to perform");
			System.out.println("1: Load sample data");
			System.out.println("2: Process the backlogged data");
			System.out.println("3: Log in as administrator");
			System.out.println("4: Log in as customer");
			System.out.println("5: Exit the application");
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

	public static void adminMenu(ArrayList<Item> paint, ArrayList<Customer> clients) {
		Scanner scan = new Scanner(System.in);

		System.out.println("Please enter the number that corresponds with the action you would like to perform");
		System.out.println("1: List current ongoing auctions");
		System.out.println("2: Choose an ongoing auction and check the bidding history");
		System.out.println("3: List information about completed auctions");
		System.out.println("4: Summary data of winning bids");
		System.out.println("5: Add and activate a new auction");
		System.out.println("6: Return to main menu");
		int select = -1;
		while(select != 6)
		{
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
				
			}
			else if(select == 4)
			{
				
			}
			else if(select == 5)
			{
				Item item = newAuction();
				if(item != null)
				{
					paint.add(item);
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

		System.out.println("Please enter the number that corresponds with the action you would like to perform");
		System.out.println("1: Check my active bids");
		System.out.println("2: Check my winning bids");
		System.out.println("3: Bid on an item");
		System.out.println("4: Pay for an item that I won");
		System.out.println("5: Return to main menu (log out)");
		int select = -1;
		while(select != 5)
		{
			try {
				select = scan.nextInt();
			} catch (InputMismatchException ime) {
				System.out.println("You did not enter an integer value");
			}
			if (select == 1)
			{
				cust.listBids();
			}
			else if(select == 2)
			{
				cust.listWinningBids();
			}
			else if(select == 3)
			{
				bid(paint, cust);
			}
			else if(select == 4)
			{
				
			}
			else if(select != 5)
			{
				System.out.println("You did not enter a valid selection");
			}
		}
	}
	
	public static boolean attemptAdminLogIn(String adminPassword) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please input the admin password.");
		if (scan.nextLine().equals(adminPassword)) {
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
			if (clients.get(i).getUserName().equals(userName) && clients.get(i).getPassword().equals(password)) {
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
		System.out.println("Input the amount of money you would like to deposit:");
		double money = scan.nextDouble();
		scan.nextLine();
		System.out.println("Input your username:");
		String username = scan.nextLine();
		System.out.println("Input your password:");
		String password = scan.nextLine();
		
		return new Customer(name, money, username, password);
	}
	
	public static void bid(ArrayList<Item> paint, Customer cust)
	{
		Scanner scan = new Scanner(System.in);

		System.out.println("Select an item to bid on:");
		Item painting = selectAuction(paint);
		ArrayList<Bid> uniqueCustomerBids = new ArrayList<Bid>();

		for (int i = 0; i < painting.getBids().size(); i++) {
			if (painting.getBids().get(i).getCust().getCustID() == cust.getCustID()) {
				uniqueCustomerBids.add(painting.getBids().get(i));
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
		if (painting.getBids().size() > 0) {
			Bid highestBid = painting.getBids().get(painting.getBids().size() - 1);
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
		if (bidSelection == 1) {
			System.out.println("Enter a legal bid value:");
			double myBid = scan.nextDouble();
			if (myBid >= minBid) {
				painting.addBid(new Bid(cust, myBid, 9999999)); // What is "maxBid" for
																	// again?
				System.out.println("Bid successful!");
			} else {
				System.out.println("You must bid at least "
						+ NumberFormat.getCurrencyInstance().format(minBid));
			}
		}
	}
	
	public static void listAuctions(ArrayList<Item> paint)
	{
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
	
	public static void listCompletedAuctions()
	{
		
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
