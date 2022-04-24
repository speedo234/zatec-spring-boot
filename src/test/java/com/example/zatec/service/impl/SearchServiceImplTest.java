package com.example.zatec.service.impl;

import com.example.zatec.util.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchServiceImplTest {


    @MockBean
    private RestTemplate restTemplate = new RestTemplate();

    @InjectMocks
    SearchServiceImpl searchService;

    @MockBean
    StarWarsServiceImpl starWarsService;

    @MockBean
    ChuckNorrisServiceImpl chuckNorrisService;

    final String baseUrl = "https://swapi.dev";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        restTemplate = mock(RestTemplate.class);
        chuckNorrisService = mock(ChuckNorrisServiceImpl.class);
        starWarsService = mock(StarWarsServiceImpl.class);
        searchService = new SearchServiceImpl(restTemplate, starWarsService, chuckNorrisService);
    }

    @Test
    void search() throws JsonProcessingException {

        final String queryString = "walker";

        ObjectMapper mapper = new ObjectMapper();
        final String dummyJsonString = "{ \"next\": \"https://swapi.dev/api/people/?page=2\", \"results\": [ { \"dummyKey1\": \"dummyValue1\" }, { \"dummyKey2\": \"dummyValue2\" } ]  }";
        JsonNode expectedNode = mapper.readTree(dummyJsonString);
        List<JsonNode> expected = new ArrayList<>();
        expected.add(expectedNode);
        expected.add(expectedNode);

        when(starWarsService.searchStarWars(queryString)).thenReturn(expected);
        when(chuckNorrisService.searchChuckNorris(queryString)).thenReturn(expected);

        List<List<JsonNode>> actual = searchService.search(queryString);

        assertThat(actual).isNotNull();
        assertThat(actual.size()).isGreaterThan(0);
    }
}