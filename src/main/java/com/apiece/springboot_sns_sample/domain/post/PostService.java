package com.apiece.springboot_sns_sample.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apiece.springboot_sns_sample.domain.user.User;
import com.apiece.springboot_sns_sample.domain.user.UserException;
import com.apiece.springboot_sns_sample.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post create(String content, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> UserException.notFound(userId));
        Post post = new Post(content, user);
        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Post findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostException.PostNotFoundException(id));
        if (post.isDeleted()) {
            throw new PostException.PostNotFoundException(id);
        }
        return post;
    }

    @Transactional(readOnly = true)
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findByDeletedAtIsNullAndParentIdIsNullAndQuoteIdIsNullAndRepostIdIsNull(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Post> findByUserId(Long userId, Pageable pageable) {
        return postRepository.findByUserIdAndDeletedAtIsNull(userId, pageable);
    }

    @Transactional
    public Post update(Long id, String content, Long userId) {
        Post post = findById(id);
        validateOwnership(post, userId);
        post.updateContent(content);
        return post;
    }

    @Transactional
    public void delete(Long id, Long userId) {
        Post post = findById(id);
        validateOwnership(post, userId);
        post.delete();
    }

    @Transactional
    public void incrementViewCount(Long id) {
        postRepository.incrementViewCount(id);
    }

    private void validateOwnership(Post post, Long userId) {
        if (post.getUser() == null || !post.getUser().getId().equals(userId)) {
            throw new PostException.PostNotOwnedException(post.getId(), userId);
        }
    }
}
