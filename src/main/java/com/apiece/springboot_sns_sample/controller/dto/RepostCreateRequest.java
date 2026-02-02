package com.apiece.springboot_sns_sample.controller.dto;

import jakarta.validation.constraints.NotNull;

import com.apiece.springboot_sns_sample.domain.post.Post;
import com.apiece.springboot_sns_sample.domain.user.User;

public record RepostCreateRequest(@NotNull(message = "Repost ID is required") Long repostId) {
    public Post toEntity(User user) {
        return Post.createRepost(user, repostId);
    }
}
