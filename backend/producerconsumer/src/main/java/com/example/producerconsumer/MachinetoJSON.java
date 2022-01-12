package com.example.producerconsumer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class MachinetoJSON {
    ArrayList<Machine> machines = new ArrayList<>();

    public MachinetoJSON(ArrayList<Machine> machines) {
        this.machines = machines;
    }

    JSONArray GetJSON() {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < machines.size(); i++) {
            jsonObject.put("id", machines.get(i).getId());
            jsonObject.put("color", machines.get(i).getColor());
            jsonObject.put("next", machines.get(i).getNext().getId());
            jsonObject.put("prev", machines.get(i).getPrev().getId());
            JSONObject JO = new JSONObject();
            JO.put("x", machines.get(i).getPosition().getX());
            JO.put("y", machines.get(i).getPosition().getY());
            jsonObject.put("position", JO.toString());
        }
        return jsonArray;
    }
}
