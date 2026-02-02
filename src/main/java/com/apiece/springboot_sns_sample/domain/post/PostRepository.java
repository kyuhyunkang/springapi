package com.apiece.springboot_sns_sample.domain.post;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"user"})
    Page<Post> findByUserIdAndDeletedAtIsNull(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Page<Post> findByDeletedAtIsNullAndParentIdIsNullAndQuoteIdIsNullAndRepostIdIsNull(Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Optional<Post> findWithUserById(Long id);

    @Modifying
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.id = :id")
    void incrementViewCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.repostCount = p.repostCount + 1 WHERE p.id = :id")
    void incrementRepostCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.repostCount = p.repostCount - 1 WHERE p.id = :id AND p.repostCount > 0")
    void decrementRepostCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.quoteCount = p.quoteCount + 1 WHERE p.id = :id")
    void incrementQuoteCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.quoteCount = p.quoteCount - 1 WHERE p.id = :id AND p.quoteCount > 0")
    void decrementQuoteCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.likeCount = p.likeCount + 1 WHERE p.id = :id")
    void incrementLikeCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.likeCount = p.likeCount - 1 WHERE p.id = :id AND p.likeCount > 0")
    void decrementLikeCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.replyCount = p.replyCount + 1 WHERE p.id = :id")
    void incrementReplyCount(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.replyCount = p.replyCount - 1 WHERE p.id = :id AND p.replyCount > 0")
    void decrementReplyCount(@Param("id") Long id);

    @EntityGraph(attributePaths = {"user"})
    Page<Post> findByParentIdAndDeletedAtIsNull(Long parentId, Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Page<Post> findByQuoteIdAndDeletedAtIsNull(Long quoteId, Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Page<Post> findByRepostIdAndDeletedAtIsNull(Long repostId, Pageable pageable);

    boolean existsByUserIdAndRepostId(Long userId, Long repostId);
}
