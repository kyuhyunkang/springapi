package com.apiece.springboot_sns_sample.controller.dto;

public record FollowCountResponse(Long count) {
    public static FollowCountResponse from(Long count) {
        return new FollowCountResponse(count);
    }
}
