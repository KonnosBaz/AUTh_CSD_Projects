#ifndef DSPROJECT2022BETTER_AVL_H
#define DSPROJECT2022BETTER_AVL_H

#include <string>

//This class represents the nodes of an AVLTree and implements their basic functions


class AVL {
    friend class AVLTree;
private:
    //These variables are self-explanatory
    AVL* parent;
    AVL* left;
    AVL* right;

    int height;

    std::string data;
    int amount;



    //These functions execute rotations on the nodes of the tree
    //For these to work properly they need to be called for the node that is signified with x in the lecture slides
    void rotateRight();
    void rotateLeft();
    void rotateLR();
    void rotateRL();

    //Utility functions
    //Self-explanatory
    void updateHeight();
    int getHeight();
    int getBalanceFactor();

    //Internal search function that uses the appropriate search algorithm for AVL trees
    //If the string s belongs in the tree, it returns a pointer to the node that contains it
    //Else, it returns nullptr, and the last variable's value, becomes a pointer to the node that should be the parent of a new node containing s
    AVL* search(std::string s, AVL *&last);
public:
    //Constructor : Sets the parent and data from its signature,
    //the amount as 1, the height as 1 and the left and right child nodes as nullptr
    AVL(AVL* parent, std::string data);
    //Destructor : Uses postorder traversal to delete all child nodes and then the node it is called for
    ~AVL();

    //Getters (self-explanatory)
    std::string getString(){return data;}
    int getAmount(){return amount;}

    //Inline printing function for debugging purposes
    void print();

};

#endif //DSPROJECT2022BETTER_AVL_H
