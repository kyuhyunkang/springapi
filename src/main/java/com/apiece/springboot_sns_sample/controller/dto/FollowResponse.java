package com.apiece.springboot_sns_sample.controller.dto;

public record FollowResponse(Long targetUserId, String targetUsername, String action, Long myFolloweesCount) {

    public static FollowResponse followed(Long targetUserId, String targetUsername, Long followeesCount) {
        return new FollowResponse(targetUserId, targetUsername, "FOLLOWED", followeesCount);
    }

    public static FollowResponse unfollowed(Long targetUserId, String targetUsername, Long followeesCount) {
        return new FollowResponse(targetUserId, targetUsername, "UNFOLLOWED", followeesCount);
    }
}
