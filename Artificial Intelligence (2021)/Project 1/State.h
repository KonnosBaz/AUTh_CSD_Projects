#include "Beaker.h"

#ifndef PROJ1_STATE_H
#define PROJ1_STATE_H


class State{
public:
    int N;
    Beaker beakers[20];
public:
    State(int N);
    State(int N, int* code);

    static bool move(Beaker& from,Beaker& to);
    bool move(int a,int b);

    int* extractState();

    void print();

    bool isFinal();



};

#endif
