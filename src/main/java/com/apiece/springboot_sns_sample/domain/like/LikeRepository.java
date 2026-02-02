package com.apiece.springboot_sns_sample.domain.like;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserIdAndPostId(Long userId, Long postId);

    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);

    @Modifying
    void deleteByUserIdAndPostId(Long userId, Long postId);
}
