package com.apiece.springboot_sns_sample.domain.reply;

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
public class ReplyService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Post create(String content, Long parentId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> UserException.notFound(userId));

        Post parentPost = postRepository.findById(parentId)
                .orElseThrow(() -> new PostException.PostNotFoundException(parentId));

        if (parentPost.isDeleted()) {
            throw new PostException.PostNotFoundException(parentId);
        }

        Post reply = Post.createReply(content, user, parentId);
        Post savedReply = postRepository.save(reply);
        postRepository.incrementReplyCount(parentId);

        return savedReply;
    }

    @Transactional(readOnly = true)
    public Page<Post> findByParentId(Long parentId, Pageable pageable) {
        return postRepository.findByParentIdAndDeletedAtIsNull(parentId, pageable);
    }

    @Transactional(readOnly = true)
    public Post findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ReplyException.ReplyNotFoundException(id));
        if (post.isDeleted() || !post.isReply()) {
            throw new ReplyException.ReplyNotFoundException(id);
        }
        return post;
    }

    @Transactional
    public Post update(Long id, String content, Long userId) {
        Post reply = findById(id);
        validateOwnership(reply, userId);
        reply.updateContent(content);
        return reply;
    }

    @Transactional
    public void delete(Long id, Long userId) {
        Post reply = findById(id);
        validateOwnership(reply, userId);

        postRepository.findById(reply.getParentId()).filter(p -> !p.isDeleted())
                .ifPresent(p -> postRepository.decrementReplyCount(p.getId()));

        reply.delete();
    }

    private void validateOwnership(Post reply, Long userId) {
        if (reply.getUser() == null || !reply.getUser().getId().equals(userId)) {
            throw new ReplyException.ReplyNotOwnedException(reply.getId(), userId);
        }
    }
}
