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
        Products = new Queue<>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Product> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Product product) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Product> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public boolean offer(Product product) {
                return false;
            }

            @Override
            public Product remove() {
                return null;
            }

            @Override
            public Product poll() {
                return null;
            }

            @Override
            public Product element() {
                return null;
            }

            @Override
            public Product peek() {
                return null;
            }
        };
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
