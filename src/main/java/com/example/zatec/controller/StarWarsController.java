package com.example.zatec.controller;


import com.example.zatec.service.StarWarsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/swapi")
public class StarWarsController {

//    @Autowired
    StarWarsService starWarsService;

    @Autowired
    public StarWarsController(StarWarsService starWarsService) {
        this.starWarsService = starWarsService;
    }

    @GetMapping("/people")
    public ResponseEntity<?> starWarsPeople(){
        return ResponseEntity.status(HttpStatus.OK).body(starWarsService.getStarWarsPeople());
    }

}
