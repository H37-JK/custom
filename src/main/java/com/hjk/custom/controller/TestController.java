package com.hjk.custom.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(value = "/test/{name}")
    public ResponseEntity<String> test(@PathVariable String name) {
        return ResponseEntity.ok(name);
    }

    @GetMapping(value = "/test2/{name}")
    public ResponseEntity<String> test2(@PathVariable String name) {
        return ResponseEntity.ok(name);
    }
}
