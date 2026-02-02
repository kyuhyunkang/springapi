package com.apiece.springboot_sns_sample.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.apiece.springboot_sns_sample.domain.post.Post;
import com.apiece.springboot_sns_sample.domain.user.User;

public record QuoteCreateRequest(
        @NotBlank(message = "Content is required") @Size(max = 500, message = "Content must not exceed 500 characters") String content,

        @NotNull(message = "Quote post ID is required") Long quoteId) {
    public Post toEntity(User user) {
        return Post.createQuote(content, user, quoteId);
    }
}
