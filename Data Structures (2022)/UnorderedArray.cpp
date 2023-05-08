#include "UnorderedArray.h"
#include <iostream>

using namespace std;


//Constructor: initializes the arrays we will need to store the data
UnorderedArray::UnorderedArray(int size) : size(size), next(0)
{
    data = new string[size];
    amount = new int[size];
}

//Inserts a string and sets its amount to 1 in the next available position
void UnorderedArray::insert(string s)
{
    if (next==size)
    {
        cerr<<"Could not insert "<<s<<" due to lack of space"<<endl;
        return;
    }


    data[next] = s;
    amount[next] = 1;
    next++;
}

//Removes duplicate strings (by setting their amount to -1) and keeps track of the amount of their appearances
void UnorderedArray::compress()
{
    cout<<"counting pairs..."<<endl;
    int gone = 0;
    int left;
    int percentage;
    int last = 0;


    for (int i = 0; i < size; ++i) {
        if (amount[i] != -1) {
            gone++;
            for (int j = i + 1; j < size; ++j) {
                if (amount[j] != -1) {
                    if (data[i] == data[j]) {
                        amount[i]++;
                        amount[j] = -1;
                        gone++;
                    }
                }
            }
        }
        left = size - gone;
        percentage = 100 - left / (size / 100);
        if (percentage > last)
        {
            cout << percentage << "%" << endl;
            last = percentage;
        }
    }
}

//Linear search that skips strings with amount -1
//Returns the index of the string in the array or -1 if it is not found
int UnorderedArray::search(std::string s)
{
    for (int i = 0; i < size; ++i) {
        if (amount[i]!=-1)
        {
            if (data[i] == s)
                return i;
        }
    }
    return -1;
}

//Prints the contents of the Array that have a valid amount
void UnorderedArray::print()
{
    for (int i = 0; i < size; ++i)
    {
        if (amount[i] > 0)
            cout<<i<<"|"<<data[i]<<"|"<<amount[i]<<endl;
    }
}


//Destructor : de-allocates the dynamically allocated memory
UnorderedArray::~UnorderedArray()
{
    delete[] data;
    delete[] amount;
}

