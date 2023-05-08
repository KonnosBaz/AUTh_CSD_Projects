#include "HashTable.h"
#define c1 69
#define c2 420
//The hashing function
int hash(std::string s,int x,int size)
{
    int sum = 0;

    for (int i = 0; i < s.length(); ++i) {
        sum+=abs(s.at(i));
    }
    return ((sum + x*x*(c1 + c2)))%size;
}


//Constructor
HashTable::HashTable(int size):size(size)
{
    data = new std::string[size];
    amount = new int[size];

    for (int i = 0; i < size; ++i) {
        amount[i] = 0;
    }
}
//Destructor
HashTable::~HashTable()
{
    delete[] data;
    delete[] amount;
}

//Insert
void HashTable::insert(std::string s)
{
    int index = search(s);

    if (index != -1)
    {
        amount[index] ++;
        return;
    }

    int i = 0;
    while (true)
    {
        index = hash(s,i,size);
        if (amount[index] == 0)
        {
            data[index] = s;
            amount[index] = 1;
            return;
        }
        i++;
    }

}

//Search
int HashTable::search(std::string s)
{
    int i = 0;

    while (true)
    {
        int index = hash(s,i,size);

        if (amount[index] == 0)
        {
            return -1;
        }

        if (data[index] == s)
        {
            return index;
        }
        i++;
    }
}

