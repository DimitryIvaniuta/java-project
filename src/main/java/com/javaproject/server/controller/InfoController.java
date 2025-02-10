package com.javaproject.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/public")
@RequiredArgsConstructor
@Slf4j
public class InfoController {

    /**
     * A simple Info endpoint to do synthetic monitoring.
     *
     * @return an info string.
     */
    @GetMapping(path = "/info")
    public ResponseEntity<String> info() {
        return ResponseEntity.ok("Java Project Example");
    }

}
