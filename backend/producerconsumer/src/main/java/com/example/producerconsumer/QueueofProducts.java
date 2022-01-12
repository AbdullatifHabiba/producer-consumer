package com.example.producerconsumer;

import java.awt.*;
import java.util.*;

public class QueueofProducts  {
    int Id;
    private Queue<Product> Products;

    Point Position;
    ArrayList<Machine> Prev;
    ArrayList<Machine> Next;

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

    void AddProduct(Product product){
        Products.add(product);
    }

    void AddNext(Machine M){
        Next.add(M);
    }

    void AddPrev(Machine M){
        Prev.add(M);
    }

    public  void updateMachines(Machine machine){
        this.Next.remove(machine);
        if(machine.ready){
            this.Next.add(0,machine);
        }else{
            this.Next.add(this.Next.size()-1,machine);
        }
    }
    public synchronized void addProductToQueue(Product product) {
        this.Products.add(product);
        /*if( Id == 0 ){
            QueueShape newShape = queueShape.clone();
            newShape.setProductsInQ(getProductsNumber());
            notifyUI( newShape );
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/


        System.out.println(product.getId() + " " + product.getColor() + " added to " + "Q" + Id);
        if( this.Next.size() != 0 ){
            Machine machine = this.Next.get(0);

            System.out.println("M" + machine.getId() +" on peak " + machine.ready);
            if( machine.ready ){
                produceProduct(machine);
                System.out.println(product.getId() + " " + product.getColor() + " added to " + "M" + machine.getId());
            }else {
                //this.updateMachines();
            }
        }
    }

    public synchronized Product getProduct(){
        if(!Products.isEmpty()){
            return Products.remove();
        }else {
            return null;
        }
    }

    public void produceProduct(Machine machine){
        machine.addProductToMachine(this.Products.remove(),this);
    }

    public int getProductsNumber(){
        return Products.size();
    }


}
