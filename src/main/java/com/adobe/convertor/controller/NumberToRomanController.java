package com.adobe.convertor.controller;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 25 2024 - 05:19 AM
 */


import com.adobe.convertor.bean.ConversionResponse;
import com.adobe.convertor.bean.ConversionResult;
import com.adobe.convertor.exception.InvalidInputException;
import com.adobe.convertor.service.NumberToRomanService;
import com.adobe.convertor.validation.InputValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The {@code NumberToRomanController} class is a REST controller that handles requests for converting numbers to Roman numerals.
 * It uses the {@link NumberToRomanService} to perform the conversions and the {@link InputValidation} class for validating input.
 * This class is annotated with {@link RestController} to indicate that it is a Spring MVC controller and {@link Slf4j} for logging.
 */
@RestController
@Slf4j
public class NumberToRomanController {

    private final NumberToRomanService numberToRomanService;

    /**
     * Constructs a new {@code NumberToRomanController} with the specified {@link NumberToRomanService}.
     *
     * @param numberToRomanService the service used to perform number to Roman numeral conversions
     */
    @Autowired
    public NumberToRomanController(NumberToRomanService numberToRomanService) {
        this.numberToRomanService = numberToRomanService;
    }


    /**
     * Converts a single number or a range of numbers to Roman numerals.
     *
     * @param query the number to be converted (optional)
     * @param min   the minimum number in the range (optional)
     * @param max   the maximum number in the range (optional)
     * @return a {@link ConversionResult} if a single number is provided, or a {@link ConversionResponse} if a range is provided
     * @throws InvalidInputException if the input is invalid
     */
    @GetMapping("/romannumeral")
    @Operation(summary = "Convert number to Roman numeral",
            description = "Convert a number or a range of numbers to Roman numerals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully converted"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Bad Request"),
            @ApiResponse(responseCode = "422", description = "Invalid input")

    })
    public Object convertToRoman(@Parameter(description = "The number to be converted") @RequestParam(value = "query", required = false) String query,
                                 @Parameter(description = "The minimum number in the range") @RequestParam(value = "min", required = false) Integer min,
                                 @Parameter(description = "The maximum number in the range") @RequestParam(value = "max", required = false) Integer max) {
        if (query != null) {
            try {
                int number = InputValidation.validateAndParseText(query);
                ConversionResult result = numberToRomanService.convertToRomanNumeral(number);
                log.info("Converted number {} to Roman numeral {}", result.getInput(), result.getOutput());
                return result;
            } catch (InvalidInputException ex) {
                log.error("Invalid input for query parameter: {}", query, ex);
                throw ex;
            }
        } else if (min != null && max != null) {
            try {
                InputValidation.validateRange(min, max);
                ConversionResponse response = new ConversionResponse(numberToRomanService.convertRangeToRoman(min, max));
                log.info("Converted range min:{} max:{} to Roman numeral conversion obj:{}", min, max, response);
                return response;
            } catch (InvalidInputException ex) {
                log.error("Invalid range parameters min:{} max:{}", min, max, ex);
                throw ex;
            }
        } else {
            log.error("Invalid input: Either 'query' or both 'min' and 'max' parameters must be provided.");
            throw new InvalidInputException("Either 'query' or both 'min' and 'max' parameters must be provided.");
        }
    }

}

