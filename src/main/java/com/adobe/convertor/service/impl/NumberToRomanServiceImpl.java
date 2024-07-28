package com.adobe.convertor.service.impl;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 28 2024 - 4:24 PM
 */


import com.adobe.convertor.bean.ConversionResult;
import com.adobe.convertor.exception.InvalidInputException;
import com.adobe.convertor.service.NumberToRomanService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class NumberToRomanServiceImpl implements NumberToRomanService {

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
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException("Error processing request", e);
                    }
                })
                .collect(Collectors.toList());
    }

    private CompletableFuture<ConversionResult> convertToRomanAsync(int number) {
        return CompletableFuture.supplyAsync(() -> new ConversionResult(String.valueOf(number), convertToRomanNumeral(number)));
    }
}

