package com.apiece.springboot_sns_sample.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostUpdateRequest(
        @NotBlank(message = "Content is required") @Size(max = 500, message = "Content must not exceed 500 characters") String content) {
}
