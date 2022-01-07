package com.example.producerconsumer;

import java.util.ArrayList;

public class TreeNode {
    int Id;
    ArrayList<TreeNode> Sons;
    ArrayList<TreeNode> Parents;
    char Type;

    public TreeNode(int id, char type) {
        Id = id;
        Type = type;
    }

    public ArrayList<TreeNode> getSons() {
        return Sons;
    }

    public void setSons(ArrayList<TreeNode> sons) {
        Sons = sons;
    }

    public ArrayList<TreeNode> getParents() {
        return Parents;
    }

    public void setParents(ArrayList<TreeNode> parents) {
        Parents = parents;
    }

    public int getId() {
        return Id;
    }

    public char getType() {
        return Type;
    }

    public void AddSon(TreeNode Node){
        Sons.add(Node);
    }

    public void AddParent(TreeNode Node){
        Parents.add(Node);
    }

    void Connect(TreeNode Node1, TreeNode Node2){
        Node1.AddSon(Node2);
        Node2.AddParent(Node1);
    }
}
