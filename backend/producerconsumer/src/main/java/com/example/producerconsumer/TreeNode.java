package com.example.producerconsumer;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class TreeNode implements Observer {
    int Id;
    ArrayList<TreeNode> Sons;
    ArrayList<TreeNode> Parents;
    char Type;

    public TreeNode(int id, char type) {
        Id = id;
        Type = type;
        Sons = new ArrayList<>();
        Parents = new ArrayList<>();
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

    public void AddSon(TreeNode Node) {
        Sons.add(Node);
    }

    public void AddParent(TreeNode Node) {
        Parents.add(Node);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
