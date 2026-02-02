package com.apiece.springboot_sns_sample.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apiece.springboot_sns_sample.config.auth.AuthUser;
import com.apiece.springboot_sns_sample.controller.dto.FollowCountResponse;
import com.apiece.springboot_sns_sample.controller.dto.FollowResponse;
import com.apiece.springboot_sns_sample.controller.dto.FollowUserResponse;
import com.apiece.springboot_sns_sample.domain.follow.FollowService;
import com.apiece.springboot_sns_sample.domain.user.User;
import com.apiece.springboot_sns_sample.domain.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final UserService userService;

    @PostMapping("/api/v1/follow/{targetUserId}")
    public ResponseEntity<FollowResponse> follow(@AuthUser Long currentUserId, @PathVariable Long targetUserId) {
        followService.follow(currentUserId, targetUserId);

        User targetUser = userService.getUserById(targetUserId);
        Long followeesCount = followService.getFolloweesCount(currentUserId);
        return ResponseEntity.ok(FollowResponse.followed(targetUserId, targetUser.getUsername(), followeesCount));
    }

    @DeleteMapping("/api/v1/follow/{targetUserId}")
    public ResponseEntity<FollowResponse> unfollow(@AuthUser Long currentUserId, @PathVariable Long targetUserId) {
        followService.unfollow(currentUserId, targetUserId);

        User targetUser = userService.getUserById(targetUserId);
        Long followeesCount = followService.getFolloweesCount(currentUserId);
        return ResponseEntity.ok(FollowResponse.unfollowed(targetUserId, targetUser.getUsername(), followeesCount));
    }

    @GetMapping("/api/v1/followers")
    public ResponseEntity<List<FollowUserResponse>> getFollowers(@AuthUser Long currentUserId) {
        List<User> followers = followService.getFollowers(currentUserId);
        return ResponseEntity.ok(FollowUserResponse.fromList(followers));
    }

    @GetMapping("/api/v1/followees")
    public ResponseEntity<List<FollowUserResponse>> getFollowees(@AuthUser Long currentUserId) {
        List<User> followees = followService.getFollowees(currentUserId);
        return ResponseEntity.ok(FollowUserResponse.fromList(followees));
    }

    @GetMapping("/api/v1/followers/count")
    public ResponseEntity<FollowCountResponse> getFollowersCount(@AuthUser Long currentUserId) {
        Long count = followService.getFollowersCount(currentUserId);
        return ResponseEntity.ok(FollowCountResponse.from(count));
    }

    @GetMapping("/api/v1/followees/count")
    public ResponseEntity<FollowCountResponse> getFolloweesCount(@AuthUser Long currentUserId) {
        Long count = followService.getFolloweesCount(currentUserId);
        return ResponseEntity.ok(FollowCountResponse.from(count));
    }
}
