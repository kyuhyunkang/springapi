package com.apiece.springboot_sns_sample.controller.dto;

public record LikeResponse(Long postId, boolean liked, Integer likeCount) {
    public static LikeResponse liked(Long postId, Integer likeCount) {
        return new LikeResponse(postId, true, likeCount);
    }

    public static LikeResponse unliked(Long postId, Integer likeCount) {
        return new LikeResponse(postId, false, likeCount);
    }
}
