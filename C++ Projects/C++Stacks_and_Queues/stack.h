// NAME: AISHWARYA ADIKI
#ifndef __stack_hpp__
#define __stack_hpp__

#include "node.h"
#include <iostream>

class Stack
{
private:
    Node Head; // A node object because a stack is an abstract concept that makes use of a linked list
public:
    Stack(); // default constructor
    Stack(Node *); // overloaded constructor
    Stack(Stack&); // copy constructor
    
    void push(Node&); //push function -> created for homework 7
    void print(); // print function -> created during homework 7
    
    Node *getHead() {return &Head;}
    void setHead(Node *){}; 
    
    char peek(); //a function that is used to find an operator
    bool empty() {return Head.Next() == nullptr;} // if the stack is empty, returns nullptr
    void clear(); // clears the stack
    
    
    void output();
    //overloaded << operator for output
    friend std::ostream& operator<<(std::ostream&, const Stack&); // friend provides access to private and protected members of the class.
    
    friend Stack& operator>>(Stack&, Node*&);     //overloaded >> operator-> created during homework 8-> pushing ont the stack
    
    //overloaded << operator (not for output)
    friend Stack& operator<<(Stack&, Node*);  //overloaded << operator-> created during homework 8-> popping off the stack
    
    ~Stack(); // destructor
    
protected:
    void remove_node(Node *); // called in the destructor to remove a node.
    void print_node(Node *); // used in the print function
    void push_node(Node *); // used in the push function that technically adds a new node to the existing linked list
    //    void modify_node(Node *);
};

// overloaded operators redeclared outside the class.
std::ostream& operator<<(std::ostream&, const Stack&);
Stack& operator>>(Stack&, Node*&);
Stack& operator<<(Stack&, Node*);

#endif /* Stack_hpp */
