Admin Password: password

Sample Customer Username: jdoe	
Sample Customer Password: password

Currently the admin menu and new user sign up menus are not implemented, but the sample user can bid on existing item(s). We also have a rough idea of how we are going to implement the rest of the project features in the future.

To Use Program:
Load sample data using menu item #1
Log on as the sample customer "John Doe" with the username "jdoe" and password "password"
Choose menu item #3 to bid on an existing item


-Added more method stubs, fixed some OOP
-Added some admin functionality
-Added JUnit test
-Made sure to put readmes directly in the package

FOR FUTURE TIME SIMULATION:
We're thinking about maybe reading in all bids from a file.
Each entry can have a time value of some sort. We can have a separate thread
running in the background that automatically reads in all the data and 
automatically places bids for each customer. This eliminates you having to
manually input bids. Then, we can have the program automatically exit
at the end of the auction day.

