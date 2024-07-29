package com.adobe.convertor.integration;

import com.adobe.convertor.bean.ConversionResponse;
import com.adobe.convertor.exception.InvalidInputException;
import com.adobe.convertor.service.NumberToRomanService;
import com.adobe.convertor.controller.NumberToRomanController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NumberToRomanIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NumberToRomanService numberToRomanService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConvertToRoman_SingleNumber() throws Exception {
        // Arrange
        when(numberToRomanService.convertToRomanNumeral(5)).thenReturn("V");

        // Act & Assert
        mockMvc.perform(get("/romannumeral")
                        .param("query", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("V"));
    }

    @Test
    public void testConvertToRoman_Range() throws Exception {
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
                .andExpect(content().json("{\"results\":[{\"input\":\"1\",\"output\":\"I\"},{\"input\":\"2\",\"output\":\"II\"}]}"));
    }

    @Test
    public void testConvertToRoman_InvalidInput() throws Exception {
        // Arrange
        when(numberToRomanService.convertToRomanNumeral(anyInt())).thenThrow(new InvalidInputException("Invalid number format"));

        // Act & Assert
        mockMvc.perform(get("/romannumeral")
                        .param("query", "invalid")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidInputException))
                .andExpect(result -> assertEquals("Invalid number format", result.getResolvedException().getMessage()));
    }

    @Test
    public void testConvertToRoman_InvalidRange() throws Exception {
        // Arrange
        when(numberToRomanService.convertRangeToRoman(5, 3)).thenThrow(new InvalidInputException("Invalid range. Ensure min < max and both are in the range 1-3999."));

        // Act & Assert
        mockMvc.perform(get("/romannumeral")
                        .param("min", "5")
                        .param("max", "3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidInputException))
                .andExpect(result -> assertEquals("Invalid range. Ensure min < max and both are in the range 1-3999.", result.getResolvedException().getMessage()));
    }
}
