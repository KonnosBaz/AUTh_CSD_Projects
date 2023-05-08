#ifndef DSPROJECT2022BETTER_AVLTREE_H
#define DSPROJECT2022BETTER_AVLTREE_H

#include "AVL.h"

//This class acts as a high level abstraction for the concept of an AVL tree so the programmer can use it as a single entity rather than different nodes
//It does this by keeping track of the root node (which can change during insertions)

class AVLTree{
private:
    //The root node of the tree
    AVL* root;

public:
    //Constructor : initializes the root as nullptr
    AVLTree();
    //Destructor : calls the AVL::~AVL() function on the node, deleting all nodes of the tree
    ~AVLTree();

    //Calls the AVL::search() function on the root node
    //Returns a pointer to the node containing s
    //or nullptr if s doesn't belong in the tree
    AVL* search(std::string s);
    //If s belongs in the tree, its amount gets increased
    //Otherwise, a new node is created in the appropriate position, while making sure the tree stays balanced
    void insert(std::string s);

    //Calls the AVL::print() function on the root, using inline traversal to print all nodes in descending alphanumerical order
    void print();
};


#endif //DSPROJECT2022BETTER_AVLTREE_H
