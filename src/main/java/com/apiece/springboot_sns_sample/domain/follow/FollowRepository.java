package com.apiece.springboot_sns_sample.domain.follow;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiece.springboot_sns_sample.domain.user.User;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowerAndFollowing(User follower, User following);

    List<Follow> findByFollower(User follower);

    List<Follow> findByFollowing(User following);

    void deleteByFollowerAndFollowing(User follower, User following);
}
