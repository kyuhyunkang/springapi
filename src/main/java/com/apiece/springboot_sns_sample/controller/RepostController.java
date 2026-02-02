package com.apiece.springboot_sns_sample.controller;

import java.net.URI;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apiece.springboot_sns_sample.config.auth.AuthUser;
import com.apiece.springboot_sns_sample.controller.dto.PostResponse;
import com.apiece.springboot_sns_sample.controller.dto.RepostCreateRequest;
import com.apiece.springboot_sns_sample.domain.post.Post;
import com.apiece.springboot_sns_sample.domain.repost.RepostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RepostController {

    private final RepostService repostService;

    @PostMapping("/api/reposts")
    public ResponseEntity<PostResponse> create(@Valid @RequestBody RepostCreateRequest request, @AuthUser Long userId) {
        Post repost = repostService.create(request.repostId(), userId);
        return ResponseEntity.created(URI.create("/api/reposts/" + repost.getId())).body(PostResponse.from(repost));
    }

    @GetMapping("/api/reposts/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long id) {
        Post repost = repostService.findById(id);
        return ResponseEntity.ok(PostResponse.from(repost));
    }

    @GetMapping("/api/posts/{postId}/reposts")
    public ResponseEntity<Page<PostResponse>> findByRepostId(@PathVariable Long postId, Pageable pageable) {
        Page<PostResponse> reposts = repostService.findByRepostId(postId, pageable).map(PostResponse::from);
        return ResponseEntity.ok(reposts);
    }

    @DeleteMapping("/api/reposts/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthUser Long userId) {
        repostService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
