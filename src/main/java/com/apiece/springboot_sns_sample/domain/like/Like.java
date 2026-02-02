package com.apiece.springboot_sns_sample.domain.like;

import jakarta.persistence.*;

import com.apiece.springboot_sns_sample.domain.common.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    public Like(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
