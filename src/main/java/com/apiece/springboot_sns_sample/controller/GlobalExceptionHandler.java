package com.apiece.springboot_sns_sample.controller;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.apiece.springboot_sns_sample.domain.follow.FollowException;
import com.apiece.springboot_sns_sample.domain.post.PostException;
import com.apiece.springboot_sns_sample.domain.quote.QuoteException;
import com.apiece.springboot_sns_sample.domain.reply.ReplyException;
import com.apiece.springboot_sns_sample.domain.repost.RepostException;
import com.apiece.springboot_sns_sample.domain.user.UserException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(String error, String message) {
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("VALIDATION_ERROR", message));
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException e) {
        String message = e.getMessage();
        if (message.contains("already exists")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("DUPLICATE_USERNAME", message));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("USER_NOT_FOUND", message));
    }

    @ExceptionHandler(FollowException.class)
    public ResponseEntity<ErrorResponse> handleFollowException(FollowException e) {
        String message = e.getMessage();
        if (message.contains("Cannot follow yourself")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("SELF_FOLLOW", message));
        }
        if (message.contains("Already following user")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("ALREADY_FOLLOWING", message));
        }
        if (message.contains("Not following user")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("NOT_FOLLOWING", message));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("FOLLOW_ERROR", message));
    }

    @ExceptionHandler(PostException.class)
    public ResponseEntity<ErrorResponse> handlePostException(PostException e) {
        String message = e.getMessage();
        if (message.contains("not owned by")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("POST_NOT_OWNED", message));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("POST_NOT_FOUND", message));
    }

    @ExceptionHandler(ReplyException.class)
    public ResponseEntity<ErrorResponse> handleReplyException(ReplyException e) {
        String message = e.getMessage();
        if (message.contains("not owned by")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("REPLY_NOT_OWNED", message));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("REPLY_NOT_FOUND", message));
    }

    @ExceptionHandler(QuoteException.class)
    public ResponseEntity<ErrorResponse> handleQuoteException(QuoteException e) {
        String message = e.getMessage();
        if (message.contains("not owned by")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("QUOTE_NOT_OWNED", message));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("QUOTE_NOT_FOUND", message));
    }

    @ExceptionHandler(RepostException.class)
    public ResponseEntity<ErrorResponse> handleRepostException(RepostException e) {
        String message = e.getMessage();
        if (message.contains("not owned by")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("REPOST_NOT_OWNED", message));
        }
        if (message.contains("already reposted")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("ALREADY_REPOSTED", message));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("REPOST_NOT_FOUND", message));
    }
}
