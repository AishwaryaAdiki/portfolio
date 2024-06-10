package DrinkRewards;

import java.text.DecimalFormat;

public class PreferredGold extends Customer {
    private float DiscountPercent; // Preferred Gold customers get some percent discount

    //Overloaded constructor:
    public PreferredGold(String First_name, String Last_name, String GuestID, float AmountSpent, float DiscountPercent) {
        super(First_name, Last_name, GuestID, AmountSpent);
        this.DiscountPercent = DiscountPercent;
    }

    //Mutator:
    public void setDiscountPercent(float DiscountPercent)
    {
        this.DiscountPercent = DiscountPercent;
    }

    //Accessor:
    public float getDiscountPercent()
    {
        return this.DiscountPercent;
    }

    //Other functions:
    @Override
    public String toString() { // converts the record into a string, so that it can be written to the file
        DecimalFormat f = new DecimalFormat("0.00");
        return ( getGuestID() + " " + getFirst_name() + " " + getLast_name() + " " +
                f.format(getAmountSpent()) + " " + getDiscountPercent());
    }
}
