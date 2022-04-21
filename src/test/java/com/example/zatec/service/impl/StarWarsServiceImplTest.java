package com.example.zatec.service.impl;

import com.example.zatec.util.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class StarWarsServiceImplTest {


    @MockBean
    private RestTemplate restTemplate = new RestTemplate();

    @MockBean
    Util util;

    @InjectMocks
    StarWarsServiceImpl starWarsService;

    final String baseUrl = "https://swapi.dev";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        restTemplate = mock(RestTemplate.class);
        util = mock(Util.class);
        starWarsService = new StarWarsServiceImpl(restTemplate, util, baseUrl);
    }

    @Disabled
    void getStarWarsPeople() throws JsonProcessingException {
        final String dummyJsonString = "{ \"next\": \"https://swapi.dev/api/people/?page=2\", \"results\": [ { \"dummyKey1\": \"dummyValue1\" }, { \"dummyKey2\": \"dummyValue2\" } ]  }";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode expected = mapper.readTree(dummyJsonString);
        final String url = baseUrl+"/api/people";
        Mockito.when(restTemplate.getForEntity(
                url,
                List.class)).thenReturn(new ResponseEntity(expected, HttpStatus.OK));
        Mockito.when(util.doApiCall( url )).thenReturn( expected );
        Mockito.when(util.getNextUrl( expected )).thenReturn( url );
        List<JsonNode> actual = starWarsService.getStarWarsPeople();
//        assertThat(actual).isNotEqualTo(null);
//        assertThat(actual.size()).isGreaterThan(0);
    }


    @Test
    void searchStarWars() throws JsonProcessingException {
        final String queryString = "walker";
        final String starWarsSearchUrl = baseUrl+"/api/people/?search="+queryString.trim();
        final String dummyJsonString = "{ \"results\": [ { \"dummyKey1\": \"dummyValue1\" }, { \"dummyKey2\": \"dummyValue2\" } ]  }";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode expected = mapper.readTree(dummyJsonString);
        Mockito.when(restTemplate.getForEntity(
                starWarsSearchUrl,
                JsonNode.class)).thenReturn(new ResponseEntity(expected, HttpStatus.OK));
        List<JsonNode> actual = starWarsService.searchStarWars(queryString);
        assertThat(actual).isNotEqualTo(null);
        assertThat(actual.size()).isGreaterThan(0);


    }
}