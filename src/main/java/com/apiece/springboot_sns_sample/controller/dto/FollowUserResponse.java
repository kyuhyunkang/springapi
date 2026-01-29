package com.apiece.springboot_sns_sample.controller.dto;

import com.apiece.springboot_sns_sample.domain.user.User;
import java.util.List;

public record FollowUserResponse(Long id, String username) {
    public static FollowUserResponse from(User user) {
        return new FollowUserResponse(user.getId(), user.getUsername());
    }

    public static List<FollowUserResponse> fromList(List<User> users) {
        return users.stream().map(FollowUserResponse::from).toList();
    }
}
