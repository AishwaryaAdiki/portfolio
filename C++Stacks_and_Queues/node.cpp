// NAME: AISHWARYA ADIKI
// NETID: AXA180100
//  Node.cpp
//  Homework 7
//  Created by Aishwarya Adiki on 11/6/19.
//  Copyright © 2019 Aishwarya Adiki. All rights reserved.


#include "node.h"

using namespace std;

//-----------------------------------------------------------------------------
ostream& operator<<( ostream& out, const Operand_t& TT ) { // overloaded << operator for pushing operators onto the stack
    
    if ( TT.which == Operand_t::type_t::CHAR ) out << TT.char_data;
    else out << TT.double_data;
    return out;
    
}  // end operand << Operand_t


//-----------------------------------------------------------------------------
ostream& operator<<( ostream& out, const Node& NN ) { // overloaded << operator for the node class
    
    out << NN.operand;
    return out;
    
}  // end operand << Node


//-----------------------------------------------------------------------------
void Node::dump( const char* label ) { //clearing character dump
    cout << label << ": " << id << " : "
    << int( id ) - 65 << " : "
    << operand << endl;
}  // end dump
