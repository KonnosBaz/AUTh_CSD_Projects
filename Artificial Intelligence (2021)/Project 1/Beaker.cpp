#include "Beaker.h"
#include "iostream"

using namespace std;


//Constructor -> The contents of the beaker are initialized as 0 which signifies empty, and so is fullness, since there is no liquid in the beaker yet
Beaker::Beaker()
{
    for (int & content : contents)
    {
        content = 0;
    }
    fullness = 0;
}

Beaker::Beaker(int code)
{
    fullness = 0;

    if (code<1000 || code >9999) {
        for (int i = 0; i < 4; ++i) {
            contents[i] = 0;
        }
    }
    else
    {
        add(code/1000);
        code-=(code/1000)*1000;
        add(code/100);
        code-=(code/100)*100;
        add(code/10);
        code-=(code/10)*10;
        add(code);
    }
}





//Helpers
bool Beaker::isFull() const {return fullness == 4;}

bool Beaker::isEmpty() const {return fullness == 0;}

int Beaker::get(int i) {return contents[i];}

int Beaker::toInt()
{
    return contents[0]*1000 + contents[1]*100 + contents[2]*10 + contents[3];
}

//Push-Pop
bool Beaker::add(int liquid)
{
    if (!isFull())
    {
        contents[fullness] = liquid;
        fullness++;
        return true;
    }
    return false;
}

bool Beaker::remove(int &liquid)
{
    if(!isEmpty())
    {
        fullness --;
        liquid = contents[fullness];
        contents[fullness] = 0;
        return true;
    }
    return false;
}

//Top Layer -> These functions are needed to control the flow of liquids from one beaker to the other
int Beaker::getTopLiquid()
{
    return contents[fullness-1];
}

int Beaker::getTopAmmount()
{
    int sum = 0;
    int topLiquid = getTopLiquid();

    for (int i = fullness-1 ; i >=0 ; i--)
    {
        if (contents[i] == topLiquid)
            sum ++;
    }

    return sum;
}

bool Beaker::isGood()
{
    int first = contents[0];
    if (first==0)
        return false;

    for (int i = 1; i < 4; ++i)
    {
        if(contents[i]!=first)
            return false;
    }

    return true;
}

void Beaker::print()
{
    cout<<fullness<<"  |  "<<toInt()<<endl;
}













