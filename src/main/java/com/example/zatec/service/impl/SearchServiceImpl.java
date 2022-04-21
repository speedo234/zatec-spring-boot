package com.example.zatec.service.impl;


import ch.qos.logback.core.subst.Node;
import com.example.zatec.exception.NotFoundException;
import com.example.zatec.service.ChuckNorrisService;
import com.example.zatec.service.SearchService;
import com.example.zatec.service.StarWarsService;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    StarWarsService starWarsService;

    @Autowired
    ChuckNorrisService chuckNorrisService;

//    @Value("${star.wars.base.url}")
//    private String starWarsBaseUrl;

    @Value("${chucknorris.base.url}")
    private String chuckNorrisBaseUrl;


    @Override
    public List<List<JsonNode>> search(String name) {
        List<JsonNode> starWarsList = starWarsService.searchStarWars(name);
        List<JsonNode> chuckNorrisList = chuckNorrisService.searchChuckNorris(name);
        //
        List<List<JsonNode>> combinedResponseList = new ArrayList<>();
        combinedResponseList.add( starWarsList );
        combinedResponseList.add(chuckNorrisList);
        return combinedResponseList;
    }
}





