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

    int GetQueue(int Id){
        for (int i = 0;i < queues.size();i++){
            if (queues.get(i).getId() == Id)
                return i;
        }
        return -1;
    }

    int GetMachine(int Id){
        for (int i = 0;i < machines.size();i++){
            if (machines.get(i).getId() == Id)
                return i;
        }
        return -1;
    }

    void Connect(TreeNode Node1, TreeNode Node2){
        Node1.AddSon(Node2);
        Node2.AddParent(Node1);
        if (Node1.getType() == 'Q'){
            Queue Q = queues.get(GetQueue(Node1.Id));
            Machine M = machines.get(GetMachine(Node2.Id));
            M.setPrev(Q);
            ArrayList<Machine> Next = Q.getNext();
            Next.add(M);
            Q.setNext(Next);
        }
        else if (Node1.getType() == 'M'){
            Queue Q = queues.get(GetQueue(Node2.Id));
            Machine M = machines.get(GetMachine(Node1.Id));
            M.setNext(Q);
            ArrayList<Machine> Previous = Q.getPrev();
            Previous.add(M);
            Q.setPrev(Previous);
        }
        else return;
    }
}
