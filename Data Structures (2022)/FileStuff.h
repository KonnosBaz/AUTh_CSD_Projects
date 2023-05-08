#ifndef DSPROJECT2022BETTER_FILESTUFF_H
#define DSPROJECT2022BETTER_FILESTUFF_H

#define ft "formattedText.txt"

#include <iostream>
#include <fstream>
#include <string>

//This file contains the functions used to interact with the file system



//Takes a file and creates a copy with no punctuation and all lowercase letters
//New file's name is "formattedText.txt", defined as ft in this file
//NEEDS TO BE FIXED IN ORDER TO WORK WITH NON-ASCII
void formatFile(std::string filename)
{
    std::ifstream infile(filename);

    if (!infile)
    {
        std::cerr << "couldn't open file " << filename << std::endl;
        return;
    }

    std::ofstream outfile(ft);

    char c;

    while(true)
    {
        infile.get(c);

        if(infile.eof())break;

        if (c == ' ' || ispunct(c))
        {
            outfile.put(' ');
        }
        else
        {
            outfile.put(tolower(c));
        }
    }

    infile.close();
    outfile.close();
}



//Counts the strings in the given file
//Returns the word count or -1 if the file doesn't exist
int countWords(std::string filename)
{
    std::ifstream file(filename);

    if (!file)
    {
        std::cerr << "couldn't open file " << filename << std::endl;
        return -1;
    }


    std::string s;
    int wordCount = 0;

    std::cout<<"counting words in: "<< filename <<std::endl;

    while (true)
    {
        file>>s;
        if (file.eof())break;
        wordCount ++;

        if (wordCount%1000000 == 0)
            std::cout<<wordCount/1000000<<" million words"<<std::endl;
    }

    file.close();

    std::cout<<"total words: "<<wordCount<<std::endl;

    return wordCount;
}


//Loads word pairs from the given file into the given structure (provided it implements the insert() function)
template<class tclass> void loadInto(tclass structure,std::string filename,int wc)
{
    std::ifstream file(filename);

    if (!file)
    {
        std::cerr << "couldn't open file " << filename << std::endl;
        return;
    }

    std::string prev,curr;

    file>>prev;

    int done = 0;
    while(true)
    {
        file>>curr;

        if(file.eof())break;

        structure->insert(prev + " " + curr);
        done ++;

        if (done%10000 == 0 )
            std::cout<<done<<"/"<<wc<<std::endl;

        prev = curr;
    }

    file.close();
}



#endif //DSPROJECT2022BETTER_FILESTUFF_H
