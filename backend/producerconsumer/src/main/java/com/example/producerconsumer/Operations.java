package com.example.producerconsumer;

import java.awt.*;
import java.util.ArrayList;

public class Operations {
    ArrayList<Queue> queues;
    ArrayList<Machine> machines;
    ArrayList<TreeNode> Tree;

    void AddNode(int Id, char Type, Point Position){
        Tree.add(new TreeNode(Id, Type));
        switch (Type){
            case 'Q':
                queues.add(new Queue(Id, Position));
                break;
            case 'M':
                machines.add(new Machine(Id, Position));
                break;
            default:
                break;
        }
    }

    void Connect(TreeNode Node1, TreeNode Node2){
        Node1.AddSon(Node2);
        Node2.AddParent(Node1);
    }

}
