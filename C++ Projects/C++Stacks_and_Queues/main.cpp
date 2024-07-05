// NAME: AISHWARYA ADIKI
//  main.cpp
//  Homework 7
//  Created by Aishwarya Adiki on 11/6/19.
//  Copyright © 2019 Aishwarya Adiki. All rights reserved.

#include <iostream>
#include "node.h"
#include "stack.h"
#include <fstream>
#include <limits>
#include <string>
#include <sstream>
#include <cmath>
#include <iomanip>

#define OBRACE '(' // opening paranthesis
#define CBRACE ')' // closing paran
#define CARAT  '^' // highest precedence
#define DIVIDE '/' // middle precedence, equal to *
#define MlTPLY '*' // middle precedence, equal to /
#define ADD    '+' // lowest precedence, equal to -
#define SUB    '-' // lowest precedence, equal to +

using namespace std;

Stack pstack; // will hold the operators
Stack pexp; // postfix expression


ifstream openFile() // function for opening the file from input
{
    string filename; // string to hold input filename
    cin >> filename; // input full filename

    return ifstream(filename,ios_base::in); // return statement opens the file
}


static const string OPS = "^/*+-()"; //  various valid operators

bool isoperator(unsigned char &op) //
{
    return OPS.find(op) != string::npos; //return true if you find a character that is one of the allowed operators
}

void findbrace() // right brace was found-> function to repeatedly pop the stack until a left brace is encountered
{
    Node* NN; // Node pointer pointing to the head of the linked list
    pstack >> NN; // the right parantheses was found, hence pop the stack
    while(NN->getOperand() != OBRACE) // while loop that keeps popping off the stack until a left or opening brace is encountered
    {
        pexp << NN; //append the popped off "operator" to the postfix expression
        pstack >> NN; // pop another off the stack (this is the left parenthesis aka the opning parenthesis
    }
    delete NN; // delete the node-> avoiding memory leak
    
}

void divide() // divide test case to push to the stack
{
    Node* NN;
    while(pstack.peek() == CARAT || pstack.peek() == MlTPLY || pstack.peek() == DIVIDE) // if the divide symbol encounters a ^,*,/, it will
    {
        pstack >> NN; //pop off the last operator from the operator stack
        pexp << NN; // and then it will append the operator that was popped off to the postfix expression
    }
    pstack << new Node(DIVIDE); //then, it will push itself onto the stack
    
}

void multiply() // multiply case-> when a "*" symbol is found in the infix expression
{
    Node* NN;
    while(pstack.peek() == DIVIDE || pstack.peek() == MlTPLY || pstack.peek() == CARAT) // if the multiply symbol encounters a ^,*,/, it will
    {
        pstack >> NN;// pop off the last operator from the operator stack
        pexp << NN;  // and then it will append the operator that was popped off to the postfix expression
        
    }
    pstack << new Node(MlTPLY); //then, it will push itself onto the stack
    
}

void add() // addition case-> when a "+" symbol is found in the infix expression
{
    Node* NN;
    char peek = pstack.peek(); //peek holds the function peek which finds one of the seven valid operators (+/^*-())
    while(peek == CARAT || peek == DIVIDE || peek == MlTPLY || peek == SUB || peek == ADD) // if the add symbol encounters a ^,*,/,-.+ it will
    {
        pstack >> NN; // pop off the last operator from the operator stack
        pexp << NN;  // and then it will append the operator that was popped off to the postfix expression
        peek = pstack.peek();
    }
    pstack << new Node(ADD); //then, it will push itself onto the stack
    
}
void subtract() // subtraction case-> when a "-" symbol is found in the infix expression
{
    Node* NN;
    char peek = pstack.peek(); //peek holds the function peek which finds one of the seven valid operators (+/^*-())
    while(peek == CARAT || peek == DIVIDE || peek == MlTPLY || peek == SUB || peek == ADD)// if the subtract symbol encounters a ^,*,/,-.+ it will
    {
        pstack >> NN; // pop off the last operator from the operator stack
        pexp << NN;   // and then it will append the operator that was popped off to the postfix expression
        peek = pstack.peek();
    }
    pstack << new Node(SUB); //then, it will push itself onto the stack
    
}

