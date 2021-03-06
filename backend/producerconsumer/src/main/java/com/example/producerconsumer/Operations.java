package com.example.producerconsumer;

import com.example.producerconsumer.SnapShot.CareTaker;
import com.example.producerconsumer.SnapShot.Originator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class Operations extends Observable {
    static ArrayList<QueueofProducts> queues = new ArrayList<>();
    static ArrayList<Machine> machines = new ArrayList<>();
    Originator originator = new Originator();
    CareTaker careTaker = new CareTaker();
    ArrayList<TreeNode> Tree = new ArrayList<>();
    ArrayList<Product> products = new ArrayList<>();
    int HeadNode;
    public ArrayList<Product> getProducts() {
        return products;
    }
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    void AddNode(int Id, char Type, Point Position) {
        Tree.add(new TreeNode(Id, Type));
        switch (Type) {
            case 'Q':
                queues.add(new QueueofProducts(Id, Position));
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

    int GetNode(int Id) {
        for (int i = 0; i < Tree.size(); i++) {
            if (Tree.get(i).getId() == Id)
                return i;
        }
        return -1;
    }

    int GetQueue(int Id) {
        for (int i = 0; i < queues.size(); i++) {
            if (queues.get(i).getId() == Id)
                return i;
        }
        return -1;
    }

    int GetMachine(int Id) {
        for (int i = 0; i < machines.size(); i++) {
            if (machines.get(i).getId() == Id)
                return i;
        }
        return -1;
    }

    void Connect(int Id1, int Id2) {
        int node1 = GetNode(Id1);
        int node2 = GetNode(Id2);
        TreeNode Node1 = Tree.get(node1);
        TreeNode Node2 = Tree.get(node2);
        Node1.AddSon(Node2);
        Node2.AddParent(Node1);
        Tree.set(node1, Node1);
        Tree.set(node2, Node2);
        if (Node1.getType() == 'Q') {
            QueueofProducts Q = queues.get(GetQueue(Node1.Id));
            Machine M = machines.get(GetMachine(Node2.Id));
            M.setPrev(Q);
            Q.AddNext(M);
        } else if (Node1.getType() == 'M') {
            QueueofProducts Q = queues.get(GetQueue(Node2.Id));
            Machine M = machines.get(GetMachine(Node1.Id));
            M.setNext(Q);
            Q.AddPrev(M);
        } else return;
    }

    public JSONArray GetJSON(ArrayList<Machine> machines, ArrayList<QueueofProducts> queues) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < queues.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", queues.get(i).getId());
            jsonObject.put("isselected", false);
            jsonObject.put("shapetype", "rectangle");
            jsonObject.put("width", 120);
            jsonObject.put("height", 60);
            jsonObject.put("color", "white");
            JSONObject JO = new JSONObject();
            JO.put("x", queues.get(i).getPosition().getX());
            JO.put("y", queues.get(i).getPosition().getY());
            jsonObject.put("position", JO);
            jsonArray.add(jsonObject);
        }
        for (int i = 0; i < machines.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", machines.get(i).getId());
            jsonObject.put("isselected", false);
            jsonObject.put("shapetype", "circle");
            jsonObject.put("radius", 50);
            jsonObject.put("color", machines.get(i).getColor());
            JSONObject JO = new JSONObject();
            JO.put("x", machines.get(i).getPosition().getX());
            JO.put("y", machines.get(i).getPosition().getY());
            jsonObject.put("position", JO);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    synchronized void ProduceProduct(Product product, int MachineId) {
        if (machines.get(GetMachine(MachineId)).isAvailable()) {
            machines.get(GetMachine(MachineId)).setColor(product.getColor());
            machines.get(GetMachine(MachineId)).setAvailable(false);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        SaveMomento();
        machines.get(GetMachine(MachineId)).setColor("white");
        machines.get(GetMachine(MachineId)).setAvailable(true);
        QueueofProducts queue = machines.get(GetMachine(MachineId)).getNext();
        queue.AddProduct(product);
        queues.set(GetQueue(queue.getId()), queue);
        SaveMomento();
    }

    synchronized void MoveProduct(int Id) {
        switch (Tree.get(GetNode(Id)).getType()) {
            case 'M':
                Machine machine = machines.get(GetMachine(Id));
                Queue<Product> products = machine.getPrev().getProducts();
                BlockingQueue<Product> productsQueue;
                while (!products.isEmpty()) {
                    Product P = products.poll();
                    System.out.println("P" + P.Id + " m" + machine.getId());
                    ProduceProduct(products.poll(), machine.getId());
                    products = machine.getPrev().getProducts();
                }
                break;
            case 'Q':
                for (int i = 0; i < queues.get(GetQueue(Id)).getNext().size(); i++) {
                    MoveProduct(queues.get(GetQueue(Id)).getNext().get(i).getId());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
        SaveMomento();
    }

    int GetProduct(String Color) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getColor().equalsIgnoreCase(Color))
                return i;
        }
        return -1;
    }

    void SaveMomento() {
        int[] positions = new int[products.size()];
        for (int i = 0; i < Tree.size(); i++) {
            switch (Tree.get(i).getType()) {
                case 'M':
                    if (!machines.get(GetMachine(Tree.get(i).getId())).isAvailable())
                        positions[GetProduct(machines.get(GetMachine(Tree.get(i).getId())).getColor())] = Tree.get(i).getId();
                    break;
                case 'Q':
                    if (!queues.get(GetQueue(Tree.get(i).getId())).getProducts().isEmpty()) {
                        for (int k = 0; k < queues.get(GetQueue(Tree.get(i).getId())).getProducts().size(); k++) {
                            positions[GetProduct(queues.get(GetQueue(Tree.get(i).getId())).getProducts().poll().getColor())] = Tree.get(i).getId();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        String state = "" + positions[0];
        for (int i = 0; i < positions.length - 1; i++)
            state = state + "," + positions[i + 1];
        System.out.println(state);
        originator.setState(state);
        careTaker.add(originator.saveStateToMomento());
    }
}
