package com.JobOffers.apiValidationError;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ApiValidationErrorDto(
        List<String>messages,
        String status
) {
}
