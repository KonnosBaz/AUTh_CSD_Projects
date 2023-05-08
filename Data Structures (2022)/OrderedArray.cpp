#include <iostream>
#include "OrderedArray.h"

using namespace std;

//Constructor : Allocates needed memory
OrderedArray::OrderedArray(int size) : size(size)
{
    data = new string[size];
    amount = new int[size];
    last = -1;
}

//Same as below but we don't bother with pos
int OrderedArray::search(std::string s)
{
    int whatever;
    return search(s,whatever);
}


//Same as below but with range set as the entire array
int OrderedArray::search(string s,int &pos)
{
    return search(s,0,last,pos);
}

//Binary Search Algorithm with custom range
//Returns the index where s is found or -1 if s does not belong in the array
//When s is not found, the pos variable takes the value of the index where s should be inserted
int OrderedArray::search(string s, int start, int end, int &pos)
{
    if (start > end)
    {
        pos = start;
        return -1;
    }

    int mid = (end + start)/2;

    if (s == data[mid])
        return mid;

    if (s<data[mid])
        return search(s,start,mid-1,pos);
    else
        return search(s,mid+1,end,pos);
}


//Moves all contents of the array one place forward
//CAN CAUSE ERRORS IF THE ARRAY IS FULL
void OrderedArray::shift(int start)
{
    for (int i = last; i >= start; i--)
    {
        data[i+1] = data[i];
        amount[i+1] = amount[i];
    }
}

//Searches for the given string in the existing array
//If it already exists, the amount of appearances of the array is increased by one
//Otherwise it uses the shift function to make place for the new string, and then inserts it
void OrderedArray::insert(string s)
{
    if(last == -1)
    {
        data[0] = s;
        amount[0] = 1;
        last++;
        return;
    }

    int pos;
    int place = search(s,pos);

    if (place == -1)
    {
        shift(pos);
        data[pos] = s;
        amount[pos] = 1;
        last++;
    }
    else
    {
        amount[place]++;
    }

}

//Simple print function for debugging purposes
void OrderedArray::printAll()
{
    for (int i = 0; i <= last; ++i)
    {
        cout<<i<<"|"<<data[i]<<"|"<<amount[i]<<endl;
    }
}

//Destructor : de-allocates all the dynamically allocated memory
OrderedArray::~OrderedArray()
{
    delete[] data;
    delete[] amount;
}

