package com.apiece.springboot_sns_sample.domain.quote;

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
public class QuoteService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Post create(String content, Long quoteId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> UserException.notFound(userId));

        Post quotedPost = postRepository.findById(quoteId)
                .orElseThrow(() -> new PostException.PostNotFoundException(quoteId));

        if (quotedPost.isDeleted()) {
            throw new PostException.PostNotFoundException(quoteId);
        }

        Post quote = Post.createQuote(content, user, quoteId);
        Post savedQuote = postRepository.save(quote);
        postRepository.incrementQuoteCount(quoteId);

        return savedQuote;
    }

    @Transactional(readOnly = true)
    public Page<Post> findByQuoteId(Long quoteId, Pageable pageable) {
        return postRepository.findByQuoteIdAndDeletedAtIsNull(quoteId, pageable);
    }

    @Transactional(readOnly = true)
    public Post findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new QuoteException.QuoteNotFoundException(id));
        if (post.isDeleted() || !post.isQuote()) {
            throw new QuoteException.QuoteNotFoundException(id);
        }
        return post;
    }

    @Transactional
    public Post update(Long id, String content, Long userId) {
        Post quote = findById(id);
        validateOwnership(quote, userId);
        quote.updateContent(content);
        return quote;
    }

    @Transactional
    public void delete(Long id, Long userId) {
        Post quote = findById(id);
        validateOwnership(quote, userId);

        postRepository.findById(quote.getQuoteId()).filter(p -> !p.isDeleted())
                .ifPresent(p -> postRepository.decrementQuoteCount(p.getId()));

        quote.delete();
    }

    private void validateOwnership(Post quote, Long userId) {
        if (quote.getUser() == null || !quote.getUser().getId().equals(userId)) {
            throw new QuoteException.QuoteNotOwnedException(quote.getId(), userId);
        }
    }
}
