package com.example.zatec.util;

import com.example.zatec.service.impl.StarWarsServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Util {

    static final Logger logger = LoggerFactory.getLogger(Util.class);

    @Autowired
    private RestTemplate restTemplate;

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

}
