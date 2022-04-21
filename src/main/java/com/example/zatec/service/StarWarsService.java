package com.example.zatec.service;


import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface StarWarsService {

    List<JsonNode> getStarWarsPeople();

    List<JsonNode> searchStarWars(String name);


}
