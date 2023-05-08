#ifndef DSPROJECT2022BETTER_ORDEREDARRAY_H
#define DSPROJECT2022BETTER_ORDEREDARRAY_H

#include <string>

class OrderedArray {
private:
    std::string* data;
    int* amount;
    int size;
    int last;

    int search(std::string s,int &pos);
    int search(std::string s,int start,int end,int &pos);
public:
    OrderedArray(int size);
    ~OrderedArray();

    int search(std::string s);

    void shift(int start);
    void insert(std::string s);

    std::string getString(int i) {return data[i];}
    int getAmount(int i) {return amount[i];}

    void printAll();

};
#endif //DSPROJECT2022BETTER_ORDEREDARRAY_H
