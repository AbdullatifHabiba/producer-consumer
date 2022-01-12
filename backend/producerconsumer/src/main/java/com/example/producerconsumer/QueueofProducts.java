package com.example.producerconsumer;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.awt.*;
import java.util.*;


public class QueueofProducts implements Iproducer {
    int Id;
    Point Position;
    ArrayList<Machine> Prev;
    ArrayList<Machine> Next;
    private Queue<Product> Products;
    private final SimpMessagingTemplate messagingTemplate = null;
    Controllertree controllertree = new Controllertree(messagingTemplate);

    public QueueofProducts(int id, Point position) {
        Id = id;
        Position = position;
        Products = new LinkedList<Product>();
        Prev = new ArrayList<>();
        Next = new ArrayList<>();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Queue<Product> getProducts() {
        return Products;
    }

    public void setProducts(Queue<Product> products) {
        Products = products;
    }

    public Point getPosition() {
        return Position;
    }

    public void setPosition(Point position) {
        Position = position;
    }

    public ArrayList<Machine> getPrev() {
        return Prev;
    }

    public ArrayList<Machine> getNext() {
        return Next;
    }

    void AddProduct(Product product) {
        Products.add(product);
    }

    void AddNext(Machine M) {
        Next.add(M);
    }

    void AddPrev(Machine M) {
        Prev.add(M);
    }

    public void updateMachines(Machine machine) {
        this.Next.remove(machine);
        if (machine.ready) {
            this.Next.add(0, machine);
        } else {
            this.Next.add(machine);
        }
    }

    public synchronized void addProductToQueue(Product product) {

        int index = -1;
        for (int i = 0; i < this.Next.size(); i++) {
            if (this.Next.get(i).ready) {
                this.Next.get(i).ready = false;
                index = i;
                break;
            }
        }
        if (index != -1) {

            Machine machine = this.Next.get(index);
            System.out.println("M" + machine.getId() + " on peak " + !machine.ready);
            produceProduct(machine);
            System.out.println(product.getId() + " " + product.getColor() + " added to " + "M" + machine.getId());
        }
    }

    public synchronized Product getProduct() {
        if (!Products.isEmpty()) {
            return Products.remove();
        } else {
            return null;
        }
    }

    public void produceProduct(Machine machine) {
        System.out.println("pr in q " + this.Products.size());
        machine.addProductToMachine(this.Products.remove(), this);
        controllertree.notifyFrontend(machine, this);
    }

    public int getProductsNumber() {
        return Products.size();
    }
}
