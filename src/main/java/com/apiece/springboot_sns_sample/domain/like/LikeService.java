package com.apiece.springboot_sns_sample.domain.like;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apiece.springboot_sns_sample.domain.post.Post;
import com.apiece.springboot_sns_sample.domain.post.PostException;
import com.apiece.springboot_sns_sample.domain.post.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Transactional
    public int like(Long userId, Long postId) {
        Post post = findPostById(postId);
        validateNotAlreadyLiked(userId, postId);

        likeRepository.save(new Like(userId, postId));
        post.incrementLikeCount();
        return post.getLikeCount();
    }

    @Transactional
    public int unlike(Long userId, Long postId) {
        Post post = findPostById(postId);
        validateAlreadyLiked(userId, postId);

        likeRepository.deleteByUserIdAndPostId(userId, postId);
        post.decrementLikeCount();
        return post.getLikeCount();
    }

    @Transactional(readOnly = true)
    public LikeStatus getLikeStatus(Long userId, Long postId) {
        Post post = findPostById(postId);
        boolean liked = likeRepository.existsByUserIdAndPostId(userId, postId);
        return new LikeStatus(liked, post.getLikeCount());
    }

    public record LikeStatus(boolean liked, int likeCount) {
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new PostException.PostNotFoundException(postId));
    }

    private void validateNotAlreadyLiked(Long userId, Long postId) {
        if (likeRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new LikeException.AlreadyLikedException(postId);
        }
    }

    private void validateAlreadyLiked(Long userId, Long postId) {
        if (!likeRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new LikeException.NotLikedException(postId);
        }
    }
}
