package pandas;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class JavaSales {

	public static void main(String[] args) {
		
		ArrayList<Item> paint = new ArrayList<>();
		ArrayList<Customer> clients = new ArrayList<>();
		
		

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
		
		while(true) {
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
		}
	}
	
	public static int custMenu() {
		Scanner scan = new Scanner(System.in);
		
		while(true) {
			System.out.println("Please enter the number that corresponds with the action you would like to perform");
			System.out.println("1: Check my active bids");
			System.out.println("2: Check my winning bids");
			System.out.println("3: Bid on an item");
			System.out.println("4: Pay for an item that I won");
			System.out.println("5: Return to main menu");
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
		}
	}
}
