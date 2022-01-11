package com.example.producerconsumer;

import java.awt.*;
import java.util.*;

public class QueueofProducts implements Observer {
    int Id;
    Stack<Product> Products;
    Point Position;
    ArrayList<Machine> Prev;
    ArrayList<Machine> Next;

    public QueueofProducts(int id, Point position) {
        Id = id;
        Position = position;
        Products = new Stack<>();
        Prev = new ArrayList<>();
        Next = new ArrayList<>();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Stack<Product> getProducts() {
        return Products;
    }

    public void setProducts(Stack<Product> products) {
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

    void AddProduct(Product product){
        Products.add(product);
    }

    void AddNext(Machine M){
        Next.add(M);
    }

    void AddPrev(Machine M){
        Prev.add(M);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
