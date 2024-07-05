package DrinkRewards;
//NAME: AISHWARYA ADIKI

import java.text.DecimalFormat;

public class Customer {
    // declare private variables:

    // every record regular, gold, or platinum has the following information associated with it
    private String First_name;  // Name of teh Customer
    private String Last_name;
    private String GuestID; // the ID
    private float AmountSpent; // lastly a running total of the amount spent


    //Overloaded constructor:
    public Customer(String First_name, String Last_name, String GuestID, float AmountSpent){
        this.First_name = First_name;
        this.Last_name = Last_name;
        this.GuestID = GuestID;
        this.AmountSpent = AmountSpent;
    }

    // Mutators: also known as setter functions

    public void setFirst_name(String First_name) { this.First_name = First_name; }
    public void setLast_name(String Last_name) { this.Last_name = Last_name; }
    public void setGuestID(String GuestID) { this.GuestID = GuestID; }
    public void setAmountSpent(float AmountSpent) { this.AmountSpent = AmountSpent; }

    // Accessors:

    public String getFirst_name() { return this.First_name; }
    public String getLast_name() { return this.Last_name; }
    public String getGuestID() { return this.GuestID; }
    public float getAmountSpent() { return this.AmountSpent; }

    //Other functions:

    @Override
    public String toString() { // converts the record into a string, so that it can be written to the file
        DecimalFormat f = new DecimalFormat("##.00");
        return (getGuestID() + " " + getFirst_name() + " " + getLast_name() + " " +
                f.format(getAmountSpent()));
    }

}



