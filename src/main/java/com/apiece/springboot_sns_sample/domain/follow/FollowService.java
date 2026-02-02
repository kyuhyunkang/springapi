package com.apiece.springboot_sns_sample.domain.follow;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apiece.springboot_sns_sample.domain.user.User;
import com.apiece.springboot_sns_sample.domain.user.UserException;
import com.apiece.springboot_sns_sample.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final FollowCountRepository followCountRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(Long followerId, Long targetUserId) {
        validateNotSelfFollow(followerId, targetUserId);

        User follower = findUserById(followerId);
        User target = findUserById(targetUserId);

        validateNotAlreadyFollowing(follower, target, targetUserId);

        followRepository.save(new Follow(follower, target));

        ensureFollowCountExists(follower);
        ensureFollowCountExists(target);

        followCountRepository.incrementFolloweesCount(followerId);
        followCountRepository.incrementFollowersCount(targetUserId);
    }

    @Transactional
    public void unfollow(Long followerId, Long targetUserId) {
        validateNotSelfFollow(followerId, targetUserId);

        User follower = findUserById(followerId);
        User target = findUserById(targetUserId);

        validateFollowing(follower, target, targetUserId);

        followRepository.deleteByFollowerAndFollowing(follower, target);

        followCountRepository.decrementFolloweesCount(followerId);
        followCountRepository.decrementFollowersCount(targetUserId);
    }

    public List<User> getFollowers(Long userId) {
        User user = findUserById(userId);
        return followRepository.findByFollowing(user).stream().map(Follow::getFollower).toList();
    }

    public List<User> getFollowees(Long userId) {
        User user = findUserById(userId);
        return followRepository.findByFollower(user).stream().map(Follow::getFollowing).toList();
    }

    public Long getFollowersCount(Long userId) {
        return followCountRepository.findByUserId(userId).map(FollowCount::getFollowersCount).orElse(0L);
    }

    public Long getFolloweesCount(Long userId) {
        return followCountRepository.findByUserId(userId).map(FollowCount::getFolloweesCount).orElse(0L);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> UserException.notFound(userId));
    }

    private void validateNotSelfFollow(Long followerId, Long targetUserId) {
        if (followerId.equals(targetUserId)) {
            throw FollowException.selfFollow();
        }
    }

    private void validateNotAlreadyFollowing(User follower, User target, Long targetUserId) {
        if (followRepository.existsByFollowerAndFollowing(follower, target)) {
            throw FollowException.alreadyFollowing(targetUserId);
        }
    }

    private void validateFollowing(User follower, User target, Long targetUserId) {
        if (!followRepository.existsByFollowerAndFollowing(follower, target)) {
            throw FollowException.notFollowing(targetUserId);
        }
    }

    private void ensureFollowCountExists(User user) {
        if (followCountRepository.findByUser(user).isEmpty()) {
            followCountRepository.saveAndFlush(new FollowCount(user));
        }
    }
}
