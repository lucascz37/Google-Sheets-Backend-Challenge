package com.lucas.googleSheets.challenge.controller;

import com.lucas.googleSheets.challenge.service.SheetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class MainController {

    private final SheetService service;

    public MainController(SheetService service) {
        this.service = service;
    }

    @GetMapping(path = "")
    public ModelAndView index() throws IOException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("filtered", "Masters degree");
        params.put("consumers", service.getConsumersWithMasters());
        return new ModelAndView("index", params);
    }

    @GetMapping(value = "/{path}")
    public ModelAndView options(@PathVariable String path) throws IOException {
        HashMap<String, Object> params = new HashMap<>();
        switch (path){
            case "married":
                params.put("filtered", "Married");
                params.put("consumers", service.getMarriedConsumers());
                break;
            case "kids":
                params.put("filtered", "Have kids");
                params.put("consumers", service.getConsumersWithKids());
                break;
            case "teens":
                params.put("filtered", "Have teens");
                params.put("consumers", service.getConsumersWithTeens());
                break;
            case "born":
                params.put("filtered", "Born between 1957 and 1967");
                params.put("consumers", service.getConsumersBornBetween());
                break;
        }
        return new ModelAndView("index", params);
    }
}
