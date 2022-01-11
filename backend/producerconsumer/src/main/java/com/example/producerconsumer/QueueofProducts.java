package com.example.producerconsumer;

import java.awt.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueofProducts implements Observer {
    int Id;
    int NumOfProducts;
    Queue<Product> Products;
    Point Position;
    ArrayList<Machine> Prev;
    ArrayList<Machine> Next;

    public QueueofProducts(int id, Point position) {
        Id = id;
        Position = position;
        NumOfProducts = 0;
        Products = new Queue<>();
        Prev = new ArrayList<>();
        Next = new ArrayList<>();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getNumOfProducts() {
        return NumOfProducts;
    }

    public void setNumOfProducts(int numOfProducts) {
        NumOfProducts = numOfProducts;
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

    public void setPrev(ArrayList<Machine> prev) {
        Prev = prev;
    }

    public ArrayList<Machine> getNext() {
        return Next;
    }

    public void setNext(ArrayList<Machine> next) {
        Next = next;
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
