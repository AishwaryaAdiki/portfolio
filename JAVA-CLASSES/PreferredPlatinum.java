package DrinkRewards;
//NAME: AISHWARYA ADIKI
//NETID: AXA180100
import java.text.DecimalFormat;

public class PreferredPlatinum extends Customer { // inheritance from the parent class Customer
    private float BonusBucks; // Platinum customers get bonus bucks instead of percent discounts

    //Overloaded constructor:
    public PreferredPlatinum(String First_name, String Last_name, String GuestID, float AmountSpent, float BonusBucks) {
        super(First_name, Last_name, GuestID, AmountSpent);
        this.BonusBucks = BonusBucks;
    }

    //Mutator:
    public void setBonusBucks(float BonusBucks) {
        this.BonusBucks = BonusBucks;
    }

    //Accessor:
    public float getBonusBucks() {
        return this.BonusBucks;
    }

    //Other functions:
    @Override
    public String toString() { // converts the record into a string, so that it can be written to the file
        DecimalFormat f = new DecimalFormat("0.00");
        return ( getGuestID() + " " + getFirst_name() + " " + getLast_name() + " " +
                f.format(getAmountSpent()) + " " + f.format(getBonusBucks()));
    }
}