void anoperator(char op) // a function that finds an operator in the infix expression and calls the right function
{
    switch (op)
    {
        case OBRACE: pstack << new Node(OBRACE); break; // if it's a left brace, it gets pushed onto the stack always
        case CBRACE: findbrace(); break; // if you find a right brace, call the finbrace() function that repeatedly pops the stack
        //highest precedence:
        case CARAT:  pstack << new Node(CARAT); break; // if you find a carat, always appened it to the operators stack
        
        //mid-tier precedence:
        case DIVIDE: divide(); break; // if you find a divide symbol, call the divide function which will then decide what to do
        case MlTPLY: multiply(); break; // if you find a * symbol, call the multiply function which will then decide what to do
        
        //lowest precedence:
        case ADD:    add(); break; // if you find a add symbol, call the add function which will then decide what to do
        case SUB:    subtract(); break; // if you find a subtract symbol, call the subtract function which will then decide what to do
            
    }
    
}

void parseline(string& line) // a regular parseline function
{
    istringstream sin(line); // reads a line from the text file and stores it in the string variable called line
    unsigned char peek;
    char op;
    double number = 0.0;
    
    pstack << new Node(OBRACE); // the stack if empty always starts off with an opening brace
    
    while (!sin.eof())
    {
        peek = static_cast<unsigned char>(sin.peek());
        if( isdigit(peek) || peek == '.' ) // if you find an operand
        {
            sin >> number;
            pexp << new Node(number); // push the operand onto the postfix expression
        }
        else if(isoperator(peek)) // else if you find an operand call the anoperator function to go through the possible operator cases
        {
            sin >> op;
            anoperator(op);
        }
        else
        {
            sin >> peek;
        }
    }
    anoperator(CBRACE); // call the anoperator function on the closing brace to essentially empty pstack (the Stack holding the
    
}


double Number( Stack& eval )
{
    Node* nn;
    double num;
    eval >> nn;  // get node
    num = nn->asdouble();
    delete nn;
    return num;
}


double operation(char op, Stack &eval) // a function to perform the pemdas operation
{
    double result = 0.0; // the final evaluated result
    double first = Number( eval ); // the first operand that is popped off the stack
    double second = Number( eval ); // the second operand that is popped of the stack
    
    switch(op) // swicth statements to choose the case
    {
        case CARAT:  result = pow( second, first ); break;  // power
        case DIVIDE: result = second / first; break; //
        case MlTPLY: result = first * second; break; //
        case ADD:    result = first + second; break;
        case SUB:    result = second - first; break;
    }
    
    //result = roundthree( result );
    return result;
    
}


void reverse(Stack &SS)
{
    Node *NN;
    while (!pexp.empty())
    {
        pexp >> NN;
        SS << NN;
    }
}


void evaluate()
{
    Stack eval;
    Stack rev; // reverse the stack
    Node *NN; // to hold the values that will be popped off the stack
    double number;
    
    reverse(rev);
    rev.output();
    
    while( ! rev.empty() ) {
        rev >> NN;
        
        if ( NN->isdouble() ) {
            number = NN->getOperand();
            eval << NN;  // push number back onto the eval stack
        } else {
            number = operation( NN->getOperand(), eval );
            eval << new Node( number );
            delete NN;
        };  // end if / else
        
    };  // end while loop
    
    number = Number( eval );
    cout << setprecision(3) << fixed << "\t" << number;
    cout << setprecision(-1) << defaultfloat;
    
}



int main()
{
    ifstream in = openFile(); // openfile
    string line;
    int count = 0;
    while (!in.eof())
    {
        in >> line;
        if ( line.length() > 1 ) {
            if (count++ > 0){
                cout << endl;
            }
            parseline(line); // parse the file and push and pop stacks accordingly
            evaluate(); // evaluate the final result
        };  // end if line length
        line = "";
    }
    cout << endl; // formatting
    
    in.close();
    return 0;
    
}

