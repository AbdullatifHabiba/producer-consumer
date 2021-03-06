package com.example.producerconsumer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/action")
public class Controller {
    JSONParser jsonParser = new JSONParser();
    Operations operations = new Operations();

    @GetMapping("/addnodes")
    void addNode(@RequestParam String node) throws ParseException {
        JSONArray jsonArray = (JSONArray) jsonParser.parse(node);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            operations.AddNode(Integer.parseInt(jsonObject.get("Id").toString()),
                    jsonObject.get("Type").toString().replaceAll("rectangle", "Q").replaceAll("circle", "M").charAt(0),
                    new Point(Integer.parseInt(jsonObject.get("x").toString()), Integer.parseInt(jsonObject.get("y").toString())));
        }
    }

    @GetMapping("/connect")
    void connect(@RequestParam String node) throws ParseException {
        JSONArray jsonArray = (JSONArray) jsonParser.parse(node);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
            JSONObject jsonObject2 = (JSONObject) jsonObject1.get("sh2");
            jsonObject1 = (JSONObject) jsonObject1.get("sh1");
            operations.Connect(Integer.parseInt(jsonObject1.get("id").toString()), Integer.parseInt(jsonObject2.get("id").toString()));
        }
    }

    @GetMapping("/addproducts")
    void addProducts(@RequestParam String products) throws ParseException {
        ArrayList<Product> productArrayList = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(products);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            Product product = new Product(Integer.parseInt(jsonObject.get("id").toString()), jsonObject.get("color").toString());
            productArrayList.add(product);
        }
        operations.setProducts(productArrayList);
    }
}
