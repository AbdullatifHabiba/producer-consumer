package com.example.producerconsumer;

public class Product {
    int Id;
    String color;

    public Product(int id, String color) {
        Id = id;
        this.color = color;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void process() throws InterruptedException {
        System.out.println("product");
        Thread.sleep(5000);
    }
}
