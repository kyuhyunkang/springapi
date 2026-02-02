package com.apiece.springboot_sns_sample.domain.quote;

public class QuoteException extends RuntimeException {

    public QuoteException(String message) {
        super(message);
    }

    public static class QuoteNotFoundException extends QuoteException {
        public QuoteNotFoundException(Long id) {
            super("Quote not found with id: " + id);
        }
    }

    public static class QuoteNotOwnedException extends QuoteException {
        public QuoteNotOwnedException(Long quoteId, Long userId) {
            super("Quote " + quoteId + " is not owned by user " + userId);
        }
    }
}
