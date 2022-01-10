package com.example.producerconsumer;

public class Product {
   public int Number;
    String color;
    public void process() throws InterruptedException {
        System.out.println("product");
        Thread.sleep(5000);
    }
}
