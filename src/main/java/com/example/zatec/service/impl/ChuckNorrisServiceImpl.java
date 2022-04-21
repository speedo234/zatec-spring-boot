package com.example.zatec.service.impl;


import com.example.zatec.exception.NotFoundException;
import com.example.zatec.service.ChuckNorrisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ChuckNorrisServiceImpl implements ChuckNorrisService {

    static final Logger logger = LoggerFactory.getLogger(ChuckNorrisServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${chucknorris.base.url}")
    private String baseUrl;

    @Override
    public List<String> getJokesCategories() {
        ResponseEntity<List> response = restTemplate.getForEntity(
                baseUrl+"/jokes/categories",
                List.class);
        List<String> jokesCategoriesList = response.getBody();
        logger.info(":::jokesCategoriesList=-> "+jokesCategoriesList);
        if(jokesCategoriesList == null || jokesCategoriesList.isEmpty()){
            throw new NotFoundException("Could not find any jokes categories at the requested path...");
        }
        return jokesCategoriesList;
    }
}
