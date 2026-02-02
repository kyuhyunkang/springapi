package com.apiece.springboot_sns_sample.domain.post;

public class PostException extends RuntimeException {

    public PostException(String message) {
        super(message);
    }

    public static class PostNotFoundException extends PostException {
        public PostNotFoundException(Long id) {
            super("Post not found with id: " + id);
        }
    }

    public static class PostNotOwnedException extends PostException {
        public PostNotOwnedException(Long postId, Long userId) {
            super("Post " + postId + " is not owned by user " + userId);
        }
    }
}
