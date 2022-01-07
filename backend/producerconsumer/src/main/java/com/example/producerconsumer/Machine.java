package com.example.producerconsumer;

import java.awt.*;

public class Machine {
    int Id;
    boolean Available;
    int min = 1000;
    int max = 18000;
    Point Position;
    String Color;
    Queue Prev;
    Queue Next;

    public Machine(int id, Point position) {
        Id = id;
        Position = position;
        Available = true;
        Color = null;
        Prev = null;
        Next = null;
    }

}
