package com.apiece.springboot_sns_sample.domain.follow;

public class FollowException extends RuntimeException {

    public FollowException(String message) {
        super(message);
    }

    public static FollowException selfFollow() {
        return new FollowException("Cannot follow yourself");
    }

    public static FollowException alreadyFollowing(Long targetUserId) {
        return new FollowException("Already following user: " + targetUserId);
    }

    public static FollowException notFollowing(Long targetUserId) {
        return new FollowException("Not following user: " + targetUserId);
    }
}
