package pandas;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class JavaSales {

	public static void main(String[] args) {
		
		ArrayList<Item> paint = new ArrayList<>();
		ArrayList<Customer> clients = new ArrayList<>();
		
		String adminPassword="password";
		
		Customer testCustomer=new Customer("John Doe");
		testCustomer.setPassword("password");
		testCustomer.setMoney(99999);
		testCustomer.setUserName("jdoe");
		
		clients.add(testCustomer);
		
		while (true) {
			int selection=mainMenu();
			if (selection==1) {
				auctionSetup(paint);
			}
			else if (selection==2) {
				
			}
			else if (selection==3) {
				System.out.println("Please input the admin password.");
				Scanner scan = new Scanner(System.in);
				if (scan.nextLine().equals(adminPassword)) {
					int selection2=0;
					while(selection2!=6) {
						selection2=adminMenu();
					};
				}
				else {
					System.out.println("Invalid password.");
				}
				
			}
			else if (selection==4) {
				System.out.println("Please input your username.");
				Scanner scan = new Scanner(System.in);
				String userName=scan.nextLine();
				System.out.println("Please input your password.");
				String password=scan.nextLine();
				
				boolean foundMatch=false;
				Customer customer = null;
				
				for (int i=0;i<clients.size();i++) {
					if (clients.get(i).getUserName().equals(userName) &&
						clients.get(i).getPassword().equals(password)) {
						foundMatch=true;
						customer=clients.get(i);
						break;
					}
				}
				
				if(foundMatch) {
					int selection3=0;
					while (selection3!=5) {
						selection3=custMenu();
						if (selection3==3) {
							System.out.println("What item would you like to bid on?");
							for (int i=0;i<paint.size();i++) {
								System.out.println(
									(i+1) + ") " + paint.get(i).getName()
								);
							}
							int paintSelection=scan.nextInt();
							
							if (paintSelection>0 && paintSelection<paint.size()+1) {
								Item painting=paint.get(paintSelection-1);
								ArrayList<Bid> uniqueCustomerBids = new ArrayList<Bid>();
								
								for (int i=0;i<painting.getBids().size();i++) {
									if (painting.getBids().get(i).getCust().getCustID()==customer.getCustID()) {
										uniqueCustomerBids.add(painting.getBids().get(i));
									}
								}
								
								String bidText="No bid";
								
								if (uniqueCustomerBids.size()>0) {
									bidText="" + uniqueCustomerBids.get(uniqueCustomerBids.size()-1).getBid();
								}
	
								System.out.println("Bid Menu:");
		
								System.out.println("Your Current Bid: " + bidText);
								System.out.println("---------------------------------------------");
								System.out.println("Current highest bid on item:");
								
								double minBid=painting.getMinimumBid();
								if (painting.getBids().size()>0) {
									Bid highestBid=painting.getBids().get(painting.getBids().size()-1);
									System.out.println(highestBid.toString());
									
									System.out.println("---------------------------------------------");
									System.out.println("You must bid at least " + 
									NumberFormat.getCurrencyInstance().format(highestBid.getBid() + painting.getIncrement()));
									minBid=highestBid.getBid()+painting.getIncrement();
								}
								else {
									System.out.println("You must bid at least " + painting.getMinimumBid());
								}
								System.out.println("Would you like to make a bid on this item?");
								
								System.out.println("1) Make bid on item");
								System.out.println("2) Return to customer menu");
								
								int bidSelection=scan.nextInt();
								if (bidSelection==1) {
									System.out.println("Enter a legal bid value:");
									double myBid=scan.nextDouble();
									if (myBid>=minBid){
										painting.addBid(new Bid(customer,myBid,9999999)); // What is "maxBid" for again?
										System.out.println("Bid successful!");
									}
									else {
										System.out.println("You must bid at least " + 
										NumberFormat.getCurrencyInstance().format(minBid));
									}
								}
								
							}
						}
					}
				}
			}
		}
	}

	public static void auctionSetup(ArrayList<Item> p) {		
	
		//p.add(new Item("The Starry Night", 12000, 500));
		p.add(new Item("Mona Lisa", 18000, 1000));
		//p.add(new Item("American Gothic", 9000, 200));
		//p.add(new Item("The Storm on the Sea of Galilee", 6000, 100));
	//	p.add(new Item("The Last Supper", 50000, 5000));
		
	}
	
	public static int mainMenu() {
		Scanner scan = new Scanner(System.in);
		
		while(true) {
			System.out.println("Please enter the number that corresponds with the action you would like to perform");
			System.out.println("1: Load sample data");
			System.out.println("2: Process the backlogged data");
			System.out.println("3: Log in as administrator");
			System.out.println("4: Log in as customer");
			System.out.println("5: Exit the application");
			int select = -1;
			try{
				select = scan.nextInt();
			} catch (InputMismatchException ime) {
				System.out.println("You did not enter an integer value");
			}
			if(select == 1 || select == 2 || select == 3 || select == 4 || select == 5)
				return select;
			else
				System.out.println("You did not enter a valid selection");
		}
	}
	
	public static int custLMenu() {
		Scanner scan = new Scanner(System.in);
		
		while(true) {
			System.out.println("Please enter the number that corresponds with the action you would like to perform");
			System.out.println("1: Returning customer");
			System.out.println("2: New customer");
			System.out.println("3: Return to main menu");
			int select = -1;
			try {
				select = scan.nextInt();
			} catch (InputMismatchException ime) {
				System.out.println("You did not enter an integer value");
			}
			if(select == 1 || select == 2 || select == 3)
				return select;
			else
				System.out.println("You did not enter a valid selection");
		}		
	}
	
	public static int adminMenu() {
		Scanner scan = new Scanner(System.in);
		
		//while(true) {
			System.out.println("Please enter the number that corresponds with the action you would like to perform");
			System.out.println("1: List current ongoing auctions");
			System.out.println("2: Choose an ongoing auction and check the bidding history");
			System.out.println("3: List information about completed auctions");
			System.out.println("4: Summary data of winning bids");
			System.out.println("5: Add and activate a new auction");
			System.out.println("6: Return to main menu");
			int select = -1;
			try {
				select = scan.nextInt();
			} catch (InputMismatchException ime) {
				System.out.println("You did not enter an integer value");
			}
			if(select == 1 || select == 2 || select == 3 || select == 4 || select == 5 || select == 6)
				return select;
			else
				System.out.println("You did not enter a valid selection");
				return 0;
		//}
	}
	
	public static int custMenu() {
		Scanner scan = new Scanner(System.in);
		
		
			System.out.println("Please enter the number that corresponds with the action you would like to perform");
			System.out.println("1: Check my active bids");
			System.out.println("2: Check my winning bids");
			System.out.println("3: Bid on an item");
			System.out.println("4: Pay for an item that I won");
			System.out.println("5: Return to main menu (log out)");
			int select = -1;
			try {
				select = scan.nextInt();
			} catch (InputMismatchException ime) {
				System.out.println("You did not enter an integer value");
			}
			if(select == 1 || select == 2 || select == 3 || select == 4 || select == 5)
				return select;
			else
				System.out.println("You did not enter a valid selection");
				return 0;
		
	}
}
