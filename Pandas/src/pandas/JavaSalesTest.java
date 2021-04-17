package pandas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JavaSalesTest {

	private JavaSales javaSales;
	
	@BeforeEach
	void setUp() throws Exception {
		javaSales = new JavaSales();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testBid() { //Make sure that if they bid there is a bid
		ArrayList<Item> paint = new ArrayList<Item>();
		Item painting=new Item("A Great Painting",50,100);
		
		paint.add(painting);
		Customer cust = new Customer("Joe Bastianich",9285920,"jbas123","password");

		javaSales.bid(paint, cust);
		
		assertEquals(1,painting.getBids().size(),"There should be one bid for the item");
	}
	
	@Test
	void testNewAuction() {
		String name="An Amazing Painting";
		double minBid=5.0;
		double increment=10.0;
		Item newItem=javaSales.newAuction(name, minBid, increment);
		assertEquals(name,newItem.getName(),"Ensure the names match");
		assertEquals(minBid,newItem.getMinimumBid(),"Ensure the minimum bids match");
		assertEquals(increment,newItem.getIncrement(),"Ensure the increments match");
	}
	
	@Test //see if it breaks on an empty array
	void testSelectAuction() {
		ArrayList<Item> paint = new ArrayList<Item>();
		Item item=javaSales.selectAuction(paint);
		assertNull(item);
		
		
	}

}
