
#include <iostream>
#include <fstream>
#include <string>
#include <stdio.h>
#include <iomanip>
#include <cstring>
#include <cctype>
#include <cmath>
//#include <bits/stdc++.h>

using namespace std;

bool validate (string);
void addRecord (string , fstream&, fstream&, fstream&);
void deleteRecord (string, fstream&, fstream&, fstream&);
void editRecord (string, fstream&, fstream&);
void searchRecord (string, fstream&, fstream&);

int main()
{
    fstream batchFile, logFile;
    fstream databaseFile;
    fstream newDatabaseFile;
    fstream databaseFile2;
    
    string inputLine;
    string batchFilename;
    string logFilename;
    string dataBaseFilename;
    
    cout << "Enter Batch Filename: " <<endl;
    cin >> batchFilename;
    cout << "Enter Log Filename: " <<endl;
    cin >> logFilename;
    cout << "Enter Database Filename: " <<endl;
    cin >> dataBaseFilename;
    
    batchFile.open (batchFilename, ios::in);
    logFile.open (logFilename, ios::app);
    databaseFile.open (dataBaseFilename,ios::in);
    databaseFile2.open ("freeplay.dat", ios::out | ios::app | ios::ate | ios::binary);
    newDatabaseFile.open("freeplay2.txt", ios::app | ios::binary);
    
    
    
    while (!batchFile.eof())
    {
        getline(batchFile, inputLine, '\n');
        
        if (inputLine[0] == '1')
        {
            addRecord(inputLine, databaseFile, databaseFile2, logFile);
        }
        else if (inputLine[0] == '2')
        {
            //searchRecord(inputLine, databaseFile2, logFile);
        }
        else if (inputLine[0] == '3')
        {
            //editRecord(inputLine, databaseFile2, logFile);
        }
        else if (inputLine[0] == '4')
        {
            //deleteRecord(inputLine, databaseFile2, logFile, newDatabaseFile);
            
        }
        
        
    }
    
    batchFile.close();
    logFile.close();
    databaseFile.close();
    databaseFile.close();
    return 0;
}

void addRecord (string inputLine, fstream &databaseFile, fstream &databaseFile2, fstream &logFile)
{
    
    //Parse String (1st Field - name)
    string longname; //creating a substring for storing the longname owith quotation marks
    size_t found = inputLine.find('"');
    size_t nfound = inputLine.find('"', found+1);
    longname = inputLine.substr(found,nfound);
    string name;
    size_t fquotefound = longname.find('"');
    size_t lquotefound = longname.find('"', found+1);
    name = longname.substr(fquotefound+1,lquotefound-1);
    //finished parsing the name string
    
    databaseFile2 << name << ", "; //added the name to freeplay.databaseFile
    logFile << "RECORD ADDED" << endl << "Name: " << name << endl;
    
    //Now Parsing the string for the score (2nd Field).
    string highscore;
    //size_t firstspace = inputLine.find(' ');
    //size_t secondspace = inputLine.find(' ', firstspace + 1);
    size_t thirdspace = inputLine.find(' ', lquotefound + 1);
    size_t fourthsapce = inputLine.find(' ', thirdspace + 1);
    highscore = inputLine.substr(thirdspace+1, fourthsapce - thirdspace -1);
    //string to int conversion for 2nd field;
    databaseFile2 << setfill('0') << setw(9) << highscore << ", ";
    logFile << "High Score: " << highscore << endl;
    
    
    // Now Parsing initials (3rd field)
    string initials;
    size_t fifthspace = inputLine.find(' ', fourthsapce + 1);
    initials = inputLine.substr(fourthsapce+1, fifthspace - fourthsapce -1);
    databaseFile2 << initials << ", ";
    logFile << "Initials: " << initials << endl;
    
    // Now Parsing the plays (4th field)
    string plays;
    size_t sixthspace = inputLine.find(' ', fifthspace + 1);
    plays = inputLine.substr(fifthspace+1, sixthspace - fifthspace -1);
    databaseFile2 << setfill('0') << setw(4) << plays << ", ";
    logFile << "Plays: " << plays << endl;
    
    //Now Parsing the revenue (5th field)
    string revenue;
    size_t endinput = inputLine.find('\n');
    revenue = inputLine.substr(sixthspace + 2, endinput - sixthspace - 3);
    databaseFile2 << "$" << setfill('0') << setw(7) << setprecision (2) << revenue ;
    logFile << "Revenue: " << "$" << revenue << endl << endl;
    
    string transfer;
    databaseFile2 << endl;
    while (!databaseFile.eof())
    {
        getline(databaseFile, transfer);
        databaseFile2 << transfer << endl;
    }
    
    databaseFile2.close();
    
}

