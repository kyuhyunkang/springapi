package com.apiece.springboot_sns_sample.controller;

import java.net.URI;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apiece.springboot_sns_sample.config.auth.AuthUser;
import com.apiece.springboot_sns_sample.controller.dto.PostResponse;
import com.apiece.springboot_sns_sample.controller.dto.PostUpdateRequest;
import com.apiece.springboot_sns_sample.controller.dto.QuoteCreateRequest;
import com.apiece.springboot_sns_sample.domain.post.Post;
import com.apiece.springboot_sns_sample.domain.quote.QuoteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    @PostMapping("/api/quotes")
    public ResponseEntity<PostResponse> create(@Valid @RequestBody QuoteCreateRequest request, @AuthUser Long userId) {
        Post quote = quoteService.create(request.content(), request.quoteId(), userId);
        return ResponseEntity.created(URI.create("/api/quotes/" + quote.getId())).body(PostResponse.from(quote));
    }

    @GetMapping("/api/quotes/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long id) {
        Post quote = quoteService.findById(id);
        return ResponseEntity.ok(PostResponse.from(quote));
    }

    @GetMapping("/api/posts/{postId}/quotes")
    public ResponseEntity<Page<PostResponse>> findByQuoteId(@PathVariable Long postId, Pageable pageable) {
        Page<PostResponse> quotes = quoteService.findByQuoteId(postId, pageable).map(PostResponse::from);
        return ResponseEntity.ok(quotes);
    }

    @PutMapping("/api/quotes/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable Long id, @Valid @RequestBody PostUpdateRequest request,
            @AuthUser Long userId) {
        Post quote = quoteService.update(id, request.content(), userId);
        return ResponseEntity.ok(PostResponse.from(quote));
    }

    @DeleteMapping("/api/quotes/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthUser Long userId) {
        quoteService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
