package Tickets;
// NAME: AISHWARYA ADIKI

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main (String [] args) throws IOException {
        Scanner input = new Scanner(System.in);
        String Filename = "src/Tickets/A3base.txt"; // *******PLEASE EDIT THE FILE PATH HERE WHILE TESTING******

        int row_num = 0;
        char seat_letter;
        int adult_tickets = 0; // prompt the user for the number of adult tickets
        int child_tickets = 0; // prompt the user for the number of child tickets
        int senior_tickets = 0; // prompt the user for the number of senior tickets
        int total_tickets = 0; // to keep track of the total tickets being purchased.
        int exit_Button;
        Auditorium newAuditorium = new Auditorium(); //initialize an Auditorium object for the multidimensional linked list
        int loopcount = 0; // loop counter to decide if a new Auditorium is being made or if we are appending to the same one
        boolean gooddata; // for input validation
        boolean nextbestfound; // for checking the availabilty of next best seats
        do {
            System.out.println("Welcome to Hollywood Movie Grill\n"); // introducing the menu options
            System.out.println("Main Menu: "); // Display according to project documentation
            // START INPUT VALIDATION FOR menu option 1 or 2. Entering anything
            System.out.println("Please enter 1 to reserve your tickets, and 2 to exit. \n" + "1. Reserve Seats \n" + "2. Exit");
            gooddata = false; // set good data to false by default
            while (!gooddata) { // assuming the data is bad right of the bat,
                try {// try catch to capture invalid data
                    adult_tickets = input.nextInt(); // get input
                    if(adult_tickets == 1 || adult_tickets == 2) { gooddata = true; }
                    else{
                        System.out.println("Make sure to enter either 1 or 2"); // if it's the wrong input display an error message
                    }
                } catch (InputMismatchException ex) { // if it's not an integer, display an error message
                    System.out.println("Please make sure to enter an integer number to represent the appropriate menu option");
                    String flush = input.next();
                } // try again
            }
            exit_Button = adult_tickets;
            if(exit_Button == 2){ // if 2 then exit the main menu
                if(newAuditorium.getFirst() == null){ // if the user directly exists the main menu, the
                    System.out.println("No purchases were made...\n"); //print no purchases were made cuz the user didn't reserve any tickets
                    newAuditorium = newAuditorium.AddAuditorium(Filename); // add a auditorium to compute the sales summary regardless of reservation
                }
                System.out.println("Now exiting..."); // exiting prompt
                System.out.println("Thank you for using the ticketing system. "); // thank you message
                break; // break out of the while loop, thereby ending the main menu system.
            }
            if (exit_Button == 1) {
                if(loopcount == 0) {
                    // Display the Auditorium:
                    newAuditorium = newAuditorium.AddAuditorium(Filename);
                    newAuditorium.display(); // display the auditorium
                }
                else{ newAuditorium.display(); }
                loopcount++;

                // START INPUT VALIDATION FOR THE ROW NUMBER
                System.out.println("Enter the Row Number: ");
                row_num = InputValidation(input,gooddata,row_num); // validate integer input
                // START INPUT VALIDATION FOR THE SEAT LETTER
                System.out.println("Enter the Seat Letter: ");
                seat_letter = input.next().charAt(0);
                gooddata = false; // validate character input
                String seat_let;
                while (!gooddata) { // assuming that there is no good data,
                    seat_let = String.valueOf(seat_letter);
                    if (!seat_let.matches("[A-Z]")) { // check if a capital letter has not been entered for seat letter
                        System.out.println("Please enter a valid Capital Letter to represent your seat in the auditorium");
                        seat_letter = input.next().charAt(0);
                    } else {
                        gooddata = true; // if a capital letter has been entered then set good data to true and exit the while loop
                    }
                }
                // START INPUT VALIDATION FOR THE ADULT TICKETS NUMBER
                System.out.println("Enter the Number of adult tickets being purchased (enter 0 if none):");
                adult_tickets = InputValidation(input,gooddata,adult_tickets); // validate integer input
                // START INPUT VALIDATION FOR THE CHILD TICKETS NUMBER
                System.out.println("Enter the Number of child tickets being purchased (enter 0 if none):");
                child_tickets = InputValidation(input,gooddata,child_tickets); // validate integer input
                // START INPUT VALIDATION FOR THE SENIOR TICKETS NUMBER
                System.out.println("Enter the Number of senior tickets being purchased (enter 0 if none):");
                senior_tickets = InputValidation(input,gooddata,senior_tickets); // validate integer input
                System.out.println("Now showing best possible ticket matches for " + adult_tickets + " adult tickets, " +
                        child_tickets + " child tickets, " + senior_tickets + " senior tickets, starting from Row: "
                        + row_num + " and Seat: " + seat_letter); // show ticket quantity and starting point for reference

                total_tickets = adult_tickets + child_tickets + senior_tickets; // total tickets as a sum of adult, child, and senior tix
                // Reserving tickets:
                if(CheckConsequtiveAvailability(newAuditorium,row_num, seat_letter, total_tickets) == total_tickets) {
                    newAuditorium = ReserveSeats(newAuditorium, row_num, seat_letter, adult_tickets,child_tickets, senior_tickets); // if consequtive seats have been found on the first try reserve them
                    newAuditorium.display(); // show the reservation on the screen marked by a "#"
                } else{ // else if no consecutive seats for found on the first try, find the next best possible seats
                    System.out.println("Desired seats are not available, " +
                            "now searching for the next best possible seats close to the center of the auditorium: ");
                    nextbestfound = FindBestSeatsTopHalf(input,newAuditorium,total_tickets,adult_tickets,child_tickets,senior_tickets);
                    if(nextbestfound){ // check to see if there's best tickets available in th etop half of the auditorium
                        newAuditorium.display(); // if there are seats avaialble in the top hald of the auditorium, make reservation and display
                    } else if(!nextbestfound){ // however, if good seats haven't been found in teh top half of the auditorium, check the bottom half
                        nextbestfound = FindBestSeatsBottomHalf(input,newAuditorium,total_tickets,adult_tickets,child_tickets,senior_tickets);
                        if(nextbestfound) { newAuditorium.display(); } // if bottom half has good seats, make teh reservation and display on screen
                        else{ // if no good tickets have been found in the entire auditorium
                            System.out.println("\nIt seems there are no seats available in the entire auditorium that match your criteria." +
                                    " \nNow Taking you back to the main menu...\n\n\n"); // display a "sorry" message and loop back to the main menu.
                        }
                    }
                }
            }
        } while (exit_Button == 1); // keep looping the main menu until exit button 2 has been selected
        WriteBackToFile(newAuditorium, Filename); // call to the writebacktofile function to update the file with the new auditorium post-purchase
        PrintSummary(newAuditorium); // Print the auditorium summary
    }// END MAIN

    public static int InputValidation(Scanner input, boolean gooddata, int num) // INPUT VALIDATION FUNCTION
    {
        gooddata = false; // set good data to false by default
        while (!gooddata) { // assuming the data is bad right of the bat,
            try { // try catch to capture invalid data
                num = input.nextInt(); // get the next int
                if(num >= 0) // if and only if it's an integer value, set gooddata to be true;
                { gooddata = true; } else{ System.out.println("Make sure to enter a number >= 0"); }
            } catch (InputMismatchException ex) { // if the user enters anything other than an integer
                System.out.println("Please make sure to enter an integer number : "); // display the prompt
                String flush = input.next();  // and try again
            }
        }
        return num; // return the data
    }
    public static int CheckConsequtiveAvailability(Auditorium A, int row, char seat, int totaltickets) // check for consequtive avaiability of seats
    {
        int ConssequtiveSeatsAvailable = 0; // consequtive seats are 0 by default
        for (int i = 0; i < totaltickets; i++) { // loop = number of total tickets being purchased
            if(A.SearchIfReserved(row, seat) != null) { ConssequtiveSeatsAvailable++; } // if a seat is not reserved, increment the count
            seat++; // search the next consecutive seat
        }
        return ConssequtiveSeatsAvailable;
    }
    public static Auditorium ReserveSeats(Auditorium newAuditorium, int row, char seat, int adult_ticekts, int child_tickets, int senior_ticekts)
    {
        TheatreSeat currentRow = newAuditorium.SearchIfReserved(row, seat); //get the node that needs to be modified/reserved
        for(int i = 0; i < adult_ticekts; i++) { // a for loop to reserve a certain number of adult tickets
            currentRow.setRow(row); // set the row to row
            currentRow.setSeat(seat); // set the seat to the seat letter that was reserved
            currentRow.setReserved(true); // set reserved to true because the seat is now occupied
            currentRow.setTicket_type('A'); // set ticket type to 'A' to represent an adult ticket
            currentRow = currentRow.right; // move to the next node because seats are being reserved consecutively
            seat++; // increment the seat letter, so the next seat can be reserved on the same row.
        }
        for(int i = 0; i < child_tickets; i++) {
            currentRow.setRow(row); // set the row to row
            currentRow.setSeat(seat); // set the seat to the seat letter that was reserved
            currentRow.setReserved(true); // set reserved to true because the seat is now occupied
            currentRow.setTicket_type('C'); // set ticket type to 'C' to represent a child ticket
            currentRow = currentRow.right; // move to the next node because seats are being reserved consecutively
            seat++; // increment the seat letter, so the next seat can be reserved on the same row.
        }
        for(int i = 0; i < senior_ticekts; i++) {
            currentRow.setRow(row); // set the row to row
            currentRow.setSeat(seat); // set the seat to the seat letter that was reserved
            currentRow.setReserved(true); // set reserved to true because the seat is now occupied
            currentRow.setTicket_type('S'); // set ticket type to 'S' to represent a senior ticket
            currentRow = currentRow.right; // move to the next node because seats are being reserved consecutively
            seat++; // increment the seat letter, so the next seat can be reserved on the same row.
        }
        System.out.println("Your reservation has been made. Please check the updated auditorium down below;");
        return newAuditorium;
    }
    public static int NumberOfSeatsInARow(Auditorium A) { // Function to count the number of seats in a row
        TheatreSeat temp = A.getFirst(); // set the temp Node to the Head of the linked list
        int seatcount = 0; // variable to count the number of nodes in a row
        while(temp != null) { // while a node exists
            seatcount++; // increment the counter
            temp = temp.right; // go to the next node
        }
        return seatcount; // after the count is finished, return the number of seats in a row
    }
    public static int NumberOfSeatsInColumn(Auditorium A) { // Function to count how many rows there are in each Auditorium linked list
        TheatreSeat temp = A.getFirst();  // set the temp Node to the Head of the linked list
        int columncount = 0; // variable to count the number of rows
        while(temp != null) { // while a node exists below/down
            columncount++; // increment the counter
            temp = temp.down; // move down to the next node
        }
        return columncount;  // after the count is finished, return the number of rows in the Auditorium Linked list
    }
    public static boolean FindBestSeatsTopHalf(Scanner input, Auditorium A, int total_tickets,
                                               int adult_tickets, int child_tickets, int senior_tickets){
        int l1 = 0;  // starting point for finding the best row
        int r1= NumberOfSeatsInColumn(A); // number of rows in the auditorium
        int mid = l1 + (r1 - l1) / 2; // closest to the center of the auditorium
        int newRow = mid + 1;
        boolean nextbext; // to see if next best seats have been found
        TheatreSeat midHeadTop = A.getFirst(); // midHead Node points to the Head of the linked list
        int l2 = 0; // starting point for finding best seats on a given row
        int r2 = NumberOfSeatsInARow(A) - 1; // number of seats on a row
        while(midHeadTop.getRow() != newRow) { // set midHead to the center row of teh auditorium, thereby breaking the auditorium into 2
            midHeadTop = midHeadTop.down;
        }
        while(midHeadTop != null) { // now examine the top half of the auditorium
            nextbext = NextBestPossibleLeft(input,A,newRow,l2,r2,total_tickets, adult_tickets,child_tickets,senior_tickets);
            if(!nextbext){ // if best seats have not been found on the left half of the row, search on the right half of the row
                 nextbext = NextBestPossibleRight(input,A,newRow,l2,r2,total_tickets,adult_tickets,child_tickets,senior_tickets);
                 if(!nextbext) // if a row doesn't have any best seats,
                 {
                     midHeadTop = midHeadTop.up; // move up a row
                     newRow--; // decrement row count to go to the one above the current
                 }else{return true;} // else if a row has best seats on its right half, return true
            } else{return true;} // else if a row has best seats on its left half, return true
        }
        return false; // if no seats half been found in the top half of the auditorium, return false.
    }
    public static boolean FindBestSeatsBottomHalf(Scanner input, Auditorium A, int total_tickets, int adult_tickets, int child_tickets, int senior_tickets){
        int l1 = 0; // starting point for finding the best row
        int r1= NumberOfSeatsInColumn(A);  // number of rows in the auditorium
        int mid = l1 + (r1 - l1) / 2; // closest to the center of the auditorium
        int newRow = mid + 1;
        boolean nextbext; // to see if next best seats have been found
        TheatreSeat midHeadBottom = A.getFirst(); // midHead Node points to the Head of the linked list
        int l2 = 0; // starting point for finding best seats on a given row
        int r2 = NumberOfSeatsInARow(A) - 1; // number of seats on a row
        while(midHeadBottom.getRow() != newRow) { // set midHead to the center row of the auditorium, thereby breaking the auditorium into 2
            midHeadBottom = midHeadBottom.down;
        }
        while(midHeadBottom != null) {// now examine the bottom half of the auditorium
            nextbext = NextBestPossibleLeft(input,A,newRow,l2,r2,total_tickets, adult_tickets,child_tickets,senior_tickets);
            if(!nextbext){ // if best seats have not been found on the left half of the row, search on the right half of the row
                nextbext = NextBestPossibleRight(input,A,newRow,l2,r2,total_tickets,adult_tickets,child_tickets,senior_tickets);
                if(!nextbext) // if a row doesn't have any best seats,
                {
                    midHeadBottom = midHeadBottom.down; // move down a row
                    newRow++; // increment row count to go to the one below the current
                }
                else{return true;} // else if a row has best seats on its right half, return true
            } else{return true;} // else if a row has best seats on its left half, return true
        }
        return false; // if no seats half been found in the bottom half of the auditorium, return false.
    }
    public static boolean NextBestPossibleRight(Scanner input, Auditorium A, int row, int l, int r, int total_tickets,
                                        int adult_tickets, int child_tickets, int senior_tickets)
    {
        int mid = l + (r - l) / 2; // set the middle of the row, thereby breaking it into two halves
        char seat = convert_to_seatLetter(mid); // middle seat
        TheatreSeat midHeadRight = A.getFirst(); // midHead Node points to the Head of the linked list
        while(midHeadRight.getSeat()!=seat){ // set midHead to the center row of the auditorium, thereby breaking the row into 2
            midHeadRight = midHeadRight.right;
        }
        while(midHeadRight != null) { // now examine the right half of the row
            if(CheckConsequtiveAvailability(A,row,seat,total_tickets) == total_tickets) { // if consecutive seats have been found from mid
                char yes_or_no = 0;
                boolean gooddata = false;
                System.out.println("Next Best Seats are " + total_tickets + " tickets, starting from (" + midHeadRight.getRow() + ", "
                        + midHeadRight.getSeat() + "). " + " Would you like to reserve them (Enter 'Y' for Yes or 'N' for No): ");
                while (!gooddata) { // prompt the user and see if he/she wants to reserve the new tickets
                    yes_or_no = input.next().charAt(0);
                    if(yes_or_no == 'Y' || yes_or_no == 'N')
                    {
                        gooddata = true;
                    } else{
                        System.out.println("Please enter either a capital 'Y' or 'N' ");
                    }
                }
                if(yes_or_no == 'Y') { // if the user enters "Y" for yes,
                    A = ReserveSeats(A, row, seat, adult_tickets,child_tickets, senior_tickets); // then reserve the consecutive tickets
                } else if(yes_or_no == 'N') {  // if they don't want to reserve the next best seats, then display a "sorry" prompt:
                    System.out.println("Sorry For the Inconvenience. Taking you back to the main menu...");
                }
                return true; // since consecutive seats have been found from a certain seat, return true
            } else { // if consecutive seats have not been found:
                midHeadRight = midHeadRight.right; // move to the next node
                seat++; // which means move to the next seat on the right half of the auditorium, and check availability from that seat on.
            }
        }
        return false; // if no seats have been found on the right half of the row, return false
    }
    public static boolean NextBestPossibleLeft(Scanner input, Auditorium A, int row, int l, int r, int total_tickets,
                                             int adult_tickets, int child_tickets, int senior_tickets)
    {
        int mid = l + (r - l) / 2; // set the middle of the row, thereby breaking it into two halves
        char seat = convert_to_seatLetter(mid); // middle seat
        TheatreSeat midHeadLeft = A.getFirst(); // midHead Node points to the Head of the linked list
        while(midHeadLeft.getSeat()!=seat){ // set midHead to the center row of the auditorium, thereby breaking the row into 2
            midHeadLeft = midHeadLeft.right;
        }
        while(midHeadLeft != null) { // now examine the left half of the row
            if(CheckConsequtiveAvailability(A,row,seat,total_tickets) == total_tickets) { // if consecutive seats have been found from mid
                char yes_or_no = 0;
                boolean gooddata = false;
                System.out.println("Next Best Seats are " + total_tickets + " tickets, starting from (" + row + ", "
                        + midHeadLeft.getSeat() + "). " + " Would you like to reserve them (Enter 'Y' for Yes or 'N' for No): ");
                while (!gooddata) { // prompt the user and see if he/she wants to reserve the new tickets
                    yes_or_no = input.next().charAt(0);
                    if(yes_or_no == 'Y' || yes_or_no == 'N')
                    {
                        gooddata = true;
                    } else{
                        System.out.println("Please enter either a capital 'Y' or 'N' ");
                    }
                }
                if(yes_or_no == 'Y') { // if the user enters "Y" for yes,
                    A = ReserveSeats(A, row, seat, adult_tickets,child_tickets, senior_tickets); // then reserve consecutive seats
                } else if(yes_or_no == 'N') { // if they don't want to reserve the next best seats, then display a "sorry" prompt:
                    System.out.println("Sorry For the Inconvenience. Taking you back to the main menu...");
                }
                return true; // since consecutive seats have been found from a certain seat, return true
            } else { // if consecutive seats have not been found:
                midHeadLeft = midHeadLeft.left; // move to the previous node
                seat--; // which means move to the prev seat on the left half of the auditorium, and check availability from that seat on.
            }
        }
        return false; // if no seats have been found on the left half of the row, return false
    }
    public static char convert_to_seatLetter(int i) { //FUNCTION TO CONVERT THE INDEX IN THE ARRAY TO A SEAT LETTER
        return (char) ('A' + i); // index i + the value of A gives the seat letter, if index is 0, then the user is at 'A'
    } // convert_to_seatLetter defined
    public static void WriteBackToFile(Auditorium A, String Filename) throws IOException {
        String str; // Will be writing back to the final element by element, essentially printing the linked one by one onto the file
        String newline = "\n"; // new line character after a row of the linked list has been written to the file
        FileOutputStream outputStream = new FileOutputStream(Filename);
        TheatreSeat currentRow; // pointer to traverse the linked list horizontally
        TheatreSeat currentCol = A.getFirst(); // pointer to traverse the linked list vertically
        if(currentCol == null) { // if no auditorium exists,
            System.out.println("Auditorium is empty"); // prompt message
        } else { // if a linked list does exist
            while(currentCol != null) { // while there is a next row,
                currentRow = currentCol; // set row and col pointer equal to each other
                while(currentRow != null) { // while there is a next node on that row
                    str = String.valueOf(currentRow.getTicket_type()); // convert the ticket type to string
                    byte[] strToBytes = str.getBytes(); // str will be converted to bytes
                    outputStream.write(strToBytes); // the bytes will be written to the file
                    currentRow = currentRow.right; // move to the next node on the row
                } // end nested-while
                currentCol = currentCol.down; // after writing one row, move to the next one
                byte[] newlineToBytes = newline.getBytes(); // print a new line after a whole row of seats have been written to the file
                outputStream.write(newlineToBytes);
            }
        }
        outputStream.close(); // close the file
    } // function WriteBackToFile defined
    public static void PrintSummary(Auditorium A){ // Print Summary Function to call at the end to display auditorium statistics
        int adult_tickets = 0; // set the number of adult_tickets to 0
        int child_tickets = 0; // set the number of child_tickets to 0
        int senior_tickets = 0; // set the number of senior_tickets to 0
        int total_seats_in_auditorium = NumberOfSeatsInARow(A) * NumberOfSeatsInColumn(A); // the total seats in the auditorium
        TheatreSeat currentRow = A.getFirst(); // set the row traversal pointer to the head of the Linked list/Auditorium
        TheatreSeat currentCol = A.getFirst(); // set the column traversal pointer to the head of the Linked list/Auditorium
        if(currentCol != null) { // if the head of the auditorium exists, then the linked list exists
            while(currentCol != null) // while you can move vertically
            {
                currentRow = currentCol; // set the row and column pointer equalt to each other
                while(currentRow != null) // while you can traverse the row horizontally
                {
                    if(currentRow.getTicket_type() == 'A') { // if ticket type says adult, then increment adult ticket count
                        adult_tickets++;
                    } else if(currentRow.getTicket_type() == 'C') { // if ticket type says child, then increment child ticket count
                        child_tickets++;
                    } else if(currentRow.getTicket_type() == 'S') { // if ticket type says senior, then increment senior ticket count
                        senior_tickets++;
                    }
                    currentRow = currentRow.right; // move to the next node
                } // end nested-while
                currentCol = currentCol.down; // once the end of the row is reached, go to the next row
            } // end outer while loop
        }
        // Print the Sales Summary:
        System.out.println("Auditorium Summary\n");
        A.display(); // Formatted Report
        System.out.println("\n\nTotal Seats in the Auditorium: " + total_seats_in_auditorium); // Print the total seats in the auditorium
        System.out.println("Total Tickets Sold: " + (adult_tickets + child_tickets + senior_tickets)); // Print total num of tickets sold
        System.out.println("Adult Tickets Sold: " + adult_tickets); // Print the number of adult ticekts sold
        System.out.println("Child Tickets Sold: " + child_tickets); // Print the number of child ticekts sold
        System.out.println("Senior Tickets Sold: " + senior_tickets); // Print the number of senior ticekts sold
        System.out.println("Total Tickets Sales: $" + ((adult_tickets *10.00) + (child_tickets*5.00) + (senior_tickets*7.50)) + "0"); // Print total sales
    }
}
