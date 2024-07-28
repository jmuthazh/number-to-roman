package com.adobe.convertor.service;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 28 2024 - 4:22 PM
 */


import com.adobe.convertor.bean.ConversionResult;
import java.util.List;

public interface NumberToRomanService {

    String convertToRomanNumeral(int number);

    List<ConversionResult> convertRangeToRoman(int min, int max);
}

