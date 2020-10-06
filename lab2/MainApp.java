package com.ibm.fb.lab2;

interface Repository {
    void findAll();
    void findById();
    void save();
    void update();
    void remove();
}

class Customer {
	String id;
	String firstName;
	String lastName;
	String city;
	
	public Customer(String iden, String fName, String lName, String cty) {		
		id= iden;
		firstName = fName;
		lastName = lName;
		city = cty;		
	}
}

public class MainApp {
	
	
	
	

}
