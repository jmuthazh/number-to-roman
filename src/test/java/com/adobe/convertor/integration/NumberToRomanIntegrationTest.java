package com.adobe.convertor.integration;

import com.adobe.convertor.bean.ConversionResponse;
import com.adobe.convertor.bean.ConversionResult;
import com.adobe.convertor.exception.InvalidInputException;
import com.adobe.convertor.service.NumberToRomanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NumberToRomanIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NumberToRomanService numberToRomanService;

    @Test
    @WithMockUser(username = "user")
    void testConvertToRoman_SingleNumber() throws Exception {
        // Arrange

        ConversionResult conversionResult = new ConversionResult("5", "V");
        when(numberToRomanService.convertToRomanNumeral(5)).thenReturn(conversionResult);
        // Act & Assert
        mockMvc.perform(get("/romannumeral")
                        .param("query", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"input\":\"5\",\"output\":\"V\"}"));
    }

    @Test
    @WithMockUser(username = "user")
    void testConvertToRoman_Range() throws Exception {
        // Arrange
        ConversionResponse response = new ConversionResponse(
                List.of(new ConversionResult("1", "I"), new ConversionResult("2", "II"))
        );
        when(numberToRomanService.convertRangeToRoman(1, 2)).thenReturn(response.getConversions());


        // Act & Assert
        mockMvc.perform(get("/romannumeral")
                        .param("min", "1")
                        .param("max", "2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"conversions\":[{\"input\":\"1\",\"output\":\"I\"},{\"input\":\"2\",\"output\":\"II\"}]}"));
    }

    @Test
    @WithMockUser(username = "user")
    void testConvertToRoman_InvalidInput() throws Exception {
        // Arrange

        String errorMessage = "Invalid number format: invalid";
        when(numberToRomanService.convertToRomanNumeral(anyInt()))
                .thenThrow(new InvalidInputException(errorMessage));

        // Act & Assert
        mockMvc.perform(get("/romannumeral")
                        .param("query", "invalid")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(InvalidInputException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals(errorMessage, Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @WithMockUser(username = "user")
    void testConvertToRoman_InvalidRange() throws Exception {

        String errorMessage = "Invalid range. Ensure min < max and both are in the range 1-3999.";
        // Arrange
        when(numberToRomanService.convertRangeToRoman(5, 3))
                .thenThrow(new InvalidInputException(errorMessage));

        // Act & Assert
        mockMvc.perform(get("/romannumeral")
                        .param("min", "5")
                        .param("max", "3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(InvalidInputException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals(errorMessage, Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

}
