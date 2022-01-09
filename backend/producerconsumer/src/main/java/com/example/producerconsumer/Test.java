package com.example.producerconsumer;

import java.awt.*;

public class Test {
    public static void main(String[] args) {
        Operations O = new Operations();
        Point P1 = new Point(1, 2);
        O.AddNode(1, 'Q', P1);
        P1 = new Point(3,4);
        O.AddNode(2, 'M', P1);
        O.Connect(1, 2);
        System.out.println(O.machines.get(0).getPrev().getId());
        System.out.println(O.queues.get(0).getNext().get(0).getId());
        System.out.println(O.Tree.get(1).getParents().get(0).getType());

    }
}
