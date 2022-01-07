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

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isAvailable() {
        return Available;
    }

    public void setAvailable(boolean available) {
        Available = available;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Point getPosition() {
        return Position;
    }

    public void setPosition(Point position) {
        Position = position;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public Queue getPrev() {
        return Prev;
    }

    public void setPrev(Queue prev) {
        Prev = prev;
    }

    public Queue getNext() {
        return Next;
    }

    public void setNext(Queue next) {
        Next = next;
    }
}
