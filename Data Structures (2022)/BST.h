#ifndef DSPROJECT2022BETTER_BST_H
#define DSPROJECT2022BETTER_BST_H

#include <string>

//This class represents the nodes of a Binary Search Tree as well as the tree as a whole
//The programmer controls it by creating the root node and calling functions on it, while internally more nodes are created


class BST {
private:
    //Self-explanatory variables
    BST* left;
    BST* right;

    std::string data;
    int amount;

    //Child Constructor (this is only used internally)
    //Sets the data as data, amount as 1, left and right child nodes as nullptr
    BST(std::string data);

    //Internal search function that uses the appropriate search algorithm for AVL trees
    //If the string s belongs in the tree, it returns a pointer to the node that contains it
    //Else, it returns nullptr, and the last variable's value, becomes a pointer to the node that should be the parent of a new node containing s
    BST* search(std::string s,BST*& last);
public:
    //Root Constructor (This is what is used by the programmer)
    //Sets the data as "root" and amount as 0, these are arbitrary values since the root node is essentially an interface for the rest of the tree and disregarded in most functions
    BST();
    //Uses post-order traversal to delete all nodes of the tree
    ~BST();

    //External search function : does the same as the above search function, but disregards the last variable
    BST* search(std::string s);
    //If f already belongs in the tree, its amount gets increased
    //Else, the right position is found and a new node containing s is created with amount 1
    void insert(std::string s);

    //Getters (self-explanatory)
    std::string getString() {return this->data;}
    int getAmount() {return this->amount;}

    //Inline printing function for debugging purposes
    void print();



};


#endif //DSPROJECT2022BETTER_BST_H
