// NAME: AISHWARYA ADIKI
//

#include "stack.h"
#include <iostream>

using namespace std;


// all the functions in the 
Stack::Stack():Head(){} // default constructor
//-----------------------------------------------------

Stack::Stack(Node * NN):Head(){
    Head.Next(NN);
} // overloaded constructor
//-----------------------------------------------------

Stack::Stack(Stack &SS):Head()
{
    push_node(SS.Head.Next());
} // copy constructor

//-----------------------------------------------------
void Stack::push_node(Node *NN) // add a new node->which happens when trying to push to the stack
{
    if(NN->Next()!=nullptr)
    {
        push_node(NN->Next());
        
    }
    Node *last = nullptr;
    last = new Node();
    last->setOperand( NN->getOperand() );
    last->Next(NN->Next());
    push(*last);
}

void Stack::push(Node &NN) // just the push command that is used in the previous push_node function
{
    NN.Next(Head.Next());
    Head.Next(&NN);
}

//-----------------------------------------------------

void Stack::print() // a print function that was created for homework 7 that prints the node in question
{
    print_node(Head.Next()); // call the function below.
}

void Stack::print_node(Node *NN) // a nested function-> good programming practice
{
    if(NN->Next() != nullptr) // as long as the next pointer points to a node, print
    {
        print_node(NN->Next());
    }
    cout << *NN << " "; // formatting as required by the project documentation
}

//-----------------------------------------------------
void Stack::output() // created previously but not used in this project
{
    
    Node* NN = const_cast<Node*>(&Head)->Next();
    while(NN != nullptr)
    {
        
        cout << *NN;
        if(NN->Next()!=nullptr)
        {
            cout << " ";
        }
        NN = NN->Next();
    }
}
//-----------------------------------------------------

char Stack::peek() // the peek function that is called in main that finds an "operator"
{
    if(Head.Next() != nullptr)
    {
        return Head.Next()->getOperand();
    }
    return 0x0;
}

//-----------------------------------------------------


void Stack::remove_node(Node *NN) // the protected remove node function in the Stack class
{
    if(NN->Next() != nullptr)
    {
        remove_node(NN->Next());
    }
    delete NN;
}


Stack::~Stack() // Stack destructor
{
    if(Head.Next()!=nullptr) // as long as head next doesn't point to a nullptr,
    {
        remove_node(Head.Next()); // remove that "next' node
    }
} // destructor


void Stack::clear() // a stack clear function that clears the stack each time an expression is convereted from infix to postifx
{
    if(Head.Next()!=nullptr)
    {
        remove_node(Head.Next());
    }
    Head.Next(nullptr);
}
//-----------------------------------------------------

ostream& operator<<(std::ostream& out, const Stack& SS) // overloaded output operator for the Stack class
{
    Node* NN = const_cast<Node*>(&SS.Head)->Next();
    while(NN != nullptr)
    {
    
        out << *NN;
        if(NN->Next()!=nullptr)
        {
            cout << ", ";
        }
        NN = NN->Next();
    }
    return out;
}

//-----------------------------------------------------

Stack& operator>>(Stack& SS, Node*& NN) // overloaded   >> operator for the Stack class as required by the project documentation
{
    Node* temp = SS.Head.Next();
    if(temp != nullptr)
    {
        NN = temp;
        SS.Head.Next(NN->Next());
    }
    return SS;
    
}

//-----------------------------------------------------

Stack& operator<<(Stack& SS, Node* NN) // overloaded   >> operator for the Stack class as required by the project documentation
{
    NN->Next(SS.Head.Next());
    SS.Head.Next(NN);
    return SS;
}
