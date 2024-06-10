/*
 Name: AISHWARYA ADIKI
*/

#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <memory>
#include <iomanip>

using namespace std;

//-----------------------------------------------------------------------------
struct Record //Define the data types within the record
{
    
    //default data types
    Record( string="head", int=0, string="rh", int=0, double=0.0 ); // record's default data definitions -> default constructor
    Record( Record& );
    ~Record();
    
    static Record Head; // create a static record object called Head
    string name; //to store the name
    int highscore;
    string initials;
    int plays;
    double revenue;
    Record *next; // pointer to the next record in the linked list
    
    Record *end(); // the end of the list --> defined as a function
    
    //List of helper functions that add a new node; remove a node,; print the list
    //format the output of the different elements in the list; and copy information to a new node, respectively
    string add(Record *);
    string rm(const string& );
    void print(ostream& );
    string format_out( bool = true );
    void copy( const Record* );
    
};//struct record defined


//-----------------------------------------------------------------------------
struct Data { // This struct will be used to define the Head of each node. This will be the first item of the linked list
    //everything elese (add, search, sort, delete) will be added and deleted with the head node as the reference point so the linked list is not lost
    
    
    Data(){}; // inLine function definition/constructor for Data struct
    ~Data(); // Destructor
    static void print( ostream& );
    static Record Head; // object "Head" for the struct Record
};  // end struct Data


//-----------------------------------------------------------------------------
Data::~Data()
{
    Record* temp(nullptr);
    while(Data::Head.next != nullptr) {
        temp = Data::Head.next;
        Data::Head.next = Data::Head.next->next;
        delete temp;
    }  // end while loop
}  // Data destructor defined


//-----------------------------------------------------------------------------
string Record::format_out( bool print_name ) // Printing the record to the log file
{
    ostringstream out;
    
    if ( print_name == true ) { // only print nam when required
        out << "Name: " << name << endl;
    }  // end if
    
    //prting the entire record in the right format
    out << "High Score: " << highscore << endl
    << "Initials: " << initials << endl
    << "Plays: " << plays << endl
    << "Revenue: $" << fixed << setprecision(2)
    << revenue << endl;
    
    return out.str();
    
} // end format_record

//-----------------------------------------------------------------------------
void Record::print(ostream& out) { // function for printing the record to the database file with the correct format
    
    out  << name  << ", "
    << highscore << ", "
    << initials << ", "
    << plays << ", $"
    << setprecision(2) << fixed << revenue // revenue has two decimal places hence set precision
    << endl;
    
}  // end record print function


//-----------------------------------------------------------------------------
void Data::print(ostream& out) // printing the add record linked list to the database file
{
    Record* record = Data::Head.next;
    
    while(record != nullptr) {
        record->print(out); // print the record to the database file in the format specified in the function void Record::print(ostream &out)
        record = record->next; // print the rest of the linked list of records
    }  // end print
} // end print


//-----------------------------------------------------------------------------
Record Data::Head;

//-----------------------------------------------------------------------------
Record::Record( string N, int H, string I, int P, double R )
: name( N ), highscore( H ), initials( I ), plays( P ),
revenue( R ), next( nullptr ) {} //overloaded constructor for Record


Record::Record( Record& orig )
: name( orig.name ), highscore( orig.highscore ), initials( orig.initials ),
plays( orig.plays ), revenue( orig.revenue ) {} // constructor for the structure on how to access the elements in the Record Structure and store the parsed batch file input

//-----------------------------------------------------------------------------
Record::~Record()
{
} //destructor for the record


//-----------------------------------------------------------------------------
Record *Record::end() { // The last node on the linked list for either add, search, edit, delete, or sort
    Record *previous = &Data::Head;
    while ( previous->next != nullptr )
    {
        previous = previous->next;
    }
    return previous; // return everything until nullptr (or end of the linked list) is encountered
}


//-----------------------------------------------------------------------------
void Record::copy( const Record* record ) // copy Rcord constructor
{
    name = record->name;
    highscore = record->highscore;
    initials = record->initials;
    plays = record->plays;
    revenue = record->revenue;
}  // copy constrcutor for the Record (this is a class constructor conpcept that has been implemented) copy contructor type is a form of a overloaded constructor


//-----------------------------------------------------------------------------
string Record::add( Record* record ) // Add a node for the linked list. Add to the end with the head being the first node
{
    Record *last = Data::Head.end(); // the first node
    last->next = record; // the second node is the record
    return record->format_out(); // return the record (which will be written to the logfile) in the format specified in the string Record::format_out function
    
}//end void addRecord


