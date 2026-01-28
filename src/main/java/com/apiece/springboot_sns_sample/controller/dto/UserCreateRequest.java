package com.apiece.springboot_sns_sample.controller.dto;

public record UserCreateRequest(
        String username,
        String password
) {
}
