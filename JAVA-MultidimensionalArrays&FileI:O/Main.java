package Tickets3; // packages are usually named in lowercase, but the project documentation says that the name should be "Tickets"
// NAME: AISHWARYA ADIKI
// NET-ID: AXA180100
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static DecimalFormat df2 = new DecimalFormat("#.00");
    public static void main(String[] args) {
        String line;
        int count_lines = 0; // to see how many rows the two dimensional array has
        int length = 0; // to check how many seats each row has
        String auditorium_spot;
        int i = 0; // the row number for the two dimensional array
        int position; // substringing

        Scanner input = new Scanner(System.in); // an inputstream Scanner Object that will be used to get user input
        int row_num = 0;
        char seat_letter = 'A';
        int adult_tickets = 0; // prompt the user for the number of adult tickets
        int child_tickets = 0; // prompt the user for the number of child tickets
        int senior_tickets = 0; // prompt the user for the number of senior tickets
        int exit_Button;

        // counting lines and seats;
        try {
            Scanner count = new Scanner(new File("src/Tickets3/A1.txt"));
            while (count.hasNextLine()) {
                line = count.nextLine();// store one line from the file into the string called line
                length = line.length(); // to get the number of columns for the 2D array
                count_lines++;          // to get the number of rows for the 2D array

            } // end while loop
            count.close(); // close the scanner file object

        }  // exception handling
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } // finish catch


        String [][] ticketReserve = new String [count_lines][length]; // two dimensional array
        String [][] finalReserve = new String [count_lines] [length]; // two dimensional array copy that will be written to the file

        int display_row = 1; // displaying the row numbers on the screen
        char display_seat = 0; // displaying the seat letters;

        // PARSE EACH ROW AND FILL BOTH THE TWO DIMENSIONAL ARRAYS -
        // ONE WILL BE USED FOR USER INTERFACE, AND THE OTHER WILL BE USED TO UPDATE THE AUDITORIUM ON THE FILE

        try {
            Scanner scanner = new Scanner(new File("src/Tickets3/A1.txt"));
            // reopen the file, but this time parse the file and fill the two 2D arrays with individual elements
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                position = 0; // Parsing the string called line into single elements
                for (int j = 0; j < line.length(); j++)
                {
                    auditorium_spot = line.substring(position, position + 1);
                    ticketReserve[i][j] = auditorium_spot; // ticketReserve will hold the file, but all letters are changed to #
                    finalReserve[i][j] = auditorium_spot; // this is an exact copy of file
                    if(auditorium_spot.indexOf('A')>=0) // If an A is encountered replace with a hash
                    {
                        ticketReserve[i][j] = "#";
                    }
                    else if(auditorium_spot.indexOf('C')>=0) // If a C is encountered replace with a hash
                    {
                        ticketReserve[i][j] = "#";
                    }
                    else if(auditorium_spot.indexOf('S')>=0) // If an S is encountered replace with a hash
                    {
                        ticketReserve[i][j] = "#";
                    }
                    position++; // increment the position so that the next letter/ dot can be parsed
                }
                i++;
            } // end while loop
            scanner.close(); // close the scanner file object

        }  // exception handling
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } // finish catch

        // DO-WHILE LOOP FOR THE MAIN MENU: Loop until the user decides to quit.
        // Imagine this is for a ticket kiosk in the lobby of the theater.
        do {
            System.out.println("Welcome to Hollywood Movie Grill\n"); // introducing the menu options
            System.out.println("Main Menu: ");
            System.out.println("Please enter 1 to reserve your tickets, and 2 to exit. \n" + "1. Reserve Seats \n" + "2. Exit");
            exit_Button = input.nextInt(); // main menu input

            if(exit_Button == 2) // if 2 then exit the main menu
            {

                System.out.println("Now exiting...");
                System.out.println("Thank you for using the ticketing system. ");
                break;
            }

            if (exit_Button == 1)
            {
                display_row = 1; // to display the row numbers besides each row of the 2D array
                System.out.println("Auditorium Seating Chart: "); // display the current Auditorium occupancy, so the user can make a choice
                System.out.printf("  ");
                for (int index = 0; index < length; index++) // printing seat letters on top of the 2D array, so the user can interact efficiently
                {
                    display_seat = convert_to_seatLetter(index); // converts each column index into its corresponding letter starting from 'A'
                    System.out.printf(String.valueOf(display_seat));
                }
                System.out.printf("\n");
                //print out the seating chart here:
                for (int pos = 0; pos < count_lines; pos++ )
                {
                    System.out.printf(String.valueOf(display_row) + " ");
                    for(int index = 0; index < length; index++)
                    {
                        System.out.printf(ticketReserve[pos][index]);
                    }
                    System.out.println();
                    display_row++;
                }
                System.out.println();
                // INPUT VALIDATION
                boolean gooddata = false;
                // START INPUT VALIDATION FOR THE ROW NUMBER
                System.out.println("Enter the Row Number: ");
                while (!gooddata)
                {
                    try {
                        row_num  = input.nextInt();
                        gooddata = true;
                    } catch ( InputMismatchException ex )
                    {
                        System.out.println("Please enter a valid row number");
                        String flush = input.next();
                    }
                }
                // START INPUT VALIDATION FOR THE SEAT LETTER
                System.out.println("Enter the Seat Letter: ");
                seat_letter = input.next().charAt(0);
                gooddata = false;
                String seat_let;
                while (!gooddata)
                {
                    seat_let = String.valueOf(seat_letter);
                    if(!seat_let.matches("[A-Z]")) {
                        System.out.println("Please enter a valid Capital Letter to represent your seat in the auditorium");
                        seat_letter = input.next().charAt(0);
                    } else{
                        gooddata = true;
                    }
                }
                // START INPUT VALIDATION FOR THE ADULT TICKETS NUMBER
                System.out.println("Enter the Number of adult tickets being purchased (enter 0 if none):");
                gooddata = false;
                while (!gooddata)
                {
                    try {
                        adult_tickets  = input.nextInt();
                        gooddata = true;
                    } catch ( InputMismatchException ex )
                    {
                        System.out.println("Please make sure to enter an integer number to represent your row number: ");
                        String flush = input.next();
                    }
                }
                // START INPUT VALIDATION FOR THE CHILD TICKETS NUMBER
                System.out.println("Enter the Number of child tickets being purchased (enter 0 if none):");
                gooddata = false;
                while (!gooddata)
                {
                    try {
                        child_tickets  = input.nextInt();
                        gooddata = true;
                    } catch ( InputMismatchException ex )
                    {
                        System.out.println("Please make sure to enter an integer number to represent your row number: ");
                        String flush = input.next();
                    }
                }
                // START INPUT VALIDATION FOR THE SENIOR TICKETS NUMBER
                System.out.println("Enter the Number of senior tickets being purchased (enter 0 if none):");
                gooddata = false;
                while (!gooddata)
                {
                    try {
                        senior_tickets  = input.nextInt();
                        gooddata = true;
                    } catch ( InputMismatchException ex )
                    {
                        System.out.println("Please make sure to enter an integer number to represent your row number: ");
                        String flush = input.next();
                    }
                }
                System.out.println("Now showing best possible ticket matches for " + adult_tickets + " adult tickets, " +
                        child_tickets + " child tickets, " + senior_tickets + " senior tickets, starting from Row: "
                        + row_num + " and Seat: " + seat_letter);

            }

            char prompt; // To store 'Y' or 'N' from user input
            display_row = 1;
            int number_of_Adult = 1; //Reserving adult tickets
            int number_of_Child = 1; //Reserving child tickets
            int number_of_Senior = 1; //Reserving senior tickets
            int total_tickets = (adult_tickets + child_tickets + senior_tickets);
            if(row_num >= 0)
            {
                int seat = convert_seatnum(seat_letter); // convert seat letter to an integer, where 'A' is 1, 'B' is 2, etc...
                int consequtive_seat = 0; // a counter for consequtive seats
                if (ticketReserve[row_num-1][seat] != "#") // if the seat the user wants is available, then
                {
                    // check if there are any consequtive seats available
                    for (int a = 0; a < total_tickets; a++)
                    {
                        if(ticketReserve[row_num-1][seat] != "#")
                        {
                            consequtive_seat = consequtive_seat + 1;
                            seat++;
                        }

                    }
                    //If consequtive seats counter is equal to the total number of seats being purchased:
                    seat = convert_seatnum(seat_letter);
                    if(consequtive_seat == total_tickets)
                    {
                        //then reserve the seats
                        Reserve_NextBestPossibleSeats(total_tickets,number_of_Adult,number_of_Child,number_of_Senior,adult_tickets,
                                child_tickets,senior_tickets,seat,row_num,ticketReserve,finalReserve);
                        System.out.println("Desired seats are available, now reserving tickets: ");
                        Display_Auditorium(length,count_lines,ticketReserve); // display the auditorium with the user's selected seats
                        Customer_Transaction(adult_tickets,child_tickets,senior_tickets,total_tickets); // provide transaction details
                        System.out.println("Thank you for purchasing tickets at the Hollywood Movie Grill.");
                        System.out.println("Enjoy Disney’s Jungle Cruise movie premiere");
                        System.out.print("Now going back to the main menu");
                        NoSeatsAvailable(); // Let them know you are taking them back to the main menu
                    }
                    else{ //If consequtive seats counter is not equal to the total number of seats being purchased:
                        System.out.println("There are no sequential seats starting from " + seat_letter +
                                "\nSearching for best possible seats near the center on the same row:");
                        int center_of_selection = (int) Math.ceil(((ticketReserve[row_num].length)/2) - seat); // establish center of selection
                        int center = Find_NextBestPossibleSeats(total_tickets,seat,row_num, center_of_selection, ticketReserve);
                        //center will hold the seat number closest to the center of the row from which the tickets can be purchased
                        int nextBest_consequtive_seat = 0;
                        int consequtive = CheckForConsequtiveSeats(center, total_tickets, row_num,
                                nextBest_consequtive_seat, ticketReserve); // a counter for consequtive seats
                        if(consequtive == total_tickets) //If consequtive seats counter is equal to the total number of seats being purchased:
                        {
                            // then prompt the user to see if they would like to purchase the seats?
                            System.out.println("Would you like to reserve the next best possible seats near the center of the row? (Enter 'Y' for Yes or 'N' for No): ");
                            prompt = input.next().charAt(0); // get user letter input
                            if(prompt == 'Y') { // if the user enters 'Y' (yes)
                                //then reserve the next best possible seats near the center of the row
                                Reserve_NextBestPossibleSeats(total_tickets,number_of_Adult,number_of_Child,number_of_Senior,adult_tickets,
                                        child_tickets,senior_tickets,center,row_num,ticketReserve,finalReserve);
                                Display_Auditorium(length,count_lines,ticketReserve); // display the auditorium with user's selection
                                Customer_Transaction(adult_tickets,child_tickets,senior_tickets,total_tickets); // provide transaction details
                                System.out.println("Thank you for purchasing tickets at the Hollywood Movie Grill.");
                                System.out.println("Enjoy Disney’s Jungle Cruise movie premiere");
                                NoSeatsAvailable(); // Let the user know you are taking them back to the main menu
                            } else if(prompt == 'N') // if the user enters 'N' (no)
                            {
                                System.out.println("No alternate seats available that match your criteria," +
                                        " we apologize for the inconvenience.\n\n");

                            }
                        } else{ // if there are no seats near the center and no seats that match user's selection:
                            System.out.println("No seats available that match your criteria on the row or near the center of the row. " +
                                    "Please try another row. " +
                                    "We apologize for the inconvenience. \n\n");
                            System.out.printf("Now taking you back to the main menu" );
                            NoSeatsAvailable(); // Let the user know you are taking them back to the main menu
                        }
                    }


                }
                else{ // IF THE SEAT CHOSEN/SELECTED IS ALREADY OCCUPIED
                    System.out.println("Desired seats not available, now searching for the next best option near the center of the row: ");
                    int center_of_selection = (int) Math.ceil(((ticketReserve[row_num].length)/2) - seat); // establish center of selection
                    int center = Find_NextBestPossibleSeats(total_tickets,seat,row_num, center_of_selection, ticketReserve);
                    //center will hold the seat number closest to the center of the row from which the tickets can be purchased
                    int nextBest_consequtive_seat = 0;
                    int consequtive = CheckForConsequtiveSeats(center, total_tickets, row_num,
                            nextBest_consequtive_seat, ticketReserve);// a counter for consequtive seats
                    if(consequtive == total_tickets) //If consequtive seats counter is equal to the total number of seats being purchased:
                    {
                        // then prompt the user to see if they would like to purchase the seats?
                        System.out.println("Would you like to reserve the next best possible seats? (Enter 'Y' for Yes or 'N' for No): ");
                        prompt = input.next().charAt(0); // get user letter input
                        if(prompt == 'Y') { // if the user enters 'Y' (yes)
                            //then reserve the next best possible seats near the center of the row
                            Reserve_NextBestPossibleSeats(total_tickets,number_of_Adult,number_of_Child,number_of_Senior,adult_tickets,
                                    child_tickets,senior_tickets,center,row_num,ticketReserve,finalReserve);
                            Display_Auditorium(length,count_lines,ticketReserve); // display the Auditorium with user's selection
                            Customer_Transaction(adult_tickets,child_tickets,senior_tickets,total_tickets); // provide transaction
                            System.out.println("Thank you for purchasing tickets at the Hollywood Movie Grill.");
                            System.out.println("Enjoy Disney’s Jungle Cruise movie premiere");
                            NoSeatsAvailable(); // Let the user know that you will taking them back to the main menu

                        } else if(prompt == 'N') { // if the user enters 'N' (no)
                            System.out.println("No alternate seats available that match your criteria," +
                                    " we apologize for the inconvenience.\n\n");
                        }
                    }
                    else { // if there are no seats near the center and no seats that match user's selection:
                        System.out.println("No seats available that match your criteria on the row or near the center of the row. " +
                                "Please try another row. " +
                                "We apologize for the inconvenience. \n\n");
                        System.out.printf("Now taking you back to the main menu" );
                        NoSeatsAvailable(); // Let the user know you are taking them back to the main menu
                    }
                }
            }

        } while (exit_Button == 1);
        print_Summary(count_lines,length,finalReserve); // call function to print summary since the program has ended
        try {
            WriteBackToFile(length,count_lines, finalReserve); // function call to Write the finalReserve array with letters back to the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // END MAIN

    public static int convert_seatnum(char s) //FUNCTION TO CONVERT A SEAT LETTER TO AN APPROPRIATE INDEX FOR THE 2D ARRAY
    {
        int pos = s - 'A'; // position is the difference of the two characters
        return  pos;
    } // convert_seatnum defined
    public static char convert_to_seatLetter(int i){ //FUNCTION TO CONVERT THE INDEX IN THE ARRAY TO A SEAT LETTER
        char seatLetter = (char) ('A' + i); // index i + the value of A gives the seat letter, if index is 0, then the user is at 'A'
        return seatLetter;
    } // convert_to_seatLetter defined
    public static int Find_NextBestPossibleSeats(int total_tickets, int seat, int row_num, int center_of_selection, String[][]ticketReserve)
    {
        if(total_tickets == 1 ) // if only one ticket is being purcahsed but the original seat is taken, then
        {
            center_of_selection = ((ticketReserve[row_num].length)/2) - 1; // directly place the user in the center of the auditorium
        }
        else{
            if(center_of_selection > 0 && center_of_selection <= 5) // else, if the distance from selected seat is > 0, but <5, then
            {
                center_of_selection = seat + (center_of_selection/2) + 1; // place here
            } else if(center_of_selection > 5) // if the distance is greater than 5 seats,
            {
                if(total_tickets >=center_of_selection) { //check if the total tickets being purchased is greater than the distance
                    center_of_selection = seat + (center_of_selection/2) ; // if so, then divide the distance by two and start placing from there
                }
                else if(total_tickets < center_of_selection) //if the total tickets being purchased is less than the distance
                {
                    if(seat == 0) // to consider the situation where the seat they chose is at index 0,
                    // but the seat still needs to counted aka if the user selects seat 'A'
                    {
                        center_of_selection = seat + (center_of_selection/2) + 1;
                    }else {
                        center_of_selection = seat + (center_of_selection/2) + 1;
                    }
                }
            } else if (center_of_selection < 0) // if the distance is negative because it's being measured from the right side,
            {
                center_of_selection = seat + (center_of_selection) - (total_tickets/2); //then, recenter to half the distance in the negative direction
            }
        }
        return Math.abs(center_of_selection); // return the new seating position near the center
    } // function Find_NextBestPossibleSeats defined
    public static int CheckForConsequtiveSeats(int center_of_selection, int total_tickets, int row_num,
                                               int nextBest_consequtive_seat, String[][]ticketReserve)
    {
        //FUNCTION TO CHECK IF THERE ARE CONSEQUTIVE SEATS NEAR THE CENTER
        int place_Holder = center_of_selection; // So as to not alter the center
        for (int a = 0; a < total_tickets; a++) {
            if(ticketReserve[row_num-1][place_Holder] != "#") { // if the seat is a '.', then increment the consequtive seat counter
                nextBest_consequtive_seat = nextBest_consequtive_seat + 1;
            }
            place_Holder++;
        }
        return nextBest_consequtive_seat; // return the value-> if consequtive seats == total tickets then we can reserve the seats
    } // function CheckForConsequtiveSeats defined
    public static void Display_Auditorium(int length, int count_lines,  String[][]ticketReserve)
    {
        int display_row = 1; // to display the row numbers besides each row of the 2D array
        char display_seat = 0; // displaying the seat letters on top of the 2D array

        System.out.println("\n\n Auditorium with your reserved seats displayed: ");
        System.out.printf("  ");
        // display the current Auditorium seating, so that the user can see the seats he/she has reserved
        for (int index = 0; index < length; index++)// printing seat letters on top of the 2D array, so the user can interact efficiently
        {
            display_seat = convert_to_seatLetter(index); // converts each column index into its corresponding letter starting from 'A'
            System.out.printf(String.valueOf(display_seat));
        }
        System.out.printf("\n");
        //Print the 2D array to the screen
        for (int pos = 0; pos < count_lines; pos++ ) {
            System.out.printf(String.valueOf(display_row) + " ");
            for(int index = 0; index < length; index++) {
                System.out.printf(ticketReserve[pos][index]);
            }
            System.out.println(); // print new line after each row
            display_row++; // increment the row number infront of each row
        }
        System.out.println();
    } // function Display_Auditorium defined
    public static void Reserve_NextBestPossibleSeats(int total_tickets, int number_of_Adult, int number_of_Child, int number_of_Senior,
                                                     int adult_tickets, int child_tickets,
                                                     int senior_tickets, int center_of_selection, int row_num,
                                                     String[][] ticketReserve, String[][] finalReserve)
    {
        //FUNCTION TO SEQUNTIALLY RESERVE EACH SEAT AS EITHER AN 'A','C', or 'S': adult, then child, then senior
        for(int k = 0; k < total_tickets; k++)
        {
            if(number_of_Adult <= adult_tickets) // if the numberofadult counter is less than or equal to the adult tickets being purchased
            {
                ticketReserve[row_num-1][center_of_selection] = "#";
                finalReserve[row_num-1][center_of_selection] = "A"; // keep reserving adult tickets in the finalReserve array
                number_of_Adult++; // increment the counter after each adult ticket is reserved
            }
            else if(number_of_Adult > adult_tickets) // once the adult counter exceeds the number of adult tickets being purchased
            {
                // then move onto the child tickets
                if(number_of_Child <= child_tickets){ // if the numberofchild counter is less than or equal to the child tickets being purchased
                    ticketReserve[row_num-1][center_of_selection] = "#";
                    finalReserve[row_num-1][center_of_selection] = "C"; // keep reserving child tickets in the finalReserve array
                    number_of_Child++; // increment the counter after each child ticket is reserved
                }
                else if(number_of_Child > child_tickets) // once the child counter exceeds the number of child tickets being purchased
                {
                    // then move onto the senior tickets
                    if(number_of_Senior <= senior_tickets){ // if the numberofsenior counter is less than or equal to the senior tickets being purchased
                        ticketReserve[row_num-1][center_of_selection] = "#";
                        finalReserve[row_num-1][center_of_selection] = "S"; // keep reserving senior tickets in the finalReserve array
                        number_of_Senior++; // increment the counter after each senior ticket is reserved
                    }
                }
            }
            center_of_selection++; //increment the seat position each time, so that the seats can be secured sequentially
        }
    } // function Reserve_NextBestPossibleSeats defined
    public static void Customer_Transaction(int adult_tickets, int child_tickets, int senior_tickets, int total_tickets)
    {
        // A FUNCTION TO PRINT THE DETAILS OF EACH TRANSACTION. IF THERE IS NO TRANSACTION THEN NOTHING WILL BE PRINTED
        System.out.println("Your Transaction details: \n");
        if(adult_tickets != 0) { // IF ADULT TICKETS ARE SOLD, CALCULATE THE ADULT TICKET TOTAL
            System.out.printf("Adult tickets total: $" + df2.format(10*adult_tickets) + "\n");
        }
        else { //ELSE SHOW $0 AS TRANSACTION TOTAL
            System.out.printf("Adult tickets total: $0.00\n");
        }
        if(child_tickets != 0) {// IF CHILD TICKETS ARE SOLD, CALCULATE THE CHILD TICKET TOTAL
            System.out.printf("Child tickets total: $" +  df2.format(5*child_tickets) + "\n");
        }
        else{//ELSE SHOW $0 AS TRANSACTION TOTAL
            System.out.printf("Child tickets total: $0.00\n");
        }
        if (senior_tickets!=0) {// IF SENIOR TICKETS ARE SOLD, CALCULATE THE SENIOR TICKET TOTAL
            System.out.printf("Senior tickets total: $" + df2.format(7.50 * senior_tickets) + "\n");
        }
        else{//ELSE SHOW $0 AS TRANSACTION TOTAL
            System.out.printf("Senior tickets total: $0.00\n");
        }
        if(total_tickets != 0) { // IF AT LEAST ONE TICKET WAS SOLD, CALCULATE THE TOTAL
            System.out.println("Transaction total: $"+
                    df2.format((7.50 * senior_tickets) + (5*child_tickets) + (10*adult_tickets)));
        } else{ //ELSE SHOW $0 AS TRANSACTION TOTAL
            System.out.println("Transaction total: $0.00\n");
        }
    } // function Customer_Transaction defined
    // FUNCTION THAT WRITES THE FINAL 2D ARRAY BACK TO THE ORIGINAL FILE
    public static void WriteBackToFile(int length, int count_lines, String[][] finalReserve) throws IOException {
        String str; // Will be writing back to the final element by element, essentially printing the array one by one onto the file
        String newline = "\n"; // new line character after a row of the 2D Array has been written to the file
        FileOutputStream outputStream = new FileOutputStream("src/Tickets3/A1.txt");
        //open output filestream which is the A1.txt file
        for (int pos = 0; pos < count_lines; pos++ ){ // nested for loop to print each element of the 2D array individually onto A1.txt
            for(int index = 0; index < length; index++) {
                str = finalReserve[pos][index]; // str will temporarily hold the accessed element
                byte[] strToBytes = str.getBytes(); // str will be converted to byes
                outputStream.write(strToBytes); // the bytes will be written to the file

            }
            byte[] newlineToBytes = newline.getBytes(); // print a new line after a whole row of seats has been written to the file
            outputStream.write(newlineToBytes);
        }
        outputStream.close(); // close the file
    } // function WriteBackToFile defined
    public static void NoSeatsAvailable() // A multipurpose function call When no seats are available,
    // or when the user has finished purchasing tickets
    {
        int timeToWait = 3; // time in seconds -> 3 seconds // three second delay before the the program loops back to the main menu
        try {
            for (int time=0; time<timeToWait ; time++) {
                Thread.sleep(1000);
                System.out.print(".");
            }
            System.out.printf("\n\n\n");
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    } //function NoSeatsAvailable defined
    // A FUNCTION TO PRINT THE ENTIRE SUMMARY OF AUDITORIUM AFTER BUTTON -2 IS SELECTED AND THE USER EXITS THE MAIN MENU
    // TO SIGNIFY THE END OF THE TRANSACTION
    public static void print_Summary(int count_lines, int length, String[][] finalReserve)
    {
        int adult_total = 0; // counter for total adult tickets reserved in the auditorium
        int child_total = 0; // total child tickets reserved in the auditorium
        int senior_total = 0; // total senior tickets reserved in the auditorium
        int total_tickets_sold; // the total number of tickets reserved in the auditorium
        String element; // to temporarily hold the value of each element in the finalReserve 2d array

        for (int pos = 0; pos < count_lines; pos++ ) {
            for(int index = 0; index < length; index++) {
                element = finalReserve[pos][index];
                if(element.indexOf('A')>=0){ // if an A is encountered, increment the adult_total counter
                    adult_total++;
                }
                else if(element.indexOf('C')>=0) // if a C is encountered, increment the child_total counter
                {
                    child_total++;
                }
                else if(element.indexOf('S')>=0) // if an S is encountered, increment the senior_total counter
                {
                    senior_total++;
                }
            }
        }
        System.out.println();
        total_tickets_sold = adult_total + child_total +  senior_total;
        float total_ticket_sales_amount = (float) ((10*adult_total) + (5*child_total) + (7.50*senior_total));// floating point variable
                                                                                                             // as requested in Project instructions
        System.out.printf("Sale summary of the Auditorium: \n");
        System.out.println("Number of seats in the auditorium: " + count_lines*(finalReserve[0].length)); // display number of seats in the auditorium
        System.out.println("Total tickets sold: " + total_tickets_sold); // display total number of tickets sold
        System.out.println("Adult tickets sold: " + adult_total); //display total number of adult tickets reserved
        System.out.println("Child tickets sold: " + child_total); // display total number of child tickets reserved
        System.out.println("Senior tickets sold: " + senior_total); // display total number of senior tickets reserved
        if(total_tickets_sold != 0) {
            System.out.println("Total Ticket Sales: $" + (df2.format(total_ticket_sales_amount)));
        } else{
            System.out.println("Total Ticket Sales: $0.00");
        }
        System.out.println();
        System.out.println("Thank you for using Hollywood Movie Grill Ticketing System ");
    }//function print_summary defined
} // end Main Class