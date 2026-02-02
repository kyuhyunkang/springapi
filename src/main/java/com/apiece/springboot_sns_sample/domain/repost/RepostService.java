package com.apiece.springboot_sns_sample.domain.repost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apiece.springboot_sns_sample.domain.post.Post;
import com.apiece.springboot_sns_sample.domain.post.PostException;
import com.apiece.springboot_sns_sample.domain.post.PostRepository;
import com.apiece.springboot_sns_sample.domain.user.User;
import com.apiece.springboot_sns_sample.domain.user.UserException;
import com.apiece.springboot_sns_sample.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RepostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Post create(Long repostId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> UserException.notFound(userId));

        Post originalPost = postRepository.findById(repostId)
                .orElseThrow(() -> new PostException.PostNotFoundException(repostId));

        if (originalPost.isDeleted()) {
            throw new PostException.PostNotFoundException(repostId);
        }

        if (postRepository.existsByUserIdAndRepostId(userId, repostId)) {
            throw new RepostException.AlreadyRepostedException(repostId, userId);
        }

        Post repost = Post.createRepost(user, repostId);
        Post savedRepost = postRepository.save(repost);
        postRepository.incrementRepostCount(repostId);

        return savedRepost;
    }

    @Transactional(readOnly = true)
    public Page<Post> findByRepostId(Long repostId, Pageable pageable) {
        return postRepository.findByRepostIdAndDeletedAtIsNull(repostId, pageable);
    }

    @Transactional(readOnly = true)
    public Post findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RepostException.RepostNotFoundException(id));
        if (post.isDeleted() || !post.isRepost()) {
            throw new RepostException.RepostNotFoundException(id);
        }
        return post;
    }

    @Transactional
    public void delete(Long id, Long userId) {
        Post repost = findById(id);
        validateOwnership(repost, userId);

        postRepository.findById(repost.getRepostId()).filter(p -> !p.isDeleted())
                .ifPresent(p -> postRepository.decrementRepostCount(p.getId()));

        repost.delete();
    }

    private void validateOwnership(Post repost, Long userId) {
        if (repost.getUser() == null || !repost.getUser().getId().equals(userId)) {
            throw new RepostException.RepostNotOwnedException(repost.getId(), userId);
        }
    }
}
