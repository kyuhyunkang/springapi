package com.apiece.springboot_sns_sample.domain.like;

public class LikeException extends RuntimeException {

    public LikeException(String message) {
        super(message);
    }

    public static class AlreadyLikedException extends LikeException {
        public AlreadyLikedException(Long postId) {
            super("Already liked post: " + postId);
        }
    }

    public static class NotLikedException extends LikeException {
        public NotLikedException(Long postId) {
            super("Not liked post: " + postId);
        }
    }
}
