package com.example.producerconsumer;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Machine implements Runnable, Iconsumer {

    boolean ready = true;
    private int Id;
    private boolean Available;
    private int min = 1000;
    private int max = 18000;
    private Point Position;
    private String Color;
    private QueueofProducts Prev;
    private QueueofProducts Next;
    private final Thread thread;
    private volatile boolean run = true;
    private volatile Product currentProduct;
    private final ExecutorService executorService =
            Executors.newCachedThreadPool();
    private final SimpMessagingTemplate messagingTemplate = null;
    Controllertree controllertree = new Controllertree(messagingTemplate);

    public Machine(int id, Point position) {
        this.Id = id;
        this.Position = position;
        this.Available = true;
        this.min = 1000;
        this.max = 18000;
        this.Prev = new QueueofProducts(0, new Point(0, 0));
        this.Next = new QueueofProducts(0, new Point(0, 0));
        this.Color = "";
        thread = new Thread(this, "M" + id);
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

    public QueueofProducts getPrev() {
        return this.Prev;
    }

    public void setPrev(QueueofProducts prev) {
        this.Prev = prev;
    }

    public QueueofProducts getNext() {
        return this.Next;
    }

    public void setNext(QueueofProducts next) {
        this.Next = next;
    }

    @Override
    public synchronized void addProductToMachine(Product currentProduct, QueueofProducts queue) {
        this.currentProduct = currentProduct;
        this.setBusyState();
        notify();
    }

    public void start() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.thread.start();
        controllertree.notifyFrontend(this, this.getPrev());

        System.out.println(thread.getName() + " started");
        System.out.println("machine time : " + this.max);
    }

    public void run() {
        synchronized (this) {
            while (!this.thread.isInterrupted()) {
                while (this.currentProduct == null) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                if (!this.run) {
                    break;
                }
                this.consumeProduct();
                this.passProductToQueue();
                this.checkWaitingProducts();
                System.out.println(this.getColor() + " : " + thread.getName());
            }
        }

    }

    public synchronized void shut() {
        synchronized (this.thread) {
            this.run = false;
            controllertree.notifyFrontend(this, this.getPrev());
            System.out.println(this.thread.getName() + " is terminated");
            this.thread.interrupt();
        }
    }

    private void checkWaitingProducts() {
        Boolean productFound = false;
        for (int i = 0; i < Prev.getProductsNumber(); i++) {
            Product product = Prev.getProduct();
            if (product != null) {
                System.out.println(Prev.getProducts().size() + " products waiting in Q" + Prev.getId());
                this.currentProduct = product;
                this.setColor(currentProduct.getColor());
                productFound = true;
                break;
            }
        }
        if (!productFound) {
            this.setReadyState();
        }
    }

    private void setBusyState() {
        this.ready = false;
        this.setColor(currentProduct.getColor());
        this.notifyInputQueues();
    }

    public void consumeProduct() {
        controllertree.notifyFrontend(this, this.getPrev());
        System.out.println(this.getColor() + " processed in " + thread.getName());
        try {
            Thread.sleep(this.max);
        } catch (Exception e) {
        }
    }

    private void setReadyState() {
        this.ready = true;
        this.setColor("White");
        this.notifyInputQueues();
    }


    private void passProductToQueue() {
        this.Next.AddProduct(this.currentProduct);
        executorService.execute(this.Next.getNext().get(0));
        System.out.println("Q" + this.Next.getId());
        System.out.println(this.Next.getProducts());
        currentProduct = null;
    }

    private void notifyInputQueues() {
        Prev.updateMachines(this);
        Next.updateMachines(this);
    }
}
