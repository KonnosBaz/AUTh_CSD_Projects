#ifndef DSPROJECT2022BETTER_HASHTABLE_H
#define DSPROJECT2022BETTER_HASHTABLE_H

#include <string>

//This class implements a Hash Table
//The hash function can be found (and changed) in the implementation

class HashTable {
private:
    //The size of the table
    int size;
    //Pointers to arrays that get dynamically created during construction
    std::string* data;
    int* amount;

public:
    //Constructor : initializes the size and allocates memory for data and amount
    //It also initializes all amounts as 0
    HashTable(int size);
    //Destructor : de-allocates all dynamically allocated memory
    ~HashTable();

    //If s already belongs in the table, its amount gets increased
    //Else it is inserted in the right position with amount 0
    void insert(std::string s);
    //Returns the index where s is found, or -1 if s doesn't belong in the table
    int search(std::string s);

    //Getters (self-explanatory)
    std::string getString(int i){return data[i];}
    int getAmount(int i){return amount[i];}
};


#endif //DSPROJECT2022BETTER_HASHTABLE_H
