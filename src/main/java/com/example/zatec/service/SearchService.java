package com.example.zatec.service;


import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface SearchService {

    List<List<JsonNode>> search(String queryString);

}
