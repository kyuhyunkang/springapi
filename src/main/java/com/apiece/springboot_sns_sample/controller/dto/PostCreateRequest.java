package com.apiece.springboot_sns_sample.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.apiece.springboot_sns_sample.domain.post.Post;
import com.apiece.springboot_sns_sample.domain.user.User;

public record PostCreateRequest(
        @NotBlank(message = "Content is required") @Size(max = 500, message = "Content must not exceed 500 characters") String content) {
    public Post toEntity(User user) {
        return new Post(content, user);
    }
}
