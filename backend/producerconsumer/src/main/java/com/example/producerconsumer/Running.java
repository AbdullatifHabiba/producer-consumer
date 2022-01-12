package com.example.producerconsumer;

import com.example.producerconsumer.SnapShot.CareTaker;
import com.example.producerconsumer.SnapShot.Originator;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Running implements Runnable {
    boolean start;
    boolean replay;
    Originator originator = new Originator();
    CareTaker careTaker = new CareTaker();
    ArrayList<QueueofProducts> queues = new ArrayList<>();
    ArrayList<Machine> machines = new ArrayList<>();
    ArrayList<TreeNode> Tree = new ArrayList<>();
    ArrayList<Product> products = new ArrayList<>();
    int HeadNode;
    Operations operations = new Operations();
    int i = 0;
    private final ExecutorService executorService =
            Executors.newCachedThreadPool();

    public void setReplay(boolean replay) {
        this.replay = replay;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    private void runmachine() {
        ArrayList<QueueofProducts> queues = this.queues;
        ArrayList<Machine> machines = this.machines;
        int[] time;
        if (replay) {
            time = new int[]{1, 2};
        } else {
            time = new int[machines.size()];
        }
        for (int i = 0; i < machines.size(); i++) {
            if (this.replay) {
                machines.get(i).setMax(time[i]);
            } else {
                time[i] = machines.get(i).getMax();
            }
            System.out.println(machines.get(i).getId() + "MM" + machines.get(i).getPrev().getProducts().peek());
            System.out.println(machines.get(i).getId() + ",s:" + machines.get(i).ready);
            if (machines.get(i).ready && machines.get(i).getPrev().getProductsNumber() > 0) {
                machines.get(i).start();
            }
        }
        originator.setState(time.toString());
    }

    private void inputProductsToQueue(int ind) {
        QueueofProducts inputQueue = queues.get(ind);
        Runnable inputProductsRun = () -> {
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                System.out.println(product.getId() + "" + product.getColor());
                inputQueue.addProductToQueue(product);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread inputThread = new Thread(inputProductsRun, "input thread");
        inputThread.start();
    }

    private synchronized void waitSimulation() {
        Runnable waitSimulationRun = () -> {
            while (queues.get(queues.size() - 1).getProducts().size() < products.size()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            terminate(machines);
        };
        Thread waitSimulationThread = new Thread(waitSimulationRun, "waiting thread");
        waitSimulationThread.start();
    }

    private void terminate(ArrayList<Machine> machines) {
        for (int i = 0; i < machines.size(); i++) {
            machines.get(i).shut();
        }
    }

    @Override
    public synchronized void run() {
        System.out.println("START");
        this.runmachine();
        this.inputProductsToQueue(i);
        this.waitSimulation();
    }
}
