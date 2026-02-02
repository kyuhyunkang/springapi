package com.apiece.springboot_sns_sample.domain.post;

import jakarta.persistence.*;

import com.apiece.springboot_sns_sample.domain.common.BaseEntity;
import com.apiece.springboot_sns_sample.domain.user.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    private static final String REPOST_EMPTY_CONTENT = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long parentId;

    private Long quoteId;

    private Long repostId;

    @Column(nullable = false)
    private Integer repostCount = 0;

    @Column(nullable = false)
    private Integer quoteCount = 0;

    @Column(nullable = false)
    private Integer likeCount = 0;

    @Column(nullable = false)
    private Integer replyCount = 0;

    @Column(nullable = false)
    private Long viewCount = 0L;

    public Post(String content, User user) {
        this.content = content;
        this.user = user;
    }

    public static Post createReply(String content, User user, Long parentId) {
        Post post = new Post(content, user);
        post.parentId = parentId;
        return post;
    }

    public static Post createQuote(String content, User user, Long quoteId) {
        Post post = new Post(content, user);
        post.quoteId = quoteId;
        return post;
    }

    public static Post createRepost(User user, Long repostId) {
        Post post = new Post(REPOST_EMPTY_CONTENT, user);
        post.repostId = repostId;
        return post;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void incrementRepostCount() {
        this.repostCount++;
    }

    public void decrementRepostCount() {
        if (this.repostCount > 0) {
            this.repostCount--;
        }
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    public void incrementReplyCount() {
        this.replyCount++;
    }

    public void decrementReplyCount() {
        if (this.replyCount > 0) {
            this.replyCount--;
        }
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public boolean isReply() {
        return this.parentId != null;
    }

    public boolean isQuote() {
        return this.quoteId != null;
    }

    public boolean isRepost() {
        return this.repostId != null;
    }
}
