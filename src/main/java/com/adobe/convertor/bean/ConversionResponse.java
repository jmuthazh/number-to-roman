package com.adobe.convertor.bean;

/*
 * @project number-to-roman
 * @author jayakesavanmuthazhagan
 * @created - Jul, 27 2024 - 9:12 AM
 */


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ConversionResponse {
 private List<ConversionResult> conversions;
}
