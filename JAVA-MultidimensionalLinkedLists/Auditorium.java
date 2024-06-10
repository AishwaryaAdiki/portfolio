package Tickets;
// NAME: AISHWARYA ADIKI
// NET ID: AXA180100
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Auditorium {
    protected TheatreSeat First; // Head of the linked list pointing to the first seat in the auditorium
    protected int skipCount; // an integer variable to determine the correct position to add a Node

    public TheatreSeat getFirst() {
        return First;
    } // Accessor to access the first node of the multi-dimensional linked-list

    public void setFirst(TheatreSeat first) {
        First = first;
    } // Mutator to set the first Node of the linked list

    public Auditorium AddAuditorium(String Filename) // Constructing the auditorium, which may throw an exception
    {
        Scanner inputFile = null; // open the file corresponding to the auditroium of choice
        try {
            inputFile = new Scanner(new File(Filename));
        } catch (FileNotFoundException e) {
            System.out.println("File doesn't exist");
            e.printStackTrace();
        }
        int Row = 1; // The display row will be set to 1 by default
        char Seat; // the seat is set to 0 or in this case 'A' by default
        char TicketType; // TicketType variable to store the parsed ticket type (either 'A', 'C', 'S', or '.'(IF THE SEAT IS UNRESERVED))

        String inputLine; // file will be read line by line and then parsed instead of reading it character by character
        Auditorium newAuditoriumList = new Auditorium(); // invoke auditorium constructor
        while (inputFile.hasNextLine()) { // as long as the file has a line to read
            inputLine = inputFile.nextLine(); // read the next line
            int position = 0; // Parsing the string called line into single elements
            int seat_count = 0; // initialize the first seat to 0 to represent 'A'
            for (int i = 0; i < inputLine.length(); i++) { // for loop that loops till the end of a whole row
                TicketType = inputLine.charAt(position); // pprase the character at the position and store into TicketType
                Seat = convert_to_seatLetter(seat_count); // Seat Letter or the Column starting from 'A' to as many seats in the row
                if (TicketType == 'A' || TicketType == 'S' || TicketType == 'C') { // if the seat is occupied by one of the following, then
                    newAuditoriumList.addNode(Row, Seat, true, TicketType, inputLine.length()); // set reserved to true
                } else { // if the seat hasn't been reserved, and is represented by a '.'  then,
                    newAuditoriumList.addNode(Row, Seat, false, TicketType, inputLine.length()); // else set reserved to false
                } // end if else
                position++; // increment the position
                seat_count++; // increment the seat count
            }
            Row++; // row is incremented by one
        }
        inputFile.close(); // close file after u finish reading from it
        return newAuditoriumList; // return the fully constructed Linked List  which is called Auditorium
    }
    public void addNode(int Row, char Seat, boolean Reserved, char TicketType, int RowLength) { // addNode FUNCTION
        TheatreSeat newNode = new TheatreSeat(Row, Seat, Reserved, TicketType); // create a new Node with given parameters
        if(First == null) // if no auditorium/linked-list exists;
        {
            First = newNode; // create the First Node (seat 1, A) ---> this will be the reference point for the rest of the auditorium
        }
        else{ // if the first Node already exists,
            //traverse the list until the last node is reached:
            TheatreSeat currentNodeRow = First; // a temporary head to travel left to right
            if(Row == 1) // if the row is equal to 1, then just traverse that row
            {
                while(currentNodeRow.right != null ) { // move ahead until currentNode's right pointer is not null
                    currentNodeRow = currentNodeRow.right;
                    skipCount++; // increment the skipcount counter to keep track of how many nodes have been traversed so far
                }
                // insert the new node:
                if(skipCount < RowLength) { // if skipcount is less than the number of seats on that row,
                    currentNodeRow.right = newNode; // then add a NewNode
                    newNode.left = currentNodeRow; // interlink the previous Node and the NewNode
                    skipCount = 0; //set skip count to 0
                }
            } else{ // if it's not the first Row,
                TheatreSeat currentNodeCol = First; // for travelling up and down the multi-dimensional linked-list
                while(currentNodeCol.down != null) { currentNodeCol = currentNodeCol.down; } // end while loop
                TheatreSeat traverseRow = currentNodeCol; // traverseRow will always be one below the currentNodeCol pointer so Nodes can be inter-linked vertically
                currentNodeRow = currentNodeCol.up;
                if(Seat == 'A'){ // if it's the first seat (A)
                    currentNodeCol.down = newNode; // create a newNode below the row above
                    newNode.up = currentNodeCol; // interlink top and bottom nodes
                } else{ // else if it's not the first seat (B-the end of the row).
                    while(traverseRow.right != null ) // traverse the row until you can find an opening to insert the new node
                    {
                        traverseRow = traverseRow.right;
                        currentNodeRow = currentNodeRow.right;
                        skipCount++; // increment the skip count pointer

                    } // end while loop
                    if(skipCount < RowLength) // once you find a opening, and the number of skips is less than the max capacity of the row
                    {
                        currentNodeRow = currentNodeRow.right;
                        traverseRow.right = newNode; // add a newNode, and interlink top,left,and right and bottom
                        newNode.left = traverseRow;
                        currentNodeRow.down = newNode;
                        newNode.up = currentNodeRow;
                        skipCount = 0; // reset skip count to 0
                    }
                } // finish the third if-else evaluation
            } // finish the second if-else evaluation
        } // Finish the 1st if-else evaluation

    }
    public Auditorium() { // deafult auditrium constructor
        First = null;
        skipCount = 0;
    }
    public TheatreSeat SearchIfReserved(int Row, char Seat) // Search if a singular seat is reserved.
    {
        TheatreSeat currentRow = First; // set the traversal row pointer to the Head of the linked list
        TheatreSeat currentCol = First; // set the traversal column pointer to the Head of the linked list
        if(currentCol != null) { // if the column or the row pointer is not null, that means the auditorium exists
            while(currentCol != null) { // while you can traverse vertically,
                currentRow = currentCol; // set row and col equal to each other
                while(currentRow != null) { // while you can traverse horizontally
                    if(currentRow.getRow() == Row && currentRow.getSeat() == Seat) // if the Node's seat is equal to the seat being searched
                    {
                        if(currentRow.getTicket_type() == '.') { return currentRow; } // if the seat is free, then return the entire node
                        else{ return null; } // else if the seat is taken, return null;

                    }else{ currentRow = currentRow.right; } // traverse right is row, seat match has not been found to test the next node
                } // end nested-while
                currentCol = currentCol.down; // after checking one row, move to next if a match hasn't been found
            } // end outer while loop
        } // finish if statement evaluation
        return null; // return null by default, is the row and seat being searched doesn't exist in the auditorium
    }
    public void display() { //  FUNCTION TO DISPLAY THE AUDITORIUM
        TheatreSeat tempRow = First; // set the row traversal pointer to point to the head of the linked list
        TheatreSeat tempCol = First; // set the column traversal pointer to point to the head of the linked list
        if(tempCol != null) // if the linked list's head exists, that means the linked list exists
        {
            System.out.printf("  "); // Print a double space for formatting the auditorium appropriately for user interaction
            while(tempRow != null) // Printing the 'ABCDEFGHIJK..." column/seat label above each seating column
            {
                System.out.print(tempRow.getSeat() + " "); // Print the seat letter followed by a space
                tempRow = tempRow.right; // move to the next node
            } // end nested-while
            System.out.printf("\n");
            tempRow = First; // reset the row traversal pointer back to the head of the linked list
            while(tempCol != null) // while you can traverse vertically
            {
                System.out.print(tempCol.getRow() + " "); //print the row number, followed by a space
                tempRow = tempCol; // set the column and row traversal pointers equal to each other
                while(tempRow != null) // while you can travel horizontally
                {
                    PrintInfo(tempRow); // Call the PrintInfo function to display the seat as either "#" or "."
                    tempRow = tempRow.right; // move to the next node
                } // end nested-while
                tempCol = tempCol.down; // once you reach the end of a row, move to the next one
                System.out.println(); // Print a new line at the end of each row
            } // end while loop
        }
    } // display Function defined
    public void PrintInfo(TheatreSeat temp) { // Print Infor function to print any reserved seat as a "#" instead of the ticket_type
        if(temp.isReserved()) { System.out.print("#" + " "); // if a ticket is reserved print a "#"
        } else{ System.out.print(temp.getTicket_type() + " "); } // if it's not, then print "."
    } // PrintInfo defined
    public static char convert_to_seatLetter(int i) { //FUNCTION TO CONVERT THE INDEX IN THE ARRAY TO A SEAT LETTER
        return (char) ('A' + i); // index i + the value of A gives the seat letter, if index is 0, then the user is at 'A'
    } // convert_to_seatLetter defined
}