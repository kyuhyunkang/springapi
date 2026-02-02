package com.apiece.springboot_sns_sample.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apiece.springboot_sns_sample.config.auth.AuthUser;
import com.apiece.springboot_sns_sample.controller.dto.LikeResponse;
import com.apiece.springboot_sns_sample.domain.like.LikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/api/v1/posts/{postId}/like")
    public ResponseEntity<LikeResponse> like(@AuthUser Long userId, @PathVariable Long postId) {
        int likeCount = likeService.like(userId, postId);
        return ResponseEntity.ok(LikeResponse.liked(postId, likeCount));
    }

    @DeleteMapping("/api/v1/posts/{postId}/like")
    public ResponseEntity<LikeResponse> unlike(@AuthUser Long userId, @PathVariable Long postId) {
        int likeCount = likeService.unlike(userId, postId);
        return ResponseEntity.ok(LikeResponse.unliked(postId, likeCount));
    }

    @GetMapping("/api/v1/posts/{postId}/like")
    public ResponseEntity<LikeResponse> getLikeStatus(@AuthUser Long userId, @PathVariable Long postId) {
        LikeService.LikeStatus status = likeService.getLikeStatus(userId, postId);
        return ResponseEntity.ok(new LikeResponse(postId, status.liked(), status.likeCount()));
    }
}
