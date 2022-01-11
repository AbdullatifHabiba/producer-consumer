package com.example.producerconsumer;

import com.example.producerconsumer.process.Producer;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Machine implements Runnable {
    private int Id;
    private boolean Available;
    private int min = 1000;
    private int max = 18000;
    private Point Position;
    private String Color;
    private Queue Prev;
    private Queue Next;

    public Machine(int id, Point position) {
        this.Id = id;
        this.Position = position;
        this.Available = true;
        this.min = 1000;
        this.max = 18000;
        this.Prev = new Queue(0, new Point(0, 0));
        this.Next = new Queue(0, new Point(0, 0));
        this.Color = "";
    }

    public int getId() {
        return this.Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public boolean isAvailable() {
        return this.Available;
    }

    public void setAvailable(boolean available) {
        this.Available = available;
    }

    public int getMin() {
        return this.min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Point getPosition() {
        return this.Position;
    }

    public void setPosition(Point position) {
        this.Position = position;
    }

    public String getColor() {
        return this.Color;
    }

    public void setColor(String color) {
        this.Color = color;
    }

    public Queue getPrev() {
        return this.Prev;
    }

    public void setPrev(Queue prev) {
        this.Prev = prev;
    }

    public Queue getNext() {
        return this.Next;
    }

    public void setNext(Queue next) {
        this.Next = next;
    }


    long intervalSimulation;
    Queue producer;
    Queue output ;
    Stack<Product> stack;
    boolean finalOutput = false;

    int number ;
    private BlockingQueue<Product > productsQueue;
    private volatile boolean keepProcessing;
    Machine(BlockingQueue<Product> products){
        this.productsQueue=products;
    }
    Machine(long time){
        this.intervalSimulation=time;
        System.out.println(intervalSimulation);
        //this.ending = ending;
    }
    public void setOutput(Queue producer){
        this.output=producer;
    }
    public void setProductsQueue(BlockingQueue<Product> productsQueue) {
        this.productsQueue = productsQueue;
    }
    public void setFinalOutput(Stack<Product> stack){
        this.stack=stack;
        finalOutput = true;
    }



    public synchronized void run() {
        System.out.println("the machine"+number);
        while (!productsQueue.isEmpty()) {
            try {
                //Available =false;
                Product temp = productsQueue.poll(1, TimeUnit.SECONDS);
                if (temp != null) {
                    Thread.sleep(intervalSimulation);

                    System.out.println(temp+"    "+producer.Id+" the machine" + number);
                    System.out.println("- -- - -- - - - - -- -");
                    if(output!=null){
                        this.output.sendToMachine(temp);
                    }
                    if (finalOutput){
                        this.stack.push(temp);
                    }
                }
            } catch (Exception e) {
                System.out.println("error in run in Machine");
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

        }
        notifyProducers();
        //this.cancelExecution();

    }
    public synchronized void notifyProducers(){
        this.Available =true;
    }

    public void setProducer(Queue producer){
        this.producer=producer;
    }
    public void cancelExecution()
    {
        this.keepProcessing = false;
    }
}
