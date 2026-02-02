package com.apiece.springboot_sns_sample.controller;

import java.net.URI;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apiece.springboot_sns_sample.config.auth.AuthUser;
import com.apiece.springboot_sns_sample.controller.dto.PostCreateRequest;
import com.apiece.springboot_sns_sample.controller.dto.PostResponse;
import com.apiece.springboot_sns_sample.controller.dto.PostUpdateRequest;
import com.apiece.springboot_sns_sample.domain.post.Post;
import com.apiece.springboot_sns_sample.domain.post.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/posts")
    public ResponseEntity<PostResponse> create(@Valid @RequestBody PostCreateRequest request, @AuthUser Long userId) {
        Post post = postService.create(request.content(), userId);
        return ResponseEntity.created(URI.create("/api/posts/" + post.getId())).body(PostResponse.from(post));
    }

    @GetMapping("/api/posts/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long id) {
        Post post = postService.findById(id);
        postService.incrementViewCount(id);
        return ResponseEntity.ok(PostResponse.from(post));
    }

    @GetMapping("/api/posts")
    public ResponseEntity<Page<PostResponse>> findAll(Pageable pageable) {
        Page<PostResponse> posts = postService.findAll(pageable).map(PostResponse::from);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/api/users/{userId}/posts")
    public ResponseEntity<Page<PostResponse>> findByUserId(@PathVariable Long userId, Pageable pageable) {
        Page<PostResponse> posts = postService.findByUserId(userId, pageable).map(PostResponse::from);
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/api/posts/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable Long id, @Valid @RequestBody PostUpdateRequest request,
            @AuthUser Long userId) {
        Post post = postService.update(id, request.content(), userId);
        return ResponseEntity.ok(PostResponse.from(post));
    }

    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthUser Long userId) {
        postService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
