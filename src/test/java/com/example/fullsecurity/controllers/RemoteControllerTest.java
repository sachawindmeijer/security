package com.example.fullsecurity.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class RemoteControllerTest {//intergratie test

    @Autowired
    MockMvc mockMvc;

@Autowired
    ObjectMapper objectMapper;

    @Test
    void addRemoteController() throws Exception {
        //arrange
        String requestJson = """
                {
                    "CompatibleWith" : "yes",
                    "BatteryType" : "aaa",
                    "Name" :  "RC54"
                }
                """;
        //act
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/remotecontrollers")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode=objectMapper.readTree(jsonResponse);
        String createdId = jsonNode.get("id").asText();

        // Debugging: Print de Location-header
        String locationHeader = result.getResponse().getHeader("Location");
        System.out.println("Location Header: " + locationHeader);

        //assert
        assertNotNull(locationHeader, "Location header should not be null");
        assertThat(result.getResponse().getHeader("Location"), matchesPattern("^.*/remotecontrollers/" + createdId));
    }
}