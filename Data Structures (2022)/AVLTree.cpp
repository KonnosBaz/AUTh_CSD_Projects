#include "AVLTree.h"

//Constructor
AVLTree::AVLTree()
{
    root = nullptr;
}

//Destructor
AVLTree::~AVLTree()
{
    delete root;
}




//Search
AVL *AVLTree::search(std::string s)
{
    AVL* whatever;

    if (root == nullptr)
        return nullptr;

    return root->search(s,whatever);
}

//Insert
void AVLTree::insert(std::string s)
{
    //if the tree is empty just add the root
    if (root == nullptr)
    {
        root = new AVL(nullptr,s);
        return;
    }

    AVL* last;
    AVL* sr = root->search(s,last);

    //if the string already exists increase the amount
    if (sr != nullptr)
    {
        sr->amount++;
        return;
    }

    //If the above aren't true...
    AVL* current;

    //create new node in the right place and keep track of it
    if (s>last->data)
    {
        last->right = new AVL(last,s);
        current = last->right;
    }
    else
    {
        last->left = new AVL(last,s);
        current = last->left;
    }
    //go up the ancestry of the new node and check for imbalance
    while(current != root)
    {
        current = current->parent;
        current->updateHeight();

        if(current->getBalanceFactor()>1)//right heavy
        {
            AVL* r = current->right;
            if (r->getBalanceFactor() == 1)//outside imbalance
            {
                current->rotateLeft();
                if (root == current)
                    root = current->parent;
            }
            else//inside imbalance
            {
                current->rotateRL();
                if (root == current)
                    root = current->parent;
            }
        }
        else if (current->getBalanceFactor()<-1)//left heavy
        {
            AVL* l = current->left;
            if (l->getBalanceFactor() == -1)//outside imbalance
            {
                current->rotateRight();
                if (root == current)
                    root = current->parent;
            }
            else//inside imbalance
            {
                current->rotateLR();
                if (root == current)
                    root = current->parent;
            }
        }
    }
}


//Print
void AVLTree::print()
{
    root->print();
}

