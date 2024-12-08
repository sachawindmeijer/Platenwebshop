package com.example.platenwinkel.helper;
import org.springframework.validation.BindingResult;

public class BindingResultHelper {
    public static String getErrorMessage(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        bindingResult.getFieldErrors().forEach(fieldError -> errorMessage.append(  "" + fieldError.getField() + " : " + fieldError.getDefaultMessage()).append(". "));
        return errorMessage.toString();
    }
}
