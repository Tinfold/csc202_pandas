package pandas;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class JavaSales {


	public static int timeHours=9;
	public static int timeMinutes=0;
	
	public static void main(String[] args) 
	{		
		ArrayList<Item> paint = new ArrayList<>();
		ArrayList<Item> completedAuctions = new ArrayList<Item>();
		ArrayList<Customer> clients = new ArrayList<>();

		Queue<QueuedBid> queuedBids = new Queue<QueuedBid>();
		
		Credentials adminLogin = new Credentials("admin", "password");

		int sentinel = 0;
		while (sentinel != 7) {
			int selection = mainMenu();
			if (selection == 1) {

				clients=inputData();

				auctionSetup(paint);

				System.out.println("Sample data loaded.");
				
			} else if (selection == 2) {
				System.out.println("Processing backlogged data...");
				while (!queuedBids.isEmpty()) {
					QueuedBid bid=queuedBids.dequeue();
					processBid(bid.getForItem(),bid.getCust(),bid.getBid(),bid.getMaxBid());
				}
			} else if (selection == 3) {
				boolean sLogin = attemptAdminLogIn(adminLogin);
				if (sLogin) {
					adminMenu(paint, clients, completedAuctions);
				}
			} else if (selection == 4) {
				custLMenu(clients, paint);
			}
			else if(selection==0) {
				if (queuedBids.isEmpty() || (timeHours>16 || timeHours!=9)) {
					generateBids(paint,clients,queuedBids);
					incrementTime();
				}
				else {
					System.out.println("You must process the backlogged data before resuming business as usual.");
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

	public static BufferedReader openRead() {
		Frame f = new Frame();
		// decide from where to read the file
		FileDialog foBox = new FileDialog(f, "Pick location for reading your file", FileDialog.LOAD);
		System.out.println("The dialog box will appear behind Eclipse.  " + 
		      "\n   Choose where you would like to read from.");
		foBox.setVisible(true);
		// get the absolute path to the file
		String foName = foBox.getFile();
		String dirPath = foBox.getDirectory();

		// create a file instance for the absolute path
		File inFile = new File(dirPath + foName);
		if (!inFile.exists()) {
			System.out.println("That file does not exist");
			System.exit(0);
		}
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(inFile));
		} catch (IOException e) {
			System.out.println("You threw an exception. ");
		}
		return in;
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

	
	public static ArrayList<Customer> inputData() {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		
		String first;
		String last;
		String user;
		String pass;
		
		BufferedReader bf = null;
		try {

			bf = openRead();

			String line = "";
			try {
				line = bf.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				line = bf.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String delim = ",";
			
			while (line != null) { 
				StringTokenizer st = new StringTokenizer(line, delim);
				while (st.hasMoreTokens()) { 
					first=st.nextToken().trim();
					last=st.nextToken().trim();
					user=st.nextToken().trim();
					pass=st.nextToken().trim();
					Customer newCustomer=new Customer((first + " " + last),user,pass);
					customers.add(newCustomer);
				}
				try {
					line = bf.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 

		}
		catch (Exception e) {
			System.out.println("Other weird things happened");
			e.printStackTrace();
		} finally {
			try {
				bf.close();
			} catch (Exception e) {
			}
		}
		return customers;

	}
	
	
	
	public static void auctionSetup(ArrayList<Item> p) {
		System.out.println("This method is deprecated, please load data from the database instead.");
		Customer testCustomer = new Customer("John Doe", "jdoe", "password");

		clients.add(testCustomer);
		p.add(new Item("The Starry Night", 12000, 500));
		p.add(new Item("Mona Lisa", 18000, 1000));
		p.add(new Item("American Gothic", 9000, 200));
		p.add(new Item("The Storm on the Sea of Galilee", 6000, 100));
		p.add(new Item("The Last Supper", 50000, 5000));
	}

	public static int mainMenu() {
		Scanner scan = new Scanner(System.in);

		
		while (true) {
			
			boolean AM=true;
			
			String hourString=""+timeHours;
			String openString="The auction house is open.";
			
			if (timeHours>=17 | timeHours<9) {
				openString="The auction house is closed.";
			}
						
			String amPM = "AM";
			
			if (timeHours>12) {
				AM=false;
				hourString=""+ (timeHours-12); // convert to AM / PM system
			}
			
			if (AM==false) {
				amPM="PM";
			} 
			
			String minString=""+timeMinutes;

			if (minString.length()==1) {
				minString="0" + minString; // make minutes 0-9 display correctly
			}
			
			System.out.println("It is currently " + hourString + ":" + minString + " " + amPM + ". " + openString);
			System.out.println("Please enter the number that corresponds with the action you would like to perform");
			System.out.println("0: ADVANCE TIME/BIDS (bids are randomly generated and automated)");
			System.out.println("1: Load CUSTOMERS from file (deprecated)");
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
			if (select == 1 || select == 2 || select == 3 || select == 4 || select == 5 || select == 0)
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

	public static void adminMenu(ArrayList<Item> paint, ArrayList<Customer> clients, ArrayList<Item> completed) {
        Scanner scan = new Scanner(System.in);


        int select = -1;
        while(select != 7)
        {
            System.out.println("Please enter the number that corresponds with the action you would like to perform");
            System.out.println("1: List current ongoing auctions");
            System.out.println("2: Choose an ongoing auction and check the bidding history");
            System.out.println("3: List information about completed auctions");
            System.out.println("4: Summary data of winning bids");
            System.out.println("5: Add and activate a new auction");
            System.out.println("6: Close an auction");
            System.out.println("7: Return to main menu");
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
                listCompletedAuctions(completed);
            }
            else if(select == 4)
            {
                winningSummary(completed);
            }
            else if(select == 5)
            {
                Item item = newAuction();
                if(item != null)
                {
                    paint.add(item);
                }
            }
            else if(select == 6) 
            {
                closeAuction(paint, completed);
            }
            else if(select != 7)
            {
                System.out.println("You did not enter a valid selection");
            }
        }
    }
	
	public static void winningSummary(ArrayList<Item> completed) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        double count = 0;
        System.out.println("Completed Auctions:\n");
        for(int i = 0; i < completed.size(); i++) {
            count += completed.get(i).getBids().peek().getBid();
            System.out.println(completed.get(i).getName());
            System.out.println("Winner: " + completed.get(i).getBids().peek().getCust().getName());
            System.out.println("Price: " + nf.format(completed.get(i).getBids().peek().getBid()));
            System.out.println();
        }
        
        System.out.println("The total expected payment for all of the completed auctions is " + nf.format(count));
    }
	
	public static void closeAuction(ArrayList<Item> paint, ArrayList<Item> completed) {
        Scanner scan = new Scanner(System.in);
        for(int i = 0; i < paint.size(); i++) {
            System.out.println((i+1) + ": " + paint.get(i).getName());
        }
        System.out.println("Please select which auction you would like to close");
        int select = -1;
        try {
            select = scan.nextInt()-1;
        } catch (Exception e) {
            System.out.println("An error occured with your selection");
        }
        System.out.println(paint.get(select).getName() + " has been removed from the auction");
        completed.add(paint.get(select));
        paint.remove(select);
    }

	public static void incrementTime() {
		timeMinutes+=30;
		if(timeMinutes>=60) {
			if (timeHours<24) {
				timeHours=timeHours+1;
				timeMinutes=0;
			}
			else {
				timeHours=1;
				timeMinutes=0;
			}
		}
	}
	
	public static void generateBids(ArrayList<Item> items, ArrayList<Customer> customers, Queue<QueuedBid> queuedBids) {
		// this method will generate a random bid for each item from an eligible customer (customer w/o bid for that same item)
		for (int a=0;a<items.size();a++) {
			Item painting=items.get(a);
			ArrayList<Bid> bidsMade = new ArrayList<Bid>();
			Stack<Bid> cloneStack=painting.getBids().clone();
			while (!cloneStack.isEmpty()) {
				bidsMade.add(cloneStack.pop());
			}
			
			ArrayList<Customer> eligibleBidders=new ArrayList<Customer>();
			for (int i=0;i<customers.size();i++) {
				boolean canBid=true;
				for (int b=0;b<bidsMade.size();b++) {
					if (bidsMade.get(b).getCust().equals(customers.get(i))) {
						canBid=false;
						break;
					}
				}
				if (canBid) {
					eligibleBidders.add(customers.get(i));
				}
			}

			Random rand=new Random();
			
			Customer randomBidder=eligibleBidders.get(rand.nextInt(eligibleBidders.size()));
			
			boolean willBidUp=true;
			double maxBid;
			double bid;

			
			if (willBidUp) {
				bid=painting.getMinimumBid();
				if (bidsMade.size()>0) {
					bid=painting.getBids().peek().getBid() + painting.getIncrement();
				}
				maxBid=bid + ((rand.nextInt(20)+1)*painting.getIncrement()); 
				// formula for calculating max bid
			}
			else {
				bid=painting.getMinimumBid() - rand.nextInt((int) Math.ceil(painting.getMinimumBid()/painting.getIncrement()));
				maxBid=bid; // yeah
				// formula for making up some bid under the min bid so their bid can be tossed away
			}
			if (timeHours>8 & timeHours<17) { // check to make sure we're open
				processBid(painting,randomBidder,bid,maxBid); // send info to another method to handle what actually happens
				//it's another method because this way i can use it for processing backlogs too
			}
			else { //if we aren't open, add it to the processing queue for tomorrow 
				queuedBids.enqueue(new QueuedBid(painting,randomBidder,bid,maxBid));
			}
			
		}
	}
	
	public static void processBid(Item painting, Customer bidder, double bid, double maxBid) {
		ArrayList<Bid> bidsMade = new ArrayList<Bid>();
		Stack<Bid> cloneStack=painting.getBids().clone();
		while (!cloneStack.isEmpty()) {
			bidsMade.add(cloneStack.pop());
		}
		if (bid>=painting.getMinimumBid()) {
			//now let's make sure its more than current highest bidder
			if (bidsMade.size()>0) {
				if (maxBid>painting.getBids().peek().getMaxBid()) {
					bid(painting,bidder,painting.getBids().peek().getMaxBid(),maxBid); //finalize the bid
					System.out.println(bidder.getName() + " has successfully bid " 
					+ NumberFormat.getCurrencyInstance().format(bid) + " on " + painting.getName() 
					+ " with a maximum bid of " + NumberFormat.getCurrencyInstance().format(maxBid));
				}
				else {
					//otherwise we throw it out and change the highest bidders current bid to match (change as in add another bid)
					//whoever tried to bid's max bid
					System.out.println(bidder.getName() + " tried to bid " 
							+ NumberFormat.getCurrencyInstance().format(bid) + " on " + painting.getName() 
							+ " with a maximum bid of " + NumberFormat.getCurrencyInstance().format(maxBid) + 
							" but failed because a previous bidder's max bid was higher at " + NumberFormat.getCurrencyInstance().format(bidsMade.get(bidsMade.size()-1).getMaxBid() ));
					
					Bid prevBid=painting.getBids().peek();
					if (bid>prevBid.getBid()) {
						painting.addBid(new Bid(prevBid.getCust(),maxBid,prevBid.getMaxBid()));
					}
					//.setBid(maxBid);
				}
			}
			else {
				bid(painting,bidder,bid,maxBid); //finalize the bid
				System.out.println(bidder.getName() + " has successfully bid " 
				+ NumberFormat.getCurrencyInstance().format(bid) + " on " + painting.getName() 
				+ " with a maximum bid of " + NumberFormat.getCurrencyInstance().format(maxBid));
			}
		}
		else {
			System.out.println(bidder.getName() + " tried to bid " + NumberFormat.getCurrencyInstance().format(bid) +
			" on " + painting.getName() + " but the bid was thrown out because it was less than the minimum for the item.");
		}
	}
	
	public static void custMenu(Customer cust, ArrayList<Item> paint) {
		Scanner scan = new Scanner(System.in);

		int select = -1;
		while(select != 4)
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
		System.out.println("Input your first name:");
		String name = scan.nextLine();
		System.out.println("Input your last name:");
		name = name + " " + scan.nextLine();
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
			Bid bid = temp.pop();
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
      /*

			if (myBid >= minBid) {
				painting.addBid(new Bid(cust, myBid, 9999999));													
				System.out.println("Bid successful!");
			} else {
				System.out.println("You must bid at least "
						+ NumberFormat.getCurrencyInstance().format(minBid));

			*/
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

	public static void bid(Item paint, Customer cust, double minBid, double maxBid) 
	// method overload for automation
	{
		Bid bid = new Bid(cust,minBid,maxBid);
		//cust.addBid(bid);
		paint.addBid(bid);
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
			
			Stack<Bid> bids = paint.get(i).getBids().clone();
			
			while(bids.isEmpty() == false)
			{
				Bid bid = bids.pop();
				if(cust.getCustID() == bid.getCust().getCustID())
				{
					System.out.println(paint.get(i).getName());
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
