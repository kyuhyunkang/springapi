package com.apiece.springboot_sns_sample.domain.reply;

public class ReplyException extends RuntimeException {

    public ReplyException(String message) {
        super(message);
    }

    public static class ReplyNotFoundException extends ReplyException {
        public ReplyNotFoundException(Long id) {
            super("Reply not found with id: " + id);
        }
    }

    public static class ReplyNotOwnedException extends ReplyException {
        public ReplyNotOwnedException(Long replyId, Long userId) {
            super("Reply " + replyId + " is not owned by user " + userId);
        }
    }
}
