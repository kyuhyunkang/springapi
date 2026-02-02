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
import com.apiece.springboot_sns_sample.controller.dto.ReplyCreateRequest;
import com.apiece.springboot_sns_sample.domain.post.Post;
import com.apiece.springboot_sns_sample.domain.reply.ReplyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/api/replies")
    public ResponseEntity<PostResponse> create(@Valid @RequestBody ReplyCreateRequest request, @AuthUser Long userId) {
        Post reply = replyService.create(request.content(), request.parentId(), userId);
        return ResponseEntity.created(URI.create("/api/replies/" + reply.getId())).body(PostResponse.from(reply));
    }

    @GetMapping("/api/replies/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long id) {
        Post reply = replyService.findById(id);
        return ResponseEntity.ok(PostResponse.from(reply));
    }

    @GetMapping("/api/posts/{postId}/replies")
    public ResponseEntity<Page<PostResponse>> findByParentId(@PathVariable Long postId, Pageable pageable) {
        Page<PostResponse> replies = replyService.findByParentId(postId, pageable).map(PostResponse::from);
        return ResponseEntity.ok(replies);
    }

    @PutMapping("/api/replies/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable Long id, @Valid @RequestBody PostUpdateRequest request,
            @AuthUser Long userId) {
        Post reply = replyService.update(id, request.content(), userId);
        return ResponseEntity.ok(PostResponse.from(reply));
    }

    @DeleteMapping("/api/replies/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthUser Long userId) {
        replyService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
