package com.example.producerconsumer;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@CrossOrigin(origins="http://localhost:4200/")
@RequestMapping("/action")
public class Controller {
    JSONParser jsonParser = new JSONParser();
    Operations operations = new Operations();

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
