package com.example.zatec.controller;

import com.example.zatec.service.ChuckNorrisService;
import com.example.zatec.service.impl.ChuckNorrisServiceImpl;
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

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChuckNorrisControllerTest {


    @Autowired
    private MockMvc chuckMockMvc;

    @MockBean
    ChuckNorrisService chuckNorrisService;

    @InjectMocks
    ChuckNorrisController chuckNorrisController;

    private static String baseEndpoint = "http://localhost:8093";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        chuckNorrisService= mock(ChuckNorrisServiceImpl.class);
        chuckNorrisController = new ChuckNorrisController(chuckNorrisService);
        chuckMockMvc = MockMvcBuilders.standaloneSetup(chuckNorrisController).build();
    }

    @Test
    void chuckNorrisCategories() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final List<String> expected = Arrays.asList("animals", "history", "food");
        when(chuckNorrisService.getJokesCategories()).thenReturn(expected);

        MvcResult mvcResult = chuckMockMvc.perform(
                        get(baseEndpoint+"/chuck/categories") )
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        List<String> actual = mapper.readValue(actualResponse, List.class);

        assertThat(actual).isNotNull();
        assertThat(actual.size()).isGreaterThan(0);
    }
}