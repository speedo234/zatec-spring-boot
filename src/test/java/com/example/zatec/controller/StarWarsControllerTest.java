package com.example.zatec.controller;

import com.example.zatec.service.ChuckNorrisService;
import com.example.zatec.service.StarWarsService;
import com.example.zatec.service.impl.ChuckNorrisServiceImpl;
import com.example.zatec.service.impl.StarWarsServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StarWarsControllerTest {


    @Autowired
    private MockMvc starwarsMockMvc;

    @MockBean
    StarWarsService starWarsService;

    @InjectMocks
    StarWarsController starWarsController;

    private static String baseEndpoint = "http://localhost:8093";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        starWarsService= mock(StarWarsServiceImpl.class);
        starWarsController = new StarWarsController(starWarsService);
        starwarsMockMvc = MockMvcBuilders.standaloneSetup(starWarsController).build();
    }

    @Test
    void starWarsPeople() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final String dummyJsonString = "{ \"next\": \"https://swapi.dev/api/people/?page=2\", \"results\": [ { \"dummyKey1\": \"dummyValue1\" }, { \"dummyKey2\": \"dummyValue2\" } ]  }";
        JsonNode expectedNode = mapper.readTree(dummyJsonString);

        List<JsonNode> expected = new ArrayList<>();
        expected.add(expectedNode);
        expected.add(expectedNode);

        when(starWarsService.getStarWarsPeople()).thenReturn(expected);

        MvcResult mvcResult = starwarsMockMvc.perform(
                        get(baseEndpoint+"/swapi/people") )
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        List<JsonNode> actual = mapper.readValue(actualResponse, List.class);

        assertThat(actual).isNotNull();
        assertThat(actual.size()).isGreaterThan(0);
    }
}