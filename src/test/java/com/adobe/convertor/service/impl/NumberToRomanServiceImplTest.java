package com.adobe.convertor.service.impl;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 28 2024 - 4:30 PM
 */




import com.adobe.convertor.bean.ConversionResult;
import com.adobe.convertor.exception.InvalidInputException;
import com.adobe.convertor.service.NumberToRomanService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class NumberToRomanServiceImplTest {

    @InjectMocks
    private NumberToRomanServiceImpl numberToRomanService;

    public NumberToRomanServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConvertToRomanNumeral_Valid() {
        String result = numberToRomanService.convertToRomanNumeral(1994);
        assertEquals("MCMXCIV", result);
    }

    @Test
    public void testConvertToRomanNumeral_OutOfRange() {
        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () -> numberToRomanService.convertToRomanNumeral(4000),
                "Expected convertToRomanNumeral(4000) to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Number out of range (1-3999)"));
    }

    @Test
    public void testConvertRangeToRoman_Valid() {
        List<ConversionResult> results = numberToRomanService.convertRangeToRoman(1, 2);
        assertEquals(2, results.size());
        assertEquals("I", results.get(0).getOutput());
        assertEquals("II", results.get(1).getOutput());
    }

    @Test
    public void testConvertRangeToRoman_InvalidRange() {
        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () -> numberToRomanService.convertRangeToRoman(10, 5),
                "Expected convertRangeToRoman(10, 5) to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Invalid range. Ensure min < max and both are in the range 1-3999."));
    }

    @Test
    public void testConvertRangeToRoman_Async() throws ExecutionException, InterruptedException {
        List<ConversionResult> results = numberToRomanService.convertRangeToRoman(1, 3);
        assertEquals(3, results.size());
        assertEquals("I", results.get(0).getOutput());
        assertEquals("II", results.get(1).getOutput());
        assertEquals("III", results.get(2).getOutput());
    }
}

