package com.example.zatec.service.impl;


import com.example.zatec.exception.NotFoundException;
import com.example.zatec.service.StarWarsService;
import com.example.zatec.util.Util;
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
public class StarWarsServiceImpl implements StarWarsService {

    static final Logger logger = LoggerFactory.getLogger(StarWarsServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Util util;

    private String baseUrl;


    public StarWarsServiceImpl() {
    }

    @Autowired
    public StarWarsServiceImpl(RestTemplate restTemplate, Util util, @Value("${star.wars.base.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.util = util;
    }

    @Override
    public List<JsonNode> getStarWarsPeople() {
        JsonNode starWarsPeopleJsonNodeBody = null;
        List<JsonNode> jsonNodeList =  new ArrayList<>();
        starWarsPeopleJsonNodeBody = util.doApiCall(baseUrl+"/api/people");
        String nextUrl = util.getNextUrl(starWarsPeopleJsonNodeBody);
        logger.info("nextUrl=-> "+nextUrl);
        for( JsonNode jsonNode: starWarsPeopleJsonNodeBody.get("results") ){
            jsonNodeList.add( jsonNode );
        }
//        String previousUrl = nextUrl;
        while(nextUrl != null && !nextUrl.equalsIgnoreCase("null") /*&& previousUrl.equalsIgnoreCase(nextUrl)*/ ){
            starWarsPeopleJsonNodeBody = util.doApiCall( nextUrl.replaceAll("\"", "") );
            nextUrl = util.getNextUrl( starWarsPeopleJsonNodeBody );



            logger.info("nextUrl=-> "+nextUrl);
            for( JsonNode jsonNode: starWarsPeopleJsonNodeBody.get("results") ){
                jsonNodeList.add( jsonNode );
            }
        }
        if( jsonNodeList == null || jsonNodeList.isEmpty()){
            throw new NotFoundException("No Star Wars People Found...");
        }
        return jsonNodeList;
    }


    public List<JsonNode> searchStarWars(String queryString) {
        final String starWarsSearchUrl = baseUrl+"/api/people/?search="+queryString.trim();
        logger.info("starWarsSearchUrl==--> {} "+starWarsSearchUrl);
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(
                starWarsSearchUrl,
                JsonNode.class);
        JsonNode starWarsSearchJsonNodeBody = response.getBody();
        logger.info(":::starWarsSearchJsonNodeBody=-> {} ",starWarsSearchJsonNodeBody);
        if(starWarsSearchJsonNodeBody == null || starWarsSearchJsonNodeBody.isEmpty()){
            throw new NotFoundException("Could not find any people from "+starWarsSearchUrl);
        }
        List<JsonNode> jsonNodeList =  new ArrayList<>();
        for( JsonNode jsonNode: starWarsSearchJsonNodeBody.get("results")){
            jsonNodeList.add( jsonNode );
        }
        return jsonNodeList;
    }


}