void deleteRecord (string inputLine, fstream &databaseFile2, fstream &logFile, fstream &newDatabaseFile)
{
    
    databaseFile2.open ("freeplay.dat", ios::out| ios::in | ios::ate | ios::binary);
    string tempnamecmp;
    size_t parsenameinput = inputLine.find(' ');
    size_t endnameinput = inputLine.find('\n', parsenameinput + 1);
    tempnamecmp = inputLine.substr(parsenameinput + 1, endnameinput - 2);
    
    string newname;
    
    databaseFile2.seekg(0L, ios::beg);
    
    string logFilenm;
    string logFilehs;
    string logFilein;
    string logFilepl;
    string logFilerv;
    
    
    while (!databaseFile2.eof())
    {
        getline (databaseFile2, newname);
        
        if (size_t present = newname.find(tempnamecmp,0) != string::npos) {
            
            size_t ps1 = newname.find(',');
            logFilenm = newname.substr (0, ps1);
            size_t ps2 = newname.find(',', ps1 + 1);
            logFilehs = newname.substr(ps1 + 2, ps2 - ps1 - 1);
            size_t ps3 = newname.find(',', ps2 + 1);
            logFilein = newname.substr(ps2 + 2, ps3 - ps2 - 2);
            size_t ps4 = newname.find(',', ps3 + 1);
            logFilepl = newname.substr(ps3 + 2, ps4 - ps3 - 2);
            size_t ps5 = newname.find('\n', ps4 + 1);
            logFilerv = newname.substr(ps4 + 3, ps5 - ps4 - 2);
        }
        else {
            newDatabaseFile << newname << endl;
        }
        
    }
    
    if(remove( "freeplay.dat") != 0 ){
        cout <<  "Error deleting file"  << endl;
    }
    else {
        cout << "File successfully deleted" <<  endl;
    }
    
    int result;
    char oldFilename[] ="freeplay2.txt";
    char newFilename[] ="freeplay.dat";
    result= rename(oldFilename , newFilename );
    if ( result == 0 ) {
        cout << "File successfully renamed"  << endl;
    } else {
        
        cout << "Error renaming file" << endl;
    }
    
    string trimmedrevenue;
    while (logFilerv[0]=='0') {
        trimmedrevenue = logFilerv.erase(0,1);
    }
    logFile << "RECORD DELETED" << endl;
    logFile << "Name: " << logFilenm << endl;
    logFile << "High Score: " << stoi(logFilehs) << endl;
    logFile << "Initials: " << logFilein << endl;
    logFile << "Plays: " << stoi(logFilepl) << endl;
    logFile << "Revenue: " << "$" << setprecision (2) << trimmedrevenue << endl << endl;
    
    databaseFile2.close();
    
}

