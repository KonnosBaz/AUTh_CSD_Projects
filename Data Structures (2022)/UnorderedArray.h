#ifndef DSPROJECT2022BETTER_UNORDEREDARRAY_H
#define DSPROJECT2022BETTER_UNORDEREDARRAY_H

#include <string>

class UnorderedArray {
private:
    std::string* data;
    int* amount;
    int size;
    int next;

public:
    UnorderedArray(int size);
    ~UnorderedArray();

    void insert(std::string s);
    void compress();
    int search(std::string s);

    void print();


    int getSize() {return size;}
    std::string getString(int i) {return data[i];}
    int getAmount(int i) {return amount[i];}

};


#endif //DSPROJECT2022BETTER_UNORDEREDARRAY_H
