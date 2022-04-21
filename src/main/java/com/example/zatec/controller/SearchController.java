package com.example.zatec.controller;


import com.example.zatec.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/search")
public class SearchController {


    @Autowired
    SearchService searchService;


    @GetMapping("/{name}")
    public ResponseEntity<?> search(@PathVariable String name){

        return ResponseEntity.status(HttpStatus.OK).body(searchService.search(name));
    }


}
