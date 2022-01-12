package com.example.producerconsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.*;

@Controller

public class Controllertree {
    static Operations operations = new Operations();
    private final SimpMessagingTemplate messagingTemplate;
    JSONParser jsonParser = new JSONParser();
    Running N = new Running();
    boolean flag = false;
    @Autowired
    private WebService webSocketService;
    @Autowired
    public Controllertree(final SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    //@SendTo("/topic/posttree")
    String gettree(String tree) throws ParseException, JsonProcessingException, InterruptedException {
        System.out.println(tree);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode shapetree = objectMapper.readTree(tree);
        JSONParser parser = new JSONParser();
        System.out.println("links" + shapetree.get("links"));
        for (int i = 0; i < 5; i++) {
            Thread.sleep(1000);
            System.out.println(tree);
            return "done";
        }
        JSONArray edittree = new JSONArray();
        return tree;
    }

    void addNode(String shapes, int Head) throws ParseException {
        JSONArray jsonArray = (JSONArray) jsonParser.parse(shapes);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            JSONObject PO = (JSONObject) jsonObject.get("position");
            operations.AddNode(Integer.parseInt(jsonObject.get("id").toString()),
                    jsonObject.get("shapeType").toString().replaceAll("rectangle", "Q").replaceAll("circle", "M").charAt(0),
                    new Point(Integer.parseInt(PO.get("x").toString()), Integer.parseInt(PO.get("y").toString())));
        }
        operations.setHeadNode(Head);

    }

    void connect(String links) throws ParseException {
        JSONArray jsonArray = (JSONArray) jsonParser.parse(links);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
            JSONObject jsonObject2 = (JSONObject) jsonObject1.get("sh2");
            jsonObject1 = (JSONObject) jsonObject1.get("sh1");
            operations.Connect(Integer.parseInt(jsonObject1.get("id").toString()), Integer.parseInt(jsonObject2.get("id").toString()));
        }
    }

    void addProducts(String products) throws ParseException {
        ArrayList<Product> productArrayList = new ArrayList<>();
        Queue<Product> productQueue = new LinkedList<>();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(products);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            Product product = new Product(Integer.parseInt(jsonObject.get("id").toString()), jsonObject.get("color").toString());
            productArrayList.add(product);
            productQueue.add(product);
        }
        operations.setProducts(productArrayList);
        Operations.queues.get(operations.getHeadNode()).setProducts(productQueue);
    }

    @MessageMapping("/action")
    public String start(String tree) throws JsonProcessingException, ParseException {
        System.out.println(tree);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode shapetree = objectMapper.readTree(tree);
        addNode(shapetree.get("shapes").toString(), shapetree.get("root").get("id").asInt());
        addProducts(shapetree.get("products").toString());
        connect(shapetree.get("links").toString());
        N.machines = Operations.machines;
        N.queues = Operations.queues;
        N.products = operations.products;
        System.out.println("MAchines num" + N.machines.size());
        System.out.println("que num" + N.queues.size());
        System.out.println("prod num" + N.products.size());
        System.out.println("prod num in q0 " + N.queues.get(0).getProductsNumber());
        System.out.println("prod num in q1  " + N.queues.get(1).getProductsNumber());
        JSONArray JS = operations.GetJSON(Operations.machines, Operations.queues);
        run();
        webSocketService.sendMessage("/topic/posttree", JS.toString());
        return "START";
    }

    void run() {
        Thread th = new Thread(N, "running");
        th.start();
        System.out.println(th.getName());
    }

    public void notifyFrontend(Machine M, QueueofProducts Q) {
        JSONArray JS = operations.GetJSON(Operations.machines, Operations.queues);
        System.out.println(JS);
        //messagingTemplate.convertAndSend("/topic/posttree",JS.toString());
    }
}