void editRecord (string inputLine, fstream &databaseFile2, fstream &logFile)
{
    databaseFile2.open ("freeplay.dat", ios::out| ios::in | ios::ate | ios::binary);
    string editName;
    string editHighscore;
    string editFieldNo;
    
    string temp_name_edit;
    
    //Substring name
    size_t pos1 = inputLine.find('"');
    size_t pos2 = inputLine.find ('"', pos1 + 1);
    temp_name_edit = inputLine.substr(pos1,pos2);
    
    size_t temppos1 = temp_name_edit.find('"');
    size_t temppos2 = temp_name_edit.find('"', temppos1 + 1);
    editName = temp_name_edit.substr(temppos1 + 1, temppos2 - 1);
    //cout << editName << endl;
    
    //Substring the feild
    size_t pos3 = inputLine.find(' ', pos2);
    size_t pos4 = inputLine.find(' ', pos3+1);
    editFieldNo = inputLine.substr(pos3+1, pos4 - pos3 -1);
    //cout << editFieldNo << endl;
    
    //Substring the highscore
    size_t pos5 = inputLine.find('\n', pos4+1);
    editHighscore = inputLine.substr(pos4 + 1, pos5 - pos4 -1);
    //cout << editHighscore << endl;
    
    string readline;
    //long numbytes;
    
    databaseFile2.seekg(0L, ios::beg);
    
    long bolpointer = 0;
    //long length_ediname = editName.length();
    
    string logFilenm2;
    string logFilehs2;
    string logFilein2;
    string logFilepl2;
    string logFilerv2;
    
    while (!databaseFile2.eof())
    {
        getline (databaseFile2, readline);
        
        if (size_t pointer_pos1 = readline.find(editName) != string::npos)
        {
            
            break;
            
        }
        else {
            bolpointer = bolpointer + readline.length();
            
        }
    }
    databaseFile2.seekg(0L, ios::beg);
    size_t marker = readline.find(',');
    long finalmarker = marker + 2 + bolpointer + 4;
    databaseFile2.seekp(finalmarker, ios::beg);
    
    databaseFile2 << setfill('0') << setw(9) << editHighscore;
    size_t ps1 = readline.find(',');
    logFilenm2 = readline.substr (0, ps1);
    size_t ps2 = readline.find(',', ps1 + 1);
    logFilehs2 = readline.substr(ps1 + 2, ps2 - ps1 - 1);
    size_t ps3 = readline.find(',', ps2 + 1);
    logFilein2 = readline.substr(ps2 + 2, ps3 - ps2 - 2);
    size_t ps4 = readline.find(',', ps3 + 1);
    logFilepl2 = readline.substr(ps3 + 2, ps4 - ps3 - 2);
    size_t ps5 = readline.find('\n', ps4 + 1);
    logFilerv2 = readline.substr(ps4 + 3, ps5 - ps4 - 2);
    
    string trimmedrevenue2;
    while (logFilerv2[0]=='0') {
        trimmedrevenue2 = logFilerv2.erase(0,1);
    }
    
    logFile << editName << " updated"<<  endl;
    logFile << "Name: " << logFilenm2 << endl;
    logFile << "High Score: " << editHighscore << endl;
    logFile << "Initials: " << logFilein2 << endl;
    logFile << "Plays: " << stoi(logFilepl2) << endl;
    logFile << "Revenue: " << "$" << setprecision (2) << trimmedrevenue2 << endl << endl;
    
    databaseFile2.close();
    
    
}
void searchRecord (string inputLine, fstream &databaseFile2, fstream &logFile)
{
    databaseFile2.open ("freeplay.dat", ios::out| ios::in | ios::ate | ios::binary);
    
    string searchline;
    string searchname;
    size_t newlinepos = inputLine.find(' ');
    size_t newlinepos2 = inputLine.find('\n', newlinepos + 1);
    searchname = inputLine.substr(newlinepos + 1, newlinepos2 - 2);
    //cout << searchname << endl;
    
    //  Substrings for logFile
    string logFilenm3;
    string logFilehs3;
    string logFilein3;
    string logFilepl3;
    string logFilerv3;
    
    databaseFile2.seekg(0L, ios::beg);
    
    while (!databaseFile2.eof())
    {
        getline(databaseFile2, searchline);
        
        if (size_t searchpos = searchline.find(searchname) != string::npos) {
            
            break;
        }
        
    }
    size_t ps1 = searchline.find(',');
    logFilenm3 = searchline.substr (0, ps1);
    size_t ps2 = searchline.find(',', ps1 + 1);
    logFilehs3 = searchline.substr(ps1 + 2, ps2 - ps1 - 2);
    size_t ps3 = searchline.find(',', ps2 + 1);
    logFilein3 = searchline.substr(ps2 + 2, ps3 - ps2 - 2);
    size_t ps4 = searchline.find(',', ps3 + 1);
    logFilepl3 = searchline.substr(ps3 + 2, ps4 - ps3 - 2);
    size_t ps5 = searchline.find('\n', ps4 + 1);
    logFilerv3 = searchline.substr(ps4 + 3, ps5 - ps4 - 2);
    
    string trimmedrevenue3;
    while (logFilerv3[0]=='0') {
        trimmedrevenue3 = logFilerv3.erase(0,1);
    }
    
    logFile << searchname << " found"<<  endl;
    logFile << "Name: " << logFilenm3 << endl;
    logFile << "High Score: " << stoi(logFilehs3) << endl;
    logFile << "Initials: " << logFilein3 << endl;
    logFile << "Plays: " << stoi(logFilepl3) << endl;
    logFile << "Revenue: " << "$" << setprecision (2) << trimmedrevenue3 << endl << endl;
    
    databaseFile2.close();
}
