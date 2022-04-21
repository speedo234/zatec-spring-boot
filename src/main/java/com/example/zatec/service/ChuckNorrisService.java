package com.example.zatec.service;


import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface ChuckNorrisService {

    List<String> getJokesCategories();

    List<JsonNode> searchChuckNorris(String name);

}
