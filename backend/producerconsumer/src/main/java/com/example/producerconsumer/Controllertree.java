package com.example.producerconsumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;


import java.awt.*;

@org.springframework.stereotype.Controller
public class Controllertree {
    JSONParser jsonParser = new JSONParser();
    Operations operations = new Operations();
    @MessageMapping  ("/action")
    @SendTo("/topic/posttree")
    String gettree( String tree) throws ParseException, JsonProcessingException, InterruptedException {
        System.out.println(tree);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode shapetree = objectMapper.readTree(tree);
        JSONParser parser = new JSONParser();
        System.out.println("links"+shapetree.get("links"));
        for(int i=0;i<5;i++){
            Thread.sleep(1000);
            System.out.println(tree);
            return "done";

        }

       /* JSONObject jsonObject = (JSONObject) parser.parse(tree);
        System.out.println(jsonObject);*/

        /*System.out.println("shapes"+jsonObject.get("shapes"));
        System.out.println("links"+jsonObject.get("links"));
        System.out.println("root"+jsonObject.get("root"));
        System.out.println("products"+jsonObject.get("products"));*/


        JSONArray edittree=new JSONArray();
        return tree;
    }
    @GetMapping("/addnode")
    void addNode(@RequestParam String node) throws ParseException {
        JSONObject jsonObject = (JSONObject) jsonParser.parse(node);
        operations.AddNode(Integer.parseInt(jsonObject.get("Id").toString()),
                jsonObject.get("Type").toString().charAt(0),
                new Point(Integer.parseInt(jsonObject.get("x").toString()), Integer.parseInt(jsonObject.get("y").toString())));
    }

    void connect(@RequestParam String node) throws ParseException {
        JSONObject jsonObject = (JSONObject) jsonParser.parse(node);

    }
}