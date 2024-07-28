package com.adobe.convertor.controller;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 25 2024 - 05:16 AM
 */


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NumberToRomanController.class)
public class NumberToRomanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testConvertToRomanValidNumber() throws Exception {
        MvcResult result = mockMvc.perform(get("/romannumeral")
                        .param("query", "1999"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("MCMXCIX")))
                .andReturn();

        assertEquals("MCMXCIX", result.getResponse().getContentAsString());
    }

    @Test
    public void testConvertToRomanInvalidNumberFormat() throws Exception {
        mockMvc.perform(get("/romannumeral")
                        .param("query", "invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("Invalid number format: invalid",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void testConvertToRomanNumberOutOfRange() throws Exception {
        mockMvc.perform(get("/romannumeral")
                        .param("query", "4000"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("Number out of range (1-3999): 4000",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void testConvertToRomanNumberTooSmall() throws Exception {
        mockMvc.perform(get("/romannumeral")
                        .param("query", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("Number out of range (1-3999): 0",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void testConvertRangeToRoman() throws Exception {
        mockMvc.perform(get("/romannumeral")
                        .param("min", "1")
                        .param("max", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"conversions\":[{\"input\":\"1\",\"output\":\"I\"},{\"input\":\"2\",\"output\":\"II\"},{\"input\":\"3\",\"output\":\"III\"}]}"));
    }

    @Test
    public void testConvertRangeToRomanInvalidRange() throws Exception {
        mockMvc.perform(get("/romannumeral")
                        .param("min", "3")
                        .param("max", "2"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("Invalid range. Ensure min < max and both are in the range 1-3999.",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }
}
