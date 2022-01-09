package com.example.producerconsumer;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Machine implements Observer {
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

    @Override
    public void update(Observable o, Object arg) {

    }
}
