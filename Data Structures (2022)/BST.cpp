#include "BST.h"
#include <iostream>

//Root Constructor
BST::BST():left(nullptr),right(nullptr),data("root"),amount(0) {}

//Child Constructor
BST::BST(std::string data): data(data),left(nullptr),right(nullptr),amount(1) {}

//Destructor
BST::~BST()
{
    if (this->left!= nullptr)
    {
        delete left;
    }
    if (this->right!= nullptr)
    {
        delete right;
    }
}


//Search functions
BST *BST::search(std::string s)
{
    BST* whatever;
    return search(s,whatever);
}

BST *BST::search(std::string s, BST *&last)
{
    if (this->data == s)
    {
        last = nullptr;
        return this;
    }
    if (s < this->data)
    {
        if (this->left == nullptr)
        {
            last = this;
            return nullptr;
        }

        return left->search(s,last);
    }
    else
    {
        if(this->right == nullptr)
        {
            last = this;
            return nullptr;
        }

        return right->search(s,last);
    }
}

//Insert
void BST::insert(std::string s)
{
    BST* last;
    BST* sr = this->search(s,last);

    if (sr == nullptr)
    {
        if (s>last->getString())
        {
            last->right = new BST(s);
        }
        else
        {
            last->left = new BST(s);
        }
    }
    else
    {
        sr->amount++;
    }
}


//print
void BST::print()
{
    if (this->left!= nullptr)
    {
        left->print();
    }
    if (this->data != "root")
        std::cout<<this->data<<"|"<< this->amount<<std::endl;
    if (this->right!= nullptr)
    {
        right->print();
    }
}

