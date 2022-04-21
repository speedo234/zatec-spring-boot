package com.example.zatec.service.impl;


import com.example.zatec.exception.NotFoundException;
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
public class StarWarsServiceImpl implements StarWarsService {

    static final Logger logger = LoggerFactory.getLogger(StarWarsServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${star.wars.base.url}")
    private String baseUrl;

    @Override
    public List<JsonNode> getStarWarsPeople() {
        JsonNode starWarsPeopleJsonNodeBody = null;
        List<JsonNode> jsonNodeList =  new ArrayList<>();
        starWarsPeopleJsonNodeBody = doApiCall(baseUrl+"/api/people");
        String nextUrl = getNextUrl(starWarsPeopleJsonNodeBody);
        logger.info("nextUrl=-> "+nextUrl);
        for( JsonNode jsonNode: starWarsPeopleJsonNodeBody.get("results") ){
            jsonNodeList.add( jsonNode );
        }
        while(nextUrl != null && !nextUrl.equalsIgnoreCase("null")){
            starWarsPeopleJsonNodeBody = doApiCall( nextUrl.replaceAll("\"", "") );
            nextUrl = getNextUrl( starWarsPeopleJsonNodeBody );
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


    public JsonNode doApiCall(String url){
        JsonNode starWarsPeopleJsonNodeBody = null;
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(
                url, JsonNode.class);
        starWarsPeopleJsonNodeBody = response.getBody();
        logger.info("=========="+starWarsPeopleJsonNodeBody);
        logger.info("=====count====="+starWarsPeopleJsonNodeBody.get("count"));
        logger.info("=====next====="+starWarsPeopleJsonNodeBody.get("next"));
        logger.info("=====results====="+starWarsPeopleJsonNodeBody.get("results"));
        return starWarsPeopleJsonNodeBody;
    }


    public String getNextUrl( JsonNode starWarsPeopleJsonNodeBody){
        return starWarsPeopleJsonNodeBody.get("next").toString().replaceAll("\"", "");
    }


    public List<JsonNode> searchStarWars(String name) {
        final String starWarsSearchUrl = baseUrl+"/api/people/?search="+name.trim();
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
