package com.apiece.springboot_sns_sample.domain.repost;

public class RepostException extends RuntimeException {

    public RepostException(String message) {
        super(message);
    }

    public static class RepostNotFoundException extends RepostException {
        public RepostNotFoundException(Long id) {
            super("Repost not found with id: " + id);
        }
    }

    public static class AlreadyRepostedException extends RepostException {
        public AlreadyRepostedException(Long postId, Long userId) {
            super("User " + userId + " already reposted post " + postId);
        }
    }

    public static class RepostNotOwnedException extends RepostException {
        public RepostNotOwnedException(Long repostId, Long userId) {
            super("Repost " + repostId + " is not owned by user " + userId);
        }
    }
}
