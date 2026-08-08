package com.junioroffer.infrastructure.apiValidation;

import java.util.List;

public record ApiValidationErrorDto(
        List<String> messages,
        String status
) {
}
