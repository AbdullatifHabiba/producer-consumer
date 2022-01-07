package com.example.producerconsumer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class Queue {
    int Id;
    int NumOfProducts;
    Stack<Product> Products;
    Point Position;
    ArrayList<Machine> Prev;
    ArrayList<Machine> Next;

    public Queue(int id, Point position) {
        Id = id;
        Position = position;
        NumOfProducts = 0;
        Products = new Stack<>();
        Prev = null;
        Next = null;
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

}
