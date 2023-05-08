#ifndef PROJ1_BEAKER_H
#define PROJ1_BEAKER_H

class Beaker {
private:
    int contents[4];
    int fullness;

public:
    Beaker();
    explicit Beaker(int code);

    bool add(int liquid);
    bool remove(int& liquid);

    int getTopLiquid();
    int getTopAmmount();

    bool isFull() const;
    bool isEmpty() const;

    int get(int i);

    int toInt();

    bool isGood();

    void print();

};

#endif
