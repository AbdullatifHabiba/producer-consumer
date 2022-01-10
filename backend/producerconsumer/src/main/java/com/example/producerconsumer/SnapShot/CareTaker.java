package com.example.producerconsumer.SnapShot;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
    private List<Momento> momentoList = new ArrayList<Momento>();

    public void add(Momento state){
        momentoList.add(state);
    }

    public Momento get(int index) {
        return momentoList.get(index);
    }
}
