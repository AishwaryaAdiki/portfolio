package Tickets;



public abstract class  Node  {
    protected int row; //  an integer variable to represent the row
    protected char seat; // a character variable to represent the seat letter not to be confused with the ticket_type
    protected boolean reserved; // boolean variable that is set to true if the seat is reserved or false if it's not.
    protected char ticket_type; // a character variable to store if a seat has been reserved by a "A"/ "C"/ "S"/ "."

    //	Overloaded constructor (called by derived classes)
    public Node(int row, char seat, boolean reserved, char ticket_type) {
        this.row = row;
        this.seat = seat;
        this.reserved = reserved;
        this.ticket_type = ticket_type;
    }

    //default constructor
    public Node() { }

    //	Mutators - Also known as setters that will store/modify data into each node
    public void setRow(int row) {
        this.row = row;
    }
    public void setSeat(char seat) {
        this.seat = seat;
    }
    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
    public void setTicket_type(char ticket_type) {
        this.ticket_type = ticket_type;
    }

    //	Accessors - Also known as getters that will retrieve data into each node
    public int getRow() {
        return row;
    }
    public char getSeat() {
        return seat;
    }
    public boolean isReserved() {
        return reserved;
    }
    public char getTicket_type() {
        return ticket_type;
    }
}
