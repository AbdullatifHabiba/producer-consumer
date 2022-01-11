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
    private QueueofProducts Prev;
    private QueueofProducts Next;

    public Machine(int id, Point position) {
        this.Id = id;
        this.Position = position;
        this.Available = true;
        this.min = 1000;
        this.max = 18000;
        this.Prev = new QueueofProducts(0, new Point(0, 0));
        this.Next = new QueueofProducts(0, new Point(0, 0));
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
    public void update(Observable o, Object arg) {

    }
}
