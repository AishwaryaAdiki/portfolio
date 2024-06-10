// NAME: AISHWARYA ADIKI

#ifndef __node_h__
#define __node_h__

#include <iostream>

struct Operand_t { // a structure defining the abstarct enumerated data type of type_t
    enum type_t { CHAR, DOUBLE }; //
    union { // union as suggested in the project documentation
        char char_data; // an operator
        double double_data; // an operand
    };  // end union
    type_t which; // an enumerated data type variable which doesn't care if its a char or a double
    Operand_t( const char CC ) : char_data( CC ), which( type_t::CHAR ) {}; // an overloaded constructor for an operand_t which is a char
    Operand_t( const double DD = 0.0 ) : double_data( DD ), which( type_t::DOUBLE ) {}; // an overloaded constructor for an operand_t which is a double
    Operand_t( const Operand_t& OO ) : double_data( OO.double_data ),which(OO.which) {};
    
    operator char() const { return char_data; };
    operator double() const { return double_data; };
    
    void operator=( const char CC ) { char_data = CC; which = type_t::CHAR; }; // overloaded operator = to assign the data type char
    void operator=( const double DD ) { double_data = DD; which = type_t::DOUBLE; }; // overloaded operator = to assign the data type double
};  // end Operand_t

std::ostream& operator<<( std::ostream&, const Operand_t& ); // overloaded operator for the output stream to add a new node to the linked list

class Node{ // Node class
    
private:
    
    Operand_t operand; // a priavte structure variable that contains the union of the charcater and the double data type
    Node *next; // a next pointer of the Node  Class to point to the next item in the linked list
    
    
public:
    
    char id;
    
    void d() {
        static char id_ = 'A';
        id = ++id_;
        e( "Create: " );
    };
    void e( const char* label ) {
        std::cout << label
        << id << " : "
        << int( id ) - 65
        << " : " << operand
        << " : " << ( isdouble() ? "DBL" : "CHR" )
        <<  std::endl;
    };
    
    void dump( const char* );
    
    Node() : operand(), next( nullptr ), id( 0x0 ) { }; // Default constructor
    template< typename TT > Node( TT AA) : operand( AA ), next( nullptr ), id( 0x0 ) { } // could replace that with two constructors: one that accepts a character and one that accpets a double
    Node( const Node& NN ) : operand( NN.operand ), next( NN.next ), id ( NN.id ) { } // copy constructor
    ~Node() {};
    
    Node *Next() { return next; }; // Inline function
    void Next( Node* NN ){ next = NN; };
    
    Node *getNext(){return next;}; //the getNext() function -> accessor
    void setNext(Node* NN){next = NN;};
    
    //Accessors: alos known as getter functions
    char getOperand() const { return operand.char_data; }; // getOperand() function that returns an operand
    double asdouble() const { return operand.double_data; };
    double getOperand(int) const { return operand.double_data; }; // getOperand() function that return sa double
    
    //Mutators:
    void setOperator(char CC){operand = CC;}

    bool isdouble(){ return ( operand.which == Operand_t::type_t::DOUBLE ); }
    
    void setOperand(char CC) {operand = CC;}//template< typename TT > void setOperand( TT AA ) { operand = AA; } // could replace with a void operand char and another called void operand double
    //The stack and the node don't care what you will be storing in the stack or the node. All input is treated identically.
    void serOperand(double DD) {operand = DD;}//template< typename TT > void setOperator( TT AA ) { operand = AA; } // could replace with a void operand char and another called void operand double
    //The stack and the node don't care what you will be storing in the stack or the node. All input is treated identically.
    friend std::ostream& operator<<( std::ostream&, const Node& ); // overloaded operator to push a node onto the stack
    
protected:
    
    bool check(char); // a boolean function to check for a character
    
    
};

std::ostream& operator<<( std::ostream&, const Node& ); // overloaded operator to push a node onto the stack- redclared outside the class

#endif
