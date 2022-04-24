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


//    @Autowired
    SearchService searchService;


    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/{queryString}")
    public ResponseEntity<?> search(@PathVariable String queryString){
        return ResponseEntity.status(HttpStatus.OK).body(searchService.search(queryString));
    }


}
