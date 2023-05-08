#include <iostream>
#include <chrono>

#include "FileStuff.h"

#include "UnorderedArray.h"
#include "OrderedArray.h"
#include "BST.h"
#include "AVLTree.h"
#include "HashTable.h"


#define inText "small-file.txt"
#define outText "output.txt"

#define STARTTIMER start = std::chrono::high_resolution_clock::now();
#define STOPTIMER end = std::chrono::high_resolution_clock::now();duration = std::chrono::duration_cast<std::chrono::milliseconds>(end - start);

#define SEPARATOR "============================================================================"
#define MS " milliseconds"

using namespace std;


//Randomly Selects 1000 strings from the contents of an unordered array and returns a pointer to the array that contains them
//Should be used before ua gets compressed
string* getQ(UnorderedArray &ua)
{
    srand(time(0));
    int indexes[1000];

    for (int i = 0; i < 1000; ++i) {
        indexes[i] = rand()%ua.getSize();
    }

    auto Q = new string[1000];

    for (int i = 0; i < 1000; ++i)
    {
        Q[i] = ua.getString(indexes[i]);
    }

    return Q;
}





int main()
{
    //Time Stuff
    auto start = std::chrono::high_resolution_clock::now();
    auto end = std::chrono::high_resolution_clock::now();
    auto duration = std::chrono::duration_cast<std::chrono::milliseconds>(end - start);

    //Initial File Stuff
    formatFile(inText);
    int wc = countWords(ft);
    ofstream output(outText);


    //Unordered Array Stuff
    output<<"UNORDERED ARRAY:"<<endl<<endl;
    STARTTIMER
    auto ua = new UnorderedArray(wc - 1);
    STOPTIMER

    output<<"Initialization :"<<duration.count()<<MS<<endl<<endl;

    STARTTIMER
    loadInto(ua,ft,wc);
    STOPTIMER

    output<<"Loading :"<<duration.count()<<MS<<endl<<endl;

    string *Q = getQ(*ua);

    STARTTIMER
    ua->compress();
    STOPTIMER

    output<<"Formatting (counting pairs):"<<duration.count()<<MS<<endl<<endl;
    output<<"SEARCHES :"<<endl;
    output<<SEPARATOR<<endl;
    STARTTIMER
    for (int i = 0; i < 1000; ++i)
    {
        int index = ua->search(Q[i]);
        output<<index<<"|"<<ua->getString(index)<<"|"<<ua->getAmount(index)<<endl;
    }
    STOPTIMER
    output<<SEPARATOR<<endl;
    output<<"Total Search Time :"<<duration.count()<<MS<<endl<<endl<<endl<<endl;

    delete ua;


    //Ordered Array Stuff
    output<<"ORDERED ARRAY:"<<endl<<endl;
    STARTTIMER
    auto oa = new OrderedArray(wc -1);
    STOPTIMER
    output<<"Initialization :"<<duration.count()<<MS<<endl<<endl;

    STARTTIMER
    loadInto(oa,ft,wc);
    STOPTIMER
    output<<"Loading :"<<duration.count()<<MS<<endl<<endl;


    output<<"SEARCHES :"<<endl;
    output<<SEPARATOR<<endl;
    STARTTIMER
    for (int i = 0; i < 1000; ++i)
    {
        int index = oa->search(Q[i]);
        output<<index<<"|"<<oa->getString(index)<<"|"<<oa->getAmount(index)<<endl;
    }
    STOPTIMER
    output<<SEPARATOR<<endl;
    output<<"Total Search Time :"<<duration.count()<<MS<<endl<<endl<<endl<<endl;

    delete oa;



    //Binary Search Tee Stuff
    output<<"BINARY SEARCH TREE:"<<endl<<endl;

    auto bst = new BST();

    STARTTIMER
    loadInto(bst,ft,wc);
    STOPTIMER

    output<<"Loading Time : "<<duration.count()<<MS<<endl<<endl;

    BST* bsr;

    output<<"SEARCHES :"<<endl;
    output<<SEPARATOR<<endl;
    STARTTIMER
    for (int i = 0; i < 1000; ++i)
    {
        bsr = bst->search(Q[i]);
        output<<bsr->getString()<<"|"<<bsr->getAmount()<<endl;

    }
    STOPTIMER
    output<<SEPARATOR<<endl;
    output<<"Total Search Time :"<<duration.count()<<MS<<endl<<endl<<endl<<endl;

    delete bst;



    //AVL Stuff
    output<<"AVL TREE:"<<endl<<endl;

    auto avlt = new AVLTree();

    STARTTIMER
    loadInto(avlt,ft,wc);
    STOPTIMER

    output<<"Loading Time : "<<duration.count()<<MS<<endl<<endl;

    AVL* asr;

    output<<"SEARCHES :"<<endl;
    output<<SEPARATOR<<endl;
    STARTTIMER
    for (int i = 0; i < 1000; ++i)
    {
        asr = avlt->search(Q[i]);
        output<<asr->getString()<<"|"<<asr->getAmount()<<endl;

    }
    STOPTIMER
    output<<SEPARATOR<<endl;
    output<<"Total Search Time :"<<duration.count()<<MS<<endl<<endl<<endl<<endl;

    delete avlt;



    //Hash Table Stuff
    output<<"HASH TABLE:"<<endl<<endl;

    STARTTIMER
    auto ht = new HashTable(wc - 1);
    STOPTIMER

    output<<"Initialization : "<<duration.count()<<MS<<endl<<endl;


    STARTTIMER
    loadInto(ht,ft,wc);
    STOPTIMER

    output<<"Loading Time : "<<duration.count()<<MS<<endl<<endl;

    int index;

    output<<"SEARCHES :"<<endl;
    output<<SEPARATOR<<endl;
    STARTTIMER
    for (int i = 0; i < 1000; ++i)
    {
        index = ht->search(Q[i]);
        output<<index<<"|"<<ht->getString(index)<<"|"<<ht->getAmount(index)<<endl;

    }
    STOPTIMER
    output<<SEPARATOR<<endl;
    output<<"Total Search Time :"<<duration.count()<<MS<<endl<<endl<<endl<<endl;

    delete ht;

    output.close();

}





