package com.finist.personwebapp.model;

import java.util.HashMap;
import java.util.Map;

public class ValidationErrorResponse {
    public String message;
    public Map<String, String> errors;

    public ValidationErrorResponse(String message) {
        this.message = message;
        errors = new HashMap<>();
    }
    public ValidationErrorResponse addError(String key, String value){
        this.errors.put(key, value);
        return this;
    }
}
