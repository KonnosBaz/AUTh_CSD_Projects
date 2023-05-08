#include <iostream>
#include "State.h"
#define N 8
#include "random"
#include "time.h"
#include "queue"


using namespace std;

State randomState()
{
    State s = State(N);
    //srand(time(NULL));

    for (int i = 1; i <= N - 2; ++i) {
        for (int j = 0; j < 4; ++j) {
            int k = rand()%N;
            s.beakers[k].add(i);

        }
    }

    return s;
}



int main()
{
    State ini = randomState();
    ini.print();

    vector<State> closed;
    queue<State> sf;

    if (ini.isFinal())
        cout<<"done";














    return 0;

}
