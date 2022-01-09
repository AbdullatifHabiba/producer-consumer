package com.example.producerconsumer;

import java.awt.*;
import java.util.ArrayList;

public class Operations {
    ArrayList<Queue> queues = new ArrayList<>();
    ArrayList<Machine> machines = new ArrayList<>();
    ArrayList<TreeNode> Tree = new ArrayList<>();
    int HeadNode;

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

    public int getHeadNode() {
        return HeadNode;
    }

    public void setHeadNode(int headNode) {
        HeadNode = headNode;
    }

    int GetNode(int Id){
        for (int i = 0;i < Tree.size();i++){
            if (Tree.get(i).getId() == Id)
                return i;
        }
        return -1;
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

    void Connect(int N1, int N2){
        int node1 = GetNode(N1);
        int node2 = GetNode(N2);
        TreeNode Node1 = Tree.get(node1);
        TreeNode Node2 = Tree.get(node2);
        Node1.AddSon(Node2);
        Node2.AddParent(Node1);
        Tree.set(node1, Node1);
        Tree.set(node2, Node2);
        if (Node1.getType() == 'Q'){
            Queue Q = queues.get(GetQueue(Node1.Id));
            Machine M = machines.get(GetMachine(Node2.Id));
            M.setPrev(Q);
            Q.AddNext(M);
        }
        else if (Node1.getType() == 'M'){
            Queue Q = queues.get(GetQueue(Node2.Id));
            Machine M = machines.get(GetMachine(Node1.Id));
            M.setNext(Q);
            Q.AddPrev(M);
        }
        else return;
    }
}
