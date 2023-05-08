#include "State.h"
#include "iostream"
#include "iomanip"
#include "cstdlib"
using namespace std;

int compare(const void* a, const void* b)
{
    const int* x = (int*) a;
    const int* y = (int*) b;

    if (*x > *y)
        return 1;
    else if (*x < *y)
        return -1;

    return 0;
}


State::State(int N) :N(N)
{
    for (int i = 0; i < N; ++i)
    {
        beakers[i] = Beaker();
    }
}

State::State(int N, int *code) :N(N)
{
    for (int i = 0; i < N; ++i)
    {
        beakers[i] = Beaker(code[i]);
    }

}



bool State::move(Beaker &from, Beaker &to)
{
    if(from.isEmpty())
        return false;

    if (to.isFull())
        return false;


    int k = from.getTopAmmount();
    int liquid = from.getTopLiquid();


    if(to.getTopLiquid()!=from.getTopLiquid() && !to.isEmpty())
        return false;

    int i;

    for(i = 0; i < k ; i++)
    {
        if (!to.add(liquid))
            break;
    }


    for (i = i ; i > 0 ; i--)
    {
        from.remove(liquid);
    }

    return true;
}

bool State::move(int a, int b)
{
    return move(beakers[a],beakers[b]);
}




void State::print()
{

    cout<<"============================================================================="<<endl;
    for (int j = 0; (j < 10 && j < N); ++j)
    {
        cout<<"|  |   ";
    }
    cout<<endl;

    for (int i = 3; i >= 0; --i)
    {
        for (int j = 0; (j < 10 && j < N); ++j)
        {
            if (beakers[j].get(i) == 0)
                cout<<"|  |   ";
            else
                cout<<"|"<<setw(2)<<beakers[j].get(i)<<"|   ";
        }
        cout<<endl;
    }

    for (int j = 0; (j < 10 && j < N); ++j)
    {
        cout<<"|__|   ";
    }
    cout<<endl;
    for (int j = 0; (j < 10 && j < N); ++j)
    {
        cout<<" "<<setw(2)<<j<<"    ";
    }
    cout<<endl<<endl<<endl;

    for (int j = 10; (j < 20 && j<N); ++j)
    {
        cout<<"|  |   ";
    }
    cout<<endl;

    for (int i = 3; i >= 0; --i)
    {
        for (int j = 10; (j < 20 && j<N); ++j)
        {
            if (beakers[j].get(i) == 0)
                cout<<"|  |   ";
            else
                cout<<"|"<<setw(2)<<beakers[j].get(i)<<"|   ";
        }
        cout<<endl;
    }

    for (int j = 10; (j < 20 && j<N); ++j)
    {
        cout<<"|__|   ";
    }
    cout<<endl;
    for (int j = 10; (j < 20 && j<N); ++j)
    {
        cout<<" "<<setw(2)<<j<<"    ";
    }

    cout<<endl;
    cout<<"============================================================================="<<endl;
}

int* State::extractState()
{
    //get codes for all the beakers
    int *beaks = new int(N);
    for (int i = 0; i < N; ++i) {
        beaks[i] = beakers[i].toInt();
    }

    qsort(beaks,N,sizeof (int),compare);

    //and now make a code for the whole state
    return beaks;
}


bool State::isFinal()
{
    for (int i = 0; i < N; ++i)
    {
        if (!beakers[i].isGood() && !beakers[i].isEmpty())
            return false;
    }

    return true;
}