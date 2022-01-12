package com.example.producerconsumer;

import com.example.producerconsumer.SnapShot.CareTaker;
import com.example.producerconsumer.SnapShot.Originator;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
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
    int i = 0;
    private final ExecutorService executorService =
            Executors.newCachedThreadPool();

    public void setReplay(boolean replay) {
        this.replay = replay;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    int GetNode(int Id) {
        for (int i = 0; i < Tree.size(); i++) {
            if (Tree.get(i).getId() == Id)
                return i;
        }
        return -1;
    }

    int GetMachine(int Id) {
        for (int i = 0; i < machines.size(); i++) {
            if (machines.get(i).getId() == Id)
                return i;
        }
        return -1;
    }

    int GetQueue(int Id) {
        for (int i = 0; i < queues.size(); i++) {
            if (queues.get(i).getId() == Id)
                return i;
        }
        return -1;
    }

    synchronized void ProduceProduct(Product product, int MachineId) {
        if (machines.get(GetMachine(MachineId)).isAvailable()) {
            machines.get(GetMachine(MachineId)).setColor(product.getColor());
            machines.get(GetMachine(MachineId)).setAvailable(false);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();

                Thread.currentThread().interrupt();
            }
        }
        SaveMomento();
        machines.get(GetMachine(MachineId)).setColor("white");
        machines.get(GetMachine(MachineId)).setAvailable(true);
        QueueofProducts queue = machines.get(GetMachine(MachineId)).getNext();
        queue.AddProduct(product);
        queues.set(GetQueue(queue.getId()), queue);
        SaveMomento();
    }

    synchronized void MoveProduct(int Id) {
        switch (Tree.get(GetNode(Id)).getType()) {
            case 'M':
                Machine machine = machines.get(GetMachine(Id));
                Queue<Product> products = machine.getPrev().getProducts();
                BlockingQueue<Product> productsQueue;

                while (!products.isEmpty()) {
                    Product P = products.poll();

                    System.out.println("P" + P.Id + " m" + machine.getId());
                    ProduceProduct(products.poll(), machine.getId());
                    products = machine.getPrev().getProducts();
                }
                break;
            case 'Q':
                for (int i = 0; i < queues.get(GetQueue(Id)).getNext().size(); i++) {
                    MoveProduct(queues.get(GetQueue(Id)).getNext().get(i).getId());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
        SaveMomento();
    }

    int GetProduct(String Color) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getColor().equalsIgnoreCase(Color))
                return i;
        }
        return -1;
    }

    void SaveMomento() {
        int[] positions = new int[products.size()];
        for (int i = 0; i < Tree.size(); i++) {
            switch (Tree.get(i).getType()) {
                case 'M':
                    if (!machines.get(GetMachine(Tree.get(i).getId())).isAvailable())
                        positions[GetProduct(machines.get(GetMachine(Tree.get(i).getId())).getColor())] = Tree.get(i).getId();
                    break;
                case 'Q':
                    if (!queues.get(GetQueue(Tree.get(i).getId())).getProducts().isEmpty()) {
                        for (int k = 0; k < queues.get(GetQueue(Tree.get(i).getId())).getProducts().size(); k++) {
                            positions[GetProduct(queues.get(GetQueue(Tree.get(i).getId())).getProducts().poll().getColor())] = Tree.get(i).getId();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        String state = "" + positions[0];
        for (int i = 0; i < positions.length - 1; i++)
            state = state + "," + positions[i + 1];
        System.out.println(state);
        originator.setState(state);
        careTaker.add(originator.saveStateToMomento());
    }

    private void runAssemblyLine() {
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

    // a function to input the products inside the input queue (and display them in the list) at the specified input rate
    private void inputProductsToQueue(int ind) {
        QueueofProducts inputQueue = queues.get(ind);
        Runnable inputProductsRun = () -> {
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                System.out.println(product.getId() + "" + product.getColor());
                inputQueue.addProductToQueue(product);
                try {
                    //  controller.updateInitialProducts(product);
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
            while (queues.get(queues.size() - 2).getProducts().size() < products.size()) {
                this.run();
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
        this.runAssemblyLine();
        //originator.setState(savedState);
        // careTaker.add(originator.saveStateToMemento());
        this.inputProductsToQueue(i);
        this.waitSimulation();
    }


}
