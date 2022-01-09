package com.example.producerconsumer;

import java.util.ArrayList;
import java.util.Stack;

public class Process {
    ArrayList<Product> products;
    Operations O = new Operations();

    void MoveProduct(int Id){
        switch (O.Tree.get(O.GetNode(Id)).getType()){
            case 'M':
                Machine machine = O.machines.get(O.GetMachine(Id));
                while (! machine.getPrev().getProducts().isEmpty()){
                    Stack<Product> products = machine.getPrev().getProducts();

                }
                break;
            default:
                break;
        }
    }
}
