package com.example.producerconsumer;

import com.example.producerconsumer.process.Pmachine;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

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


    private BlockingQueue<Product> ProductsQueue =
            new LinkedBlockingQueue< Product >();
    private ExecutorService executorService =
            Executors.newCachedThreadPool();
    private List<Machine> Machines =
            new LinkedList< Machine >();
    private volatile boolean shutdownCalled = false;



    public void addMachine(Machine machine){
        Machines.add(machine);
    }


    public synchronized boolean sendToMachine(Product product)
    {try {
        Thread.sleep(10);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
        if(!shutdownCalled)
        {
            try
            {
                ProductsQueue.put(product);

                int index =-1;
                for(int i = 0 ; i < Machines.size();i++){
                    if(Machines.get(i).isAvailable()){
                        Machines.get(i).setAvailable(false);
                        index=i;

                        break;
                    }
                }
                if(index!=-1){
                    //Machines.get(index).Available = false;
                    Machines.get(index).setProducer(this);
                    Machines.get(index).setProductsQueue(this.ProductsQueue);
                    Machines.get(index).number=index;
                    executorService.execute(Machines.get(index));}
            }
            catch(InterruptedException ie)
            {
                Thread.currentThread().interrupt();
                return false;
            }
            return true;
        }
        else
        {
            return false;
        }
    }
    public void finishConsumption()
    {
        for(Machine machine : Machines)
        {
            machine.cancelExecution();
        }

        executorService.shutdown();
    }

}