//----------------------------------------------------------------------------
string Record::rm( const string& name ) // Removing or deleting nodes from the linked list
{
    
    string format( "Record NOT FOUND" ); // deafult string  assuming that the record was not found. Each record will
    Record* prev = nullptr;
    Record* current = &Data::Head; // current pointing to the head of the linked list
    bool found = false; // to check whether the record that the user wants to delete has been found
    
    
    while( current != nullptr ) {
        
        //cerr<<"Debug name: " << current->name << endl;
        if ( current->name.compare( name ) == 0 ) {
            prev->next = current->next;
            format = current->format_out(); // if the name of the record was found, then write the record deleted to the log file in the correct logfile output format)
            delete current; // delete the current pointer so that the record deleted will be permanently lost from memory (there is no way to go back to it)
            found = true; // name (Record) found and deleted
        } // end if
        prev = current;
        current = current->next;
    } // end while loop
    
    return format; // return the record that has either been deleted or not found and hence can't be deleted
    
} // end rm

//-----------------------------------------------------------------------------
string file_name( const string& message ) {
    
    string filename; // accepting the 3 filenames in the order: database filename, batch filename, log filename
    //cout << message;
    cin >> filename;
    return filename;
    
} // end file_name


// Parse a line from the database file and add the record
//-----------------------------------------------------------------------------
void parse_database_line(istringstream& in) { // Parse the entire Database file so that each parsed item can be stored as either the name, highscore,
    
    Record* record = new Record(); // dynamically create space in memory to hold one Record
    string str;
    
    // name
    getline( in, str, ',' ); // parse name
    record->name = str;
    
    // high-score
    getline( in, str, ',' ); // parse high score
    record->highscore = stoi(str); // convert that highscore to an integer from a string
    
    // initials
    getline( in, str, ',' ); //  parse Initials
    str.erase(0,1);
    record->initials = str;
    
    
    // plays
    getline( in, str, ',' ); // Parse plays
    record->plays = stoi( str ); // convert the string to an integer
    
    // revenue
    getline( in, str, '$' ); //Parse revenue
    getline( in, str );
    record->revenue = stod(str); // convert the string to a double
    
    Data::Head.add(record); // add the record after the head, thus creating the linked list
    
}  // end parse_database_line




// Read each line in the database file and build the memory database
//-----------------------------------------------------------------------------
void read_database() {
    
    istringstream linestream;
    string line;
    
    ifstream in(file_name( "" ));
    while ( getline( in, line ) ) {
        linestream.str( line );
        linestream.clear();
        parse_database_line( linestream );
    }  // end while
    
} // end read_database


//-----------------------------------------------------------------------------
void add_to_database( istringstream& in, ofstream& log ) { // Performing the add requirement
    
    Record* record = new Record(); // dynamically create space in memory to add one Record to the database
    char dollar_sign;
    
    in >> quoted(record->name) // from the batch file, get the name, highscore, initials, plays, and revenue
    >> record->highscore
    >> record->initials
    >> record->plays
    >> dollar_sign
    >> record->revenue;
    if(record->name.compare("head") == 0)
    {
        return;
    }
    log << "RECORD ADDED" << endl
    << Data::Head.add(record) << endl; // output the Record added to the linked list
    
}  // end ad_to_database


//-----------------------------------------------------------------------------
void search_for_record(istringstream& in, ofstream& log ) { // Performing the search requirement
    
    Record* record = &Data::Head; // record pointing the
    string term;  // what to search for
    bool found = false;
    //string newterm;
    //in >> term;
    getline( in, term);  // get search term
    term.erase(0, 1);  // remove leading space
    if(term.find("\r") != string::npos) // removing the carriage returns
    {
        term = term.substr(0, term.length()-1);
    }
    
    //cerr << "Debug search: " << term << endl;   // Debug statements
    //log << "Debug: " << "'" << newterm << "'" << endl; // Debug statements
    while((record != nullptr ))
    {
        if((record->name.find(term) != string::npos)) // if the search term is found in the record's name element, then print name and found and write the rest of the record to logfile
        {
            log << record->name << " FOUND" << endl << record->format_out( false ) << endl;
            found = true; // the record has been found
        }
        record = record->next; // go the next item in thelinked list
    }  // end while
    
    if (found == false)
    {
        log << term << " NOT FOUND" << endl << endl; // search term not found
    }
    
    
}  // end search_for_record


//-----------------------------------------------------------------------------
void edit_record(istringstream& in, ofstream& log) { // Performing the edit requirement
    
    ostringstream out;
    string name;
    int command; // the field number that needs to be edited
    string value;
    Record* record = &Data::Head;
    
    in >> quoted(name) >> command >> value; // get the different variables in the order
    
    while( ( record != nullptr ) && ( record->name.compare(name) != 0 ) ) {
        record = record->next;
    }  // while
    
    if ( record != nullptr ) {
        log << record->name << " UPDATED" << endl << "UPDATE TO "; // the correct form to record the edit in the log file
        switch ( command ) { // switch function (an extended if-elseif essentially) to determine which field to edit
            case 1: // high score // if the int command = 1, edit the highscore field
                log << "high score";
                record->highscore = stoi( value );
                break;
            case 2: // initials // if the int command = 2, edit the initials field
                log << "initials";
                record->initials = value;
                break;
            case 3: // plays // if the int command = 3, edit the plays field
                log << "plays";
                record->plays = stoi( value );
                record->revenue = (record->plays)*0.25;
                break;
            default:
                log << "Unknown edit command" << endl; // if it's none of the fields, write error message to log file. However, this will never happne because the input will be validated as mentioned in the Project 3 instructions
        };  // end switch
        log << " - VALUE " << value << endl << record->format_out() << endl;
        
    }  // end if
    
}  // end edit_record


