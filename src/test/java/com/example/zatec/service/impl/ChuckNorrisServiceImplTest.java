package com.example.zatec.service.impl;

import com.example.zatec.service.ChuckNorrisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class ChuckNorrisServiceImplTest {

    @MockBean
    private RestTemplate restTemplate = new RestTemplate();

    @InjectMocks
    ChuckNorrisServiceImpl chuckNorrisService;

    String baseUrl = "https://api.chucknorris.io";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        restTemplate = mock(RestTemplate.class);
        chuckNorrisService = new ChuckNorrisServiceImpl(restTemplate, baseUrl);
    }

    @Test
    void getJokesCategories() {
        List<String> stringList = Arrays.asList("dummyValue1", "dummyValue2", "dummyValue3", "dummyValue4", "dummyValue5");
        Mockito.when(restTemplate.getForEntity(
                baseUrl+"/jokes/categories",
                List.class)).thenReturn(new ResponseEntity(stringList, HttpStatus.OK));
        List<String> actual = chuckNorrisService.getJokesCategories();
        assertThat(actual).isNotEqualTo(null);
        assertThat(actual.size()).isGreaterThan(0);
    }

    @Test
    void searchChuckNorris() throws JsonProcessingException {
        final String searchString = "sky";
        final String chuckNorrisSearchUrl = baseUrl+"/jokes/search?query="+searchString.trim();

        final String dummyJsonString = "{ \"result\": [ { \"dummyKey1\": \"dummyValue1\" }, { \"dummyKey2\": \"dummyValue2\" } ]  }";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(dummyJsonString);
        Mockito.when(restTemplate.getForEntity(
                chuckNorrisSearchUrl,
                JsonNode.class)).thenReturn(new ResponseEntity(jsonNode, HttpStatus.OK));
        List<JsonNode> actual = chuckNorrisService.searchChuckNorris(searchString);
        assertThat(actual).isNotEqualTo(null);
        assertThat(actual.size()).isGreaterThan(0);
    }
}