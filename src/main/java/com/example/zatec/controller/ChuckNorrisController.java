package com.example.zatec.controller;


import com.example.zatec.service.ChuckNorrisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/chuck")
public class ChuckNorrisController {

    @Autowired
    ChuckNorrisService chuckService;


    @GetMapping("/categories")
    public ResponseEntity<?> chuckNorrisCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(chuckService.getJokesCategories());
    }

}
