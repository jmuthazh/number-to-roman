package com.adobe.convertor.controller;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 25 2024 - 05:19 AM
 */


import com.adobe.convertor.bean.ConversionResponse;
import com.adobe.convertor.exception.InvalidInputException;
import com.adobe.convertor.service.NumberToRomanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberToRomanController {

    private final NumberToRomanService numberToRomanService;

    @Autowired
    public NumberToRomanController(NumberToRomanService numberToRomanService) {
        this.numberToRomanService = numberToRomanService;
    }

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
                return numberToRomanService.convertToRomanNumeral(number);
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Invalid number format: " + query);
            }
        } else if (min != null && max != null) {
            return new ConversionResponse(numberToRomanService.convertRangeToRoman(min, max));
        } else {
            throw new InvalidInputException("Either 'query' or both 'min' and 'max' parameters must be provided.");
        }
    }
}

