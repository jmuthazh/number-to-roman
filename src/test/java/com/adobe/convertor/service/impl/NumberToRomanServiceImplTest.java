package com.adobe.convertor.service.impl;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 28 2024 - 4:30 PM
 */


import com.adobe.convertor.bean.ConversionResult;
import com.adobe.convertor.exception.ConversionProcessException;
import com.adobe.convertor.exception.InvalidInputException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class NumberToRomanServiceImplTest {

    @InjectMocks
    private NumberToRomanServiceImpl numberToRomanService;

    @Mock
    private CompletableFuture<ConversionResult> mockFuture;

    public NumberToRomanServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConvertToRomanNumeral_Valid() {
        ConversionResult result = numberToRomanService.convertToRomanNumeral(1994);
        assertEquals("MCMXCIV", result.getOutput());
    }

    @Test
    void testConvertToRomanNumeral_OutOfRange() {
        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () -> numberToRomanService.convertToRomanNumeral(4000),
                "Expected convertToRomanNumeral(4000) to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Number out of range (1-3999)"));
    }

    @Test
    void testConvertRangeToRoman_Valid() {
        List<ConversionResult> results = numberToRomanService.convertRangeToRoman(1, 2);
        assertEquals(2, results.size());
        assertEquals("I", results.get(0).getOutput());
        assertEquals("II", results.get(1).getOutput());
    }

    @Test
    void testConvertRangeToRoman_InvalidRange() {
        InvalidInputException thrown = assertThrows(
                InvalidInputException.class,
                () -> numberToRomanService.convertRangeToRoman(10, 5),
                "Expected convertRangeToRoman(10, 5) to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Invalid range. Ensure min < max and both are in the range 1-3999."));
    }

    @Test
    void testConvertRangeToRoman_Async()  {
        List<ConversionResult> results = numberToRomanService.convertRangeToRoman(1, 3);
        assertEquals(3, results.size());
        assertEquals("I", results.get(0).getOutput());
        assertEquals("II", results.get(1).getOutput());
        assertEquals("III", results.get(2).getOutput());
    }

    @Test
    void testConvertRangeToRoman_InterruptedException() throws ExecutionException, InterruptedException {
        // Mock the CompletableFuture to throw InterruptedException
        Mockito.when(mockFuture.get()).thenThrow(new InterruptedException("Test InterruptedException"));

        // Override the convertToRomanAsync method to return the mocked future
        NumberToRomanServiceImpl numberToRomanServiceSpy = Mockito.spy(numberToRomanService);
        Mockito.doReturn(mockFuture).when(numberToRomanServiceSpy).convertToRomanAsync(Mockito.anyInt());

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> numberToRomanServiceSpy.convertRangeToRoman(1, 1),
                "Expected convertRangeToRoman to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Thread was interrupted"));
        assertInstanceOf(InterruptedException.class, thrown.getCause());
    }

    @Test
    void testConvertRangeToRoman_ExecutionException() throws ExecutionException, InterruptedException {
        // Mock the CompletableFuture to throw ExecutionException
        Mockito.when(mockFuture.get()).thenThrow(new ExecutionException("Test ExecutionException", new Throwable()));

        // Override the convertToRomanAsync method to return the mocked future
        NumberToRomanServiceImpl numberToRomanServiceSpy = Mockito.spy(numberToRomanService);
        Mockito.doReturn(mockFuture).when(numberToRomanServiceSpy).convertToRomanAsync(Mockito.anyInt());

        ConversionProcessException thrown = assertThrows(
                ConversionProcessException.class,
                () -> numberToRomanServiceSpy.convertRangeToRoman(1, 1),
                "Expected convertRangeToRoman to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Error processing request"));
        assertInstanceOf(ExecutionException.class, thrown.getCause());
    }
}

