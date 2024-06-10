package Tickets;



public class TheatreSeat extends Node {
    // Each node will have four pointers: up, down, left, right
    TheatreSeat up; // UP POINTER OF A THEATRESEAT NODE
    TheatreSeat down; //  UP POINTER OF A THEATRESEAT NODE
    TheatreSeat left; //  UP POINTER OF A THEATRESEAT NODE
    TheatreSeat right; //  UP POINTER OF A THEATRESEAT NODE
    //overloaded constructor
    public TheatreSeat(int row, char seat, boolean reserved, char ticketType) {
        super(row, seat, reserved, ticketType); // super from abstract Node class
        //initiate every single pointer to null to create a singular, complete node.
        up = null;
        down = null;
        left = null;
        right = null;
    }
}