// Delete a record from the list
//-----------------------------------------------------------------------------
void delete_record( istringstream& in, ofstream& log ) { // Performing the delete requirement
    
    string name;
    getline( in, name);  // get the name of the record to be deleted
    name.erase( 0, 1);  // remove the leading space
    if (name.find("\n") != string::npos ||name.find("\r") != string::npos) // remove the carraige return
    {
        name = name.substr(0, name.length()-1);
    }
    log << "RECORD DELETED" << endl
    << Data::Head.rm(name) << endl; // print the record that was removed in the correct output format that is given in the function .rm (remove)
}  // end delete_record


//-----------------------------------------------------------------------------
bool sort_by_name( const Record* one, const Record* two ) { // sort by name;
    
    if ( ( one == nullptr ) || ( two == nullptr ) )
        return false; // if there is only one item in the linked list, then it is already sorted
    return ( one->name.compare( two->name ) > 0 ); // bubble sort -> return true or false so that it can be used in the sort_records function
    
}  // end sort_by_name


//-----------------------------------------------------------------------------
bool sort_by_plays( const Record* one, const Record* two ) { // sort by the number of plays
    
    if ( ( one == nullptr ) || ( two == nullptr ) )
        return false; // if there is only one item in the linked list, then it is already sorted
    if (one->plays == two->plays) // if the number of plays are the same, then go ahead and sort according to name
    {
        return sort_by_name(one,two); // sort by name
    }
    return ( one->plays >= two->plays ); // return either true or false so that the return booln can be used in the sort_records function
    
    
}  // end sort_by_plays


//-----------------------------------------------------------------------------
void sort_records( istringstream& in, ofstream& log ) { // Performing the sort requirement
    
    bool (*compare)( const Record*, const Record* ) = sort_by_name;
    string term;
    in >> term;  // name or plays
    
    if ( term.compare( "name" ) == 0 ) {
        compare = sort_by_name;
    }  // end sort by name
    if ( term.compare( "plays" ) == 0 ) {
        compare = sort_by_plays;
    }  // end sort by plays
    
    Record temp; // create a temporary record
    
    for ( Record* current = Data::Head.next ; current->next != nullptr; current = current->next ) { //pointer at the head
        for ( Record* iter = current->next; iter != nullptr; iter = iter->next ) {// Perform the sort-> bubble
            if ( compare( current, iter ) == true ) {
                temp.copy( current );
                current->copy( iter );
                iter->copy( &temp );
            }  // end if compare
        }  // end for loop
    } // end for loop
    
    log << "RECORDS SORTED BY " << term << endl; // write to log file in the right format
    Data::print(log); // write all the soreted records to the log file, as they appear in the databasefile but sorted.
    log << endl;
    
}  // end search_records

//-----------------------------------------------------------------------------
void read_batchfile() {
    
    istringstream linestream;
    string line;
    int command;
    
    ifstream infile( file_name( "" ) );  // get batch file
    ofstream log( file_name( "" ), ofstream::out  );  // get log file name
    
    while( getline( infile, line ) ) { // while there is a line to get aka while not .eof()
        
        linestream.str( line );
        linestream.clear();
        linestream >> command; // get the command, i.e. the first letter of each line in the batch file that correpsonds to the action that needs to be performed.
        
        switch( command ) {
            case 1: add_to_database( linestream, log ); break; // if the command number = 1, then add
            case 2: search_for_record( linestream, log ); break; // if the command number = 2, then search
            case 3: edit_record( linestream, log ); break; // if the command number = 3, then edit
            case 4: delete_record( linestream, log ); break; // if the command number = 4, then delete
            case 5: sort_records( linestream, log ); break; // if the command number = 5, then sort
            default: cerr << "Command " << command << " not yet supported" << endl; // when the command is not add, search, edit, delete, or sort
        }  // end switch
        
    }  // end while
    
    log.close(); // close log file
    infile.close(); // close batch file
    
}  // end read_database

//-----------------------------------------------------------------------------
void writeFreeplay (Data& data)
{
    ofstream out ("freeplay.dat", ios::out);
    data.print(out); // print the correct linked list to freeplay.dat, after all manipulations (add, search, edit, delete, and sort) have been done in memory
    out.close(); // close the database file
}

//-----------------------------------------------------------------------------
int main()
{
    
    Data data;  // store all data in memory
    read_database(); // read the contents of the database file
    read_batchfile();  // read and parse the batch file
    writeFreeplay(data); // write the data to the database file
    return 0;
    
}

