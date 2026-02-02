package com.apiece.springboot_sns_sample.controller.dto;

import java.util.List;

import com.apiece.springboot_sns_sample.domain.user.User;

public record FollowUserResponse(Long id, String username) {
    public static FollowUserResponse from(User user) {
        return new FollowUserResponse(user.getId(), user.getUsername());
    }

    public static List<FollowUserResponse> fromList(List<User> users) {
        return users.stream().map(FollowUserResponse::from).toList();
    }
}
