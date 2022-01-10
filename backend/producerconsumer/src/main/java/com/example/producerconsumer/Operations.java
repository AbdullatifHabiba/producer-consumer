package com.example.producerconsumer;

import com.example.producerconsumer.SnapShot.CareTaker;
import com.example.producerconsumer.SnapShot.Momento;
import com.example.producerconsumer.SnapShot.Originator;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

public class Operations extends Observable {
    Originator originator = new Originator();
    CareTaker careTaker = new CareTaker();

    ArrayList<Queue> queues = new ArrayList<>();
    ArrayList<Machine> machines = new ArrayList<>();
    ArrayList<TreeNode> Tree = new ArrayList<>();
    ArrayList<Product> products = new ArrayList<>();
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

    void Connect(int Id1, int Id2){
        int node1 = GetNode(Id1);
        int node2 = GetNode(Id2);
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

    void ProduceProduct(Product product, int MachineId) {
        if (machines.get(GetMachine(MachineId)).isAvailable()) {
            machines.get(GetMachine(MachineId)).setColor(product.getColor());
            machines.get(GetMachine(MachineId)).setAvailable(false);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            machines.get(GetMachine(MachineId)).setColor("");
            machines.get(GetMachine(MachineId)).setAvailable(true);
            Queue queue = machines.get(GetMachine(MachineId)).getNext();
            queue.AddProduct(product);
            queues.set(GetQueue(queue.getId()), queue);
        }
    }

    void MoveProduct(int Id) {
        switch (Tree.get(GetNode(Id)).getType()) {
            case 'M':
                Machine machine = machines.get(GetMachine(Id));
                Stack<Product> products = machine.getPrev().getProducts();
                while (!products.isEmpty()) {
                    ProduceProduct(products.pop(), machine.getId());
                    products = machine.getPrev().getProducts();
                }
                break;
            case 'Q':
                for (int i = 0;i < queues.get(GetQueue(Id)).getNext().size();i++){
                    MoveProduct(queues.get(GetQueue(Id)).getNext().get(i).getId());
                }
                break;
            default:
                break;
        }
    }

    int GetProduct(String Color){
        for (int i = 0;i < products.size();i++){
            if (products.get(i).getColor().equalsIgnoreCase(Color))
                return i;
        }
        return -1;
    }
    void SaveMomento(){
        int[] positions = new int[products.size()];
        for (int i = 0;i < Tree.size();i++){
            switch (Tree.get(i).getType()){
                case 'M':
                    if (!machines.get(GetMachine(Tree.get(i).getId())).isAvailable())
                        positions[GetProduct(machines.get(GetMachine(Tree.get(i).getId())).getColor())] = Tree.get(i).getId();
                    break;
                case 'Q':
                    if (!queues.get(GetQueue(Tree.get(i).getId())).getProducts().isEmpty()){
                        for (int k = 0;k < queues.get(GetQueue(Tree.get(i).getId())).getProducts().size();k++){
                            positions[GetProduct(queues.get(GetQueue(Tree.get(i).getId())).getProducts().get(k).getColor())] = Tree.get(i).getId();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        String state = "" + positions[0];
        for (int i = 0;i < positions.length - 1;i ++)
            state = state + "," + positions[i + 1];
        originator.setState(state);
        careTaker.add(originator.saveStateToMomento());
    }
}
