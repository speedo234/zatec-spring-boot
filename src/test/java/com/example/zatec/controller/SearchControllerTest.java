package com.example.zatec.controller;

import com.example.zatec.service.SearchService;
import com.example.zatec.service.StarWarsService;
import com.example.zatec.service.impl.SearchServiceImpl;
import com.example.zatec.service.impl.StarWarsServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SearchControllerTest {


    @Autowired
    private MockMvc searchMockMvc;

    @MockBean
    SearchServiceImpl searchService;

    @InjectMocks
    SearchController searchController;


    private static String baseEndpoint = "http://localhost:8093";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        searchService= mock(SearchServiceImpl.class);
        searchController = new SearchController(searchService);
        searchMockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
    }

    @Test
    void search() throws Exception {

        final String queryString = "skype";

        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());

        final String dummyJsonString = "{ \"next\": \"https://swapi.dev/api/people/?page=2\", \"results\": [ { \"dummyKey1\": \"dummyValue1\" }, { \"dummyKey2\": \"dummyValue2\" } ]  }";
        JsonNode expectedNode = mapper.readTree(dummyJsonString);
        List<JsonNode> expectedList = new ArrayList<>();
        expectedList.add(expectedNode);
        List<List<JsonNode>> expected = new ArrayList<>();
        expected.add(expectedList);
        expected.add(expectedList);

        when(searchService.search(queryString)).thenReturn(expected);

        MvcResult mvcResult = searchMockMvc.perform(
                        get(baseEndpoint+"/search/"+queryString) )
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        List<JsonNode> actual = mapper.readValue(actualResponse, List.class);

        assertThat(actual).isNotNull();
        assertThat(actual.size()).isGreaterThan(0);
        assertThat(actual.size()).isEqualTo(expected.size());
    }
}