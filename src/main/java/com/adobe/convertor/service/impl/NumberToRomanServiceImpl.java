package com.adobe.convertor.service.impl;

/*
 * Implementation of the NumberToRomanService.
 *
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 28 2024 - 4:24 PM
 */


import com.adobe.convertor.bean.ConversionResult;
import com.adobe.convertor.exception.ConversionProcessException;
import com.adobe.convertor.exception.InvalidInputException;
import com.adobe.convertor.service.NumberToRomanService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

@Service
public class NumberToRomanServiceImpl implements NumberToRomanService {

    /**
     * Converts a given number to its Roman numeral representation.
     *
     * @param number the number to be converted
     * @return the Roman numeral representation of the number
     * @throws InvalidInputException if the number is out of range (1-3999)
     */
    @Override
    public String convertToRomanNumeral(int number) {
        if (number < 1 || number > 3999) {
            throw new InvalidInputException("Number out of range (1-3999): " + number);
        }

        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] units = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return thousands[number / 1000] +
                hundreds[(number % 1000) / 100] +
                tens[(number % 100) / 10] +
                units[number % 10];
    }

    /**
     * Converts a range of numbers to their Roman numeral representations.
     *
     * @param min the minimum number in the range (inclusive)
     * @param max the maximum number in the range (inclusive)
     * @return an unmodifiable list of ConversionResult containing the original number and its Roman numeral representation
     * @throws InvalidInputException if the range is invalid or numbers are out of range (1-3999)
     */
    @Override
    public List<ConversionResult> convertRangeToRoman(int min, int max) {
        if (min < 1 || max > 3999 || min > max) {
            throw new InvalidInputException("Invalid range. Ensure min < max and both are in the range 1-3999.");
        }

        List<CompletableFuture<ConversionResult>> futures = IntStream.rangeClosed(min, max)
                .mapToObj(this::convertToRomanAsync)
                .toList();

        return futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException e) {
                        // Re-interrupt the current thread
                        Thread.currentThread().interrupt();
                        throw new ConversionProcessException("Thread was interrupted", e);
                    } catch (ExecutionException e) {
                        throw new ConversionProcessException("Error processing request", e);
                    }
                })
                .toList();
    }

    /**
     * Asynchronously converts a given number to its Roman numeral representation.
     *
     * @param number the number to be converted
     * @return a CompletableFuture containing the ConversionResult with the original number and its Roman numeral representation
     */
    CompletableFuture<ConversionResult> convertToRomanAsync(int number) {
        return CompletableFuture.supplyAsync(() -> new ConversionResult(String.valueOf(number), convertToRomanNumeral(number)));
    }
}

