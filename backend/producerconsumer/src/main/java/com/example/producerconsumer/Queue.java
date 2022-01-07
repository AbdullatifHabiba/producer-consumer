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

    void AddProduct(Product product){
        Products.add(product);
    }

}
