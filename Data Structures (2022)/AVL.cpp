#include "AVL.h"
#include <iostream>


//Constructor
AVL::AVL(AVL *parent, std::string data): parent(parent), left(nullptr), right(nullptr), data(data), amount(1), height(1) {}

//Destructor
AVL::~AVL()
{
    if (left!= nullptr)
        delete left;
    if (right!= nullptr)
        delete right;

}


//Utility Functions
int max(int a,int b)
{
    if (a>b)return a;
    else return b;
}

int AVL::getHeight()
{
    if (this == nullptr)
        return 0;
    else
        return height;
}

void AVL::updateHeight()
{
    this->height = max(left->getHeight(),right->getHeight()) + 1;
}

int AVL::getBalanceFactor()
{
    return right->getHeight() - left->getHeight();
}



//Rotations
void AVL::rotateRight()
{
    AVL* P = this->parent;
    AVL* y = this->left;
    AVL*B = y->right;

    this->left = B;
    if (B!= nullptr)
        B->parent = this;

    y->right = this;
    this->parent = y;

    if (P!= nullptr)
    {
        if (this == P->left)
            P->left = y;
        else
            P->right = y;
    }

    y->parent = P;

    updateHeight();
    y->updateHeight();

}

void AVL::rotateLeft()
{
    AVL* P = this->parent;
    AVL* y = this->right;
    AVL*B = y->left;

    this->right = B;
    if (B!= nullptr)
        B->parent = this;

    y->left = this;
    this->parent = y;

    if(P!= nullptr)
    {
        if (this == P->left)
            P->left = y;
        else
            P->right = y;
    }
    y->parent = P;

    updateHeight();
    y->updateHeight();
}

void AVL::rotateLR()
{
    AVL* y = this->left;

    y->rotateLeft();
    this->rotateRight();
}

void AVL::rotateRL()
{
    AVL* y = this->right;

    y->rotateRight();
    this->rotateLeft();
}



AVL *AVL::search(std::string s, AVL *&last)
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

//Print
void AVL::print()
{
    if (left!= nullptr)
        left->print();

    std::cout<<data<<"|"<<amount<<std::endl;

    if (right!= nullptr)
        right->print();
}

