package com.example.producerconsumer;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Test {
    public static void main(String[] args) {
        Operations O = new Operations();
        Product product1 = new Product(0, "red");
        Product product2 = new Product(1, "gray");
        Product product3 = new Product(2, "black");
        Product product4 = new Product(3, "blue");
        Queue<Product> products = new LinkedList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        ArrayList<Product> productArrayList = new ArrayList<>();
        productArrayList.add(product1);
        productArrayList.add(product2);
        productArrayList.add(product3);
        productArrayList.add(product4);
        O.setProducts(productArrayList);
        Point P1 = new Point(1, 2);
        O.AddNode(1, 'Q', P1);
        P1 = new Point(3, 2);
        O.AddNode(4, 'M', P1);
        O.Connect(1, 4);
        P1 = new Point(3, 12);
        O.AddNode(5, 'M', P1);
        O.Connect(1, 5);
        P1 = new Point(3, 6);
        O.AddNode(3, 'Q', P1);
        O.Connect(4, 3);
        O.Connect(5, 3);
        P1 = new Point(44, 6);
        O.AddNode(7, 'Q', P1);
        P1 = new Point(7, 7);
        O.AddNode(6, 'M', P1);
        O.Connect(3, 6);
        O.Connect(6, 7);
        P1 = new Point(2, 7);
        O.AddNode(2, 'M', P1);
        O.Connect(2, 3);
        O.Connect(1, 2);
        O.queues.get(O.GetQueue(1)).setProducts(products);
        System.out.println(O.machines.get(0).getPrev().getId());
        System.out.println(O.queues.get(0).getNext().get(0).getId());
        System.out.println(O.Tree.get(1).getParents().get(0).getType());
        Running N = new Running();
        N.machines = O.machines;
        N.queues = O.queues;
        N.products = O.products;
        Thread th = new Thread(N, "running");
        th.start();
        System.out.println(th.getName());
    }
}