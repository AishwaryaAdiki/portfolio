package DrinkRewards;
//NAME: AISHWARYA ADIKI

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        // Parse the customer.dat file:
        String inputLine; // a string to hold a line read from the file as input
        String First_name; // to hold the first name of a customer
        String Last_name ; // to hold the last name of a customer
        String GuestID ; // to hold the GuestID of a customer
        float AmountSpent; // to hold the amount spent by a customer
        int NumberOfEntries = 0; // counting the number of customers in the file and resizing the array to fit one more record

        Customer[] RegularList = new Customer[0]; // creating a customer array to hold the regular customers
        Object[] PreferredList = new Object[0]; // an object type array that will hold both PreferredGold and PreferredPlatinum customers

        // Creating a Regular Customer  Array
        try {
            Scanner count = new Scanner(new File("src/DrinkRewards/customer.dat")); // open the customers.dat file
            while (count.hasNextLine()) { // while there is a line to read,
                inputLine = count.nextLine();// store one line from the file into the string called inputline
                String[] array = inputLine.split(" "); // split the line using spaces to store as separate fields
                RegularList = ExpandCusByOne(RegularList); // expand the regularlist array by one to hold a new record
                GuestID = array[0]; // the first "split" element is the GuestID
                First_name = array[1]; //  second "split" element is the Firstname
                Last_name = array[2]; //  third "split" element is the Lastname
                AmountSpent = Float.parseFloat(array[3]); // fourth split element is the amount spent by the customer previously
                RegularList[NumberOfEntries] = new Customer(First_name, Last_name, GuestID, AmountSpent); // Create a new record
                NumberOfEntries++; // increase the index for the regular customer array
            }// end while loop
            count.close(); // close the scanner file object
        }  // exception handling
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } //  Finished Creating a Regular Customer  Array
        // Creating a Preferred Customer Object Array that can hold gold or platinum customers
        NumberOfEntries = 0; // resetting the array index back to zero, so the PreferredList array can be created
        float bonus; // a float variable that holds both "Bonus Bucks" and Percent Discount depending on the class instance created
        try {
            Scanner file = new Scanner(new File("src/DrinkRewards/preferred.dat"));
            while (file.hasNextLine()) {
                inputLine = file.nextLine();// store one line from the file into the string called inputline
                String[] array = inputLine.split(" ");// split the line using spaces to store as separate fields
                GuestID = array[0]; // the first "split" element is the GuestID
                First_name = array[1];  //  second "split" element is the Firstname
                Last_name = array[2]; //  third "split" element is the Lastname
                AmountSpent = Float.parseFloat(array[3]); // fourth split element is the amount spent by the customer previously
                bonus = Float.parseFloat(array[4]); // fifth split element can either be bonus bucks or discount percent of the customer
                PreferredList = ExpandPrefByOne(PreferredList); // expand the PreferredList array by one to hold a new record
                if (AmountSpent < (float) 200.0) { //if the amount already spent is less tha $200, then make that person a PreferredGold
                    PreferredList[NumberOfEntries] = new PreferredGold(First_name, Last_name, GuestID, AmountSpent, bonus);
                    NumberOfEntries++; // create a new record for PreferredGold and increase the index
                } else if (AmountSpent >= (float) 200.0) { //if the amount already spent is less tha $200, then make that person a PreferredPlat
                    PreferredList[NumberOfEntries] = new PreferredPlatinum(First_name, Last_name, GuestID, AmountSpent, bonus);
                    NumberOfEntries++; // create a new record for PreferredPlatinum and increase the PreferredList index by one
                }
            } // end while loop
            file.close(); // close the scanner file object
        }  // exception handling
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } //  Finished Creating a Preferred Customer Object Array

        String read_guestID; // to hold the guestID of the customer that has placed the order
        char size; // size of the drink
        String drink_type; // can be either soda, tea, or punch
        int quantity; // the number of drinks
        float total ; // to hold the total amount spent including the previous purchases
        Customer match; // to hold the record if a GUESTID match is found in  the RegularList
        Object prefermatch; // to hold the record if a GUESTID match is found in  the PreferredList

        // Reading Orders from orders.dat:
        try {
            Scanner newFile = new Scanner(new File("src/DrinkRewards/orders.dat"));
            while (newFile.hasNextLine()) {
                inputLine = newFile.nextLine();// store one line from the file into the string called inputline
                String [] array = inputLine.split(" "); // split the line using spaces to store as separate fields
                read_guestID = array[0]; // the first "split" element is the GuestID
                size = array[1].charAt(0); //  second "split" element is the size of drink
                drink_type = array[2]; //  third "split" element is the drink type being ordered
                quantity = Integer.parseInt(array[3]); //  fourth "split" element is the quantity of drinks being purchased

                match = SearchForMatchInRegularList(read_guestID, RegularList); // search for a match, and if found, store the record

                if (match != null) // only if a match has been found in the RegularList
                {
                    assert drink_type != null;
                    total = amountSpent(match, size, drink_type, quantity); // calculate the total amount the customer had spent to date
                    if (total > 50) { // if the total exceeds 50,
                        RegularList = ShrinkCusByOne(RegularList, read_guestID); // then shrink Regular list by one so customer can be promoted
                    }
                    PreferredList = ApplyDiscount_and_PromoteToPrefer(match, PreferredList, total); // function will onlt be called if total exceeds 50;
                }else{
                    prefermatch = SearchForPlatinumMatchInPreferredList(read_guestID, PreferredList); // if no match in RegularList, search the PreferredList

                    if (prefermatch != null) { // only if a match has been found
                        assert drink_type != null;
                        total = amountSpent((Customer) prefermatch, size, drink_type, quantity); // calculate the total amount
                        PreferredList = ShrinkPrefByOne(read_guestID, PreferredList); // Shrink Preferred by the record that has been modified.
                        // It will be added back in once appropriate bonus bucks/discounts have been applied
                        PreferredList = ApplyDiscount_and_PromoteToPrefer((Customer) prefermatch, PreferredList, total);
                    }
                }
            } // end while loop
            newFile.close(); // close the scanner file object
        } // exception handling
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } //  Finished Creating an Regular Customer Object Array
        WriteBackToFile(PreferredList,RegularList);
    } // end main

    // Expand-The-Cutomer-Array-By-One Function:
    public static Customer[] ExpandCusByOne(Customer[] RegularList) { // expanding RegularList array by one

        Customer[] c1 = new Customer[RegularList.length + 1]; // create a new array that can hold one more element than the original one
        System.arraycopy(RegularList, 0, c1, 0, c1.length - 1); // copy the orginal into the new
        return c1; // return the new array, which will now become the new original
    }
    public static Customer[] ShrinkCusByOne(Customer[] RegularList, String read_guestID) { //  shrinking RegularList by one
        Customer[] c1 = new Customer[RegularList.length - 1]; // Create an array that holds one less element than the original one
        for (int i = 0, k = 0; i < RegularList.length; i++) {
            if (RegularList[i].getGuestID().equals(read_guestID)) { // if the record you want to delete from the RegularList has been found
                continue; // skip that record, i.e. don't copy that record into the new array, thus eliminating it
            } c1[k++] = RegularList[i]; // copy the rest over
        }
        return c1; // return the new, shrunken array
    }
    public static Object[] ExpandPrefByOne(Object[] PreferredList) {
        Object[] l2 = new Object[PreferredList.length + 1]; // create an array that can hold one more element than the PreferredList array
        System.arraycopy(PreferredList, 0, l2, 0, l2.length - 1); // copy the orginal array into the new one
        return l2; // return the new array, which is now the new orginal
    }

    // Search the Regular List for a ID match to take the order:
    public static Customer SearchForMatchInRegularList(String guestID, Customer[] RegularList) {
        for (DrinkRewards.Customer customer : RegularList) { // for each customer in the regular list
            String temp = customer.getGuestID(); // get the guestID
            if (temp.equals(guestID)) { // and check if the GuestID matches the one in orders.dat
                return customer; // if a match is found, return the whole record
            }
        }
        return null; // if no match is found return null
    }

    // Search the Preferred List for a ID match to take the order:
    public static Object SearchForPlatinumMatchInPreferredList(String read_guestID, Object[] PreferredList) {
        for (Object o : PreferredList) { // for each record in the PreferredList array
            String buffer = o.toString(); // convert the record to string
            int found = buffer.indexOf(read_guestID); // if the GuestId of the record matches the one in orders.dat
            if (found >= 0) { return o; } // return the record
        }
        return null; // if no match is found return null
    }
    public static Object[] ShrinkPrefByOne(String read_guestID, Object[] PreferredList) { // function to shrink PreferredList by one
        Object[] Pref = new Object[PreferredList.length - 1]; // Create an array that holds one less element than the original one
        for (int i = 0, k = 0; i < PreferredList.length; i++) {
            String buffer = PreferredList[i].toString(); // convert the record to string
            int found = buffer.indexOf(read_guestID); // if a match for the GuestID has been found
            if (found >= 0) {
                continue; // skip the record, thereby emilinating it
            }
            Pref[k++] = PreferredList[i]; // copy the rest of the array over
        }
        return Pref; // return the new shrunken array
    }
    //Promoting a Regular Customer to a preferred one and
    //Applying customer discount:
    public static Object[] ApplyDiscount_and_PromoteToPrefer(Customer match, Object[] PreferredList, float total) {

        if (total >= 50.0 && total < 100.0) { // if the total amount spent exceeds 50 but is less than 100
            total = total * (float) 0.95; // then apply the 5% disocunt
            PreferredList = ExpandPrefByOne(PreferredList); // and expand the PreferredList by one
            PreferredList[PreferredList.length - 1] = new PreferredGold(match.getFirst_name(), match.getLast_name(), match.getGuestID(),
                    total, (float) 5.00); // make a new gold record

        } else if (total >= 100.0 && total < 150.0) { // if the total amount spent exceeds 100 but is less than 150
            total = total * (float) 0.90; // then apply the 10% disocunt
            PreferredList = ExpandPrefByOne(PreferredList); // and expand the PreferredList by one
            PreferredList[PreferredList.length - 1] = new PreferredGold(match.getFirst_name(), match.getLast_name(), match.getGuestID(),
                    total, (float) 10.00); // make a new gold record

        } else if (total >= 150.0 && total < 200.0) { // do the same for the 150-199 range by applying the 15% discount and promoting to gold
            total = total * (float) 0.85;
            PreferredList = ExpandPrefByOne(PreferredList);
            PreferredList[PreferredList.length - 1] = new PreferredGold(match.getFirst_name(), match.getLast_name(), match.getGuestID(),
                    total, (float) 15.00); // make a new gold record

        } else if (match.getAmountSpent() > 200 || total > 200) { // if the total or the previously spent money exceeds 200
            int bonus_bucks_applied = (int) (match.getAmountSpent() - 200) / 5; //  bonus bucks the customer has already earned
            total = total - bonus_bucks_applied; // apply the bonus bucks to the total
            int bonus_bucks_earned = (int) (total - match.getAmountSpent())/5; // the bonus bucks earned for this particular transaction
            PreferredList = ExpandPrefByOne(PreferredList); // Expand pref by one
            PreferredList[PreferredList.length - 1] = new PreferredPlatinum(match.getFirst_name(), match.getLast_name(), match.getGuestID(),
                    total, (float) bonus_bucks_earned); // make a new platinum record
        } else if (total < 50) {
            match.setAmountSpent(total); // if the total is less than 50, then just make the total the new total amount spent
        }
        return PreferredList; // return the Preferred List array since this function only does promotions and discounts
    }
    // Calculate the amount spent for each transaction:
    public static float amountSpent(Customer match, char size, String drink_type, int quantity) {
        float SodaPrice = (float) 0.20; // soda price as given in the project details
        float TeaPrice = (float) 0.12; // tea price per ounce as given
        float PunchPrice = (float) 0.15; // punch price per ounce as given
        float drinkTotal; // the total cost for one drink
        if (drink_type.equals("soda")) {
            drinkTotal = calcDrinkTotal(size, SodaPrice); // calc price based on the size of the drink times 0.20, which is price per ounce of Soda
        } else if (drink_type.equals("tea")) {
            drinkTotal = calcDrinkTotal(size, TeaPrice); // calc price based on the size of the drink times 0.12, which is price per ounce of Tea
        } else if (drink_type.equals("punch")) {
            drinkTotal = calcDrinkTotal(size, PunchPrice); // calc price based on the size of the drink times 0.15, which is price per ounce of punch
        }
        else{return match.getAmountSpent();}
        float total = (match.getAmountSpent() + (drinkTotal) * quantity); // the total is the existing amoutn spent + the current transaction
        return total; // return the new running total
    }
    // Calculate the amount spent on each drink, depending on the size.
    public static float calcDrinkTotal(char size, float Priceperounce)  // price per ounce is a hold variable, the actual
    // price per ounce will be passed in when the function will be called
    {
        float drinkTotal = 0;
        if (size == 'S') {
            drinkTotal = Priceperounce * 12; // a small drink is 12 ounces
        } else if (size == 'M') {
            drinkTotal = Priceperounce * 20; // a medium drink is 20 ounces
        } else if (size == 'L') {
            drinkTotal = Priceperounce * 32; // a large drink is 32 ounces
        }
        return drinkTotal;
    }
    public static void WriteBackToFile(Object [] Preferredlist, Customer [] Regularlist) throws IOException {
        String str; // Will be writing back to the file element by element, essentially printing the array one by one onto the file
        String newline = "\n"; // new line character after a row of the 2D Array has been written to the file
        FileOutputStream outputStream_reg = new FileOutputStream("src/DrinkRewards/customer.dat"); // open outputfile stream
        FileOutputStream outputStream_pref = new FileOutputStream("src/DrinkRewards/preferred.dat");//open second outputfilestream
        for (int pos = 0; pos < Regularlist.length; pos++ ){ // nested for loop to print each element of the RegularList
            if(pos ==  (Regularlist.length-1)) // if it's the last element don't print a new line character
            {
                str = Regularlist[pos].toString(); // str will temporarily hold the accessed element
                byte[] strToBytes = str.getBytes(); // str will be converted to byes
                outputStream_reg.write(strToBytes); // the bytes will be written to the file
            }
            else{
                str = Regularlist[pos].toString(); // str will temporarily hold the accessed element
                byte[] strToBytes = str.getBytes(); // str will be converted to byes
                outputStream_reg.write(strToBytes); // the bytes will be written to the file

                byte[] newlineToBytes = newline.getBytes(); // print a new line after a record has been written to the file
                outputStream_reg.write(newlineToBytes);
            }
        }
        for (int pos = 0; pos < Preferredlist.length; pos++ ){ // nested for loop to print each element of the PreferredList
            if(pos == (Preferredlist.length-1)) // if it's the last element don't print a new line character
            {
                str = Preferredlist[pos].toString(); // str will temporarily hold the accessed element
                byte[] strToBytes = str.getBytes(); // str will be converted to byes
                outputStream_pref.write(strToBytes); // the bytes will be written to the file
            }else{
                str = Preferredlist[pos].toString(); // str will temporarily hold the accessed element
                byte[] strToBytes = str.getBytes(); // str will be converted to byes
                outputStream_pref.write(strToBytes); // the bytes will be written to the file

                byte[] newlineToBytes = newline.getBytes(); // print a new line after a Preferred customer record has been written to the file
                outputStream_pref.write(newlineToBytes);
            }
        }
        outputStream_reg.close(); // close the filestream
        outputStream_pref.close(); // close  filestream
    } // function WriteBackToFile defined
}
