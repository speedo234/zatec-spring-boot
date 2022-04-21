package com.example.zatec.service.impl;


import ch.qos.logback.core.subst.Node;
import com.example.zatec.exception.NotFoundException;
import com.example.zatec.service.SearchService;
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

    @Value("${star.wars.base.url}")
    private String starWarsBaseUrl;

    @Value("${chucknorris.base.url}")
    private String chuckNorrisBaseUrl;




    private List<JsonNode> searchChuckNorris(String name) {
        final String chuckNorrisSearchUrl = chuckNorrisBaseUrl+"/jokes/search?query="+name.trim();
        logger.info("chuckNorrisSearchUrl==--> {} "+chuckNorrisSearchUrl);
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(
                chuckNorrisSearchUrl,
                JsonNode.class);
        JsonNode chuckNorrisSearchJsonNodeBody = response.getBody();
        logger.info(":::chuckNorrisSearchJsonNodeBody=-> {} ",chuckNorrisSearchJsonNodeBody);
        if(chuckNorrisSearchJsonNodeBody == null || chuckNorrisSearchJsonNodeBody.isEmpty()){
            throw new NotFoundException("Could not find any jokes from "+chuckNorrisBaseUrl);
        }
        List<JsonNode> jsonNodeList =  new ArrayList<>();
        for( JsonNode jsonNode: chuckNorrisSearchJsonNodeBody.get("result")){
            jsonNodeList.add( jsonNode );
        }
        return jsonNodeList;
    }

    private List<JsonNode> searchStarWars(String name) {
        final String starWarsSearchUrl = starWarsBaseUrl+"/api/people/?search="+name.trim();
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

    @Override
    public List<List<JsonNode>> search(String name) {
        List<JsonNode> starWarsList = searchStarWars(name);
        List<JsonNode> chuckNorrisList = searchChuckNorris(name);
        //
        List<List<JsonNode>> combinedResponseList = new ArrayList<>();
        combinedResponseList.add( starWarsList );
        combinedResponseList.add(chuckNorrisList);

        return combinedResponseList;
    }
}





