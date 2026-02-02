package com.apiece.springboot_sns_sample.controller.dto;

import java.time.LocalDateTime;

import com.apiece.springboot_sns_sample.domain.post.Post;

public record PostResponse(Long id, String content, Long userId, String username, Long parentId, Long quoteId,
        Long repostId, Integer repostCount, Integer quoteCount, Integer likeCount, Integer replyCount, Long viewCount,
        LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static PostResponse from(Post post) {
        return new PostResponse(post.getId(), post.getContent(), post.getUser().getId(), post.getUser().getUsername(),
                post.getParentId(), post.getQuoteId(), post.getRepostId(), post.getRepostCount(), post.getQuoteCount(),
                post.getLikeCount(), post.getReplyCount(), post.getViewCount(), post.getCreatedAt(),
                post.getUpdatedAt());
    }
}
