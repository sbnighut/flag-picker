package com.practice.flagpicker.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND, reason = "Could not find countries or country flag associated with the search criteria. Try it after modifying the criteria.")
public class CountryNotFoundException extends RuntimeException  {
    private static final long serialVersionUID = 1L;

    public CountryNotFoundException(String message) {
        super(message);
    }

    public CountryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
