package com.adobe.convertor.controller;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 25 2024 - 05:19 AM
 */

import com.adobe.convertor.bean.ConversionResponse;
import com.adobe.convertor.bean.ConversionResult;
import com.adobe.convertor.exception.InvalidInputException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class NumberToRomanController {


    @GetMapping("/romannumeral")
    @Operation(summary = "Convert number to Roman numeral",
            description = "Convert a number or a range of numbers to Roman numerals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully converted"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public Object convertToRoman(@Parameter(description = "The number to be converted") @RequestParam(value = "query", required = false) String query,
                                 @Parameter(description = "The minimum number in the range") @RequestParam(value = "min", required = false) Integer min,
                                 @Parameter(description = "The maximum number in the range") @RequestParam(value = "max", required = false) Integer max) {
        if (query != null) {
            try {
                int number = Integer.parseInt(query);
                return convertToRomanNumeral(number);
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Invalid number format: " + query);
            }
        } else if (min != null && max != null) {
            return new ConversionResponse(convertRangeToRoman(min, max));
        } else {
            throw new InvalidInputException("Either 'query' or both 'min' and 'max' parameters must be provided.");
        }
    }

    private List<ConversionResult> convertRangeToRoman(int min, int max) {
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

    private String convertToRomanNumeral(int number) {
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
}
