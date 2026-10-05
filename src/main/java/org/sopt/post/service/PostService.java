package org.sopt.post.service;

import org.sopt.post.domain.Category;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.repository.PostRepository;
import org.sopt.post.validator.PostValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostService {
    private final PostRepository postRepository;
    private final PostValidator postValidator;

    public PostService(
            PostRepository postRepository,
            PostValidator postValidator
    ) {
        this.postRepository = postRepository;
        this.postValidator = postValidator;
    }

    private long nextId = 1;

    public void createPost(
            String title,
            String content,
            Category category,
            String author
    ) {
        postValidator.validate(title, content);

        String createdAt = LocalDateTime.now().toString();
        Post post = new Post(nextId, title, content, category, createdAt, author);
        nextId += 1;

        postRepository.save(post);
    }

    public List<PostResponse> readPosts() {
        List<PostResponse> responses = new ArrayList<>();
        List<Post> posts = postRepository.findAll();

        for (Post post : posts) {
            PostResponse response = new PostResponse(post);
            responses.add(response);
        }

        return responses;
    }

    public PostResponse readPost(Long id) {
        Post post = postRepository.findById(id);

        if (post == null) {
            throw new PostNotFoundException("게시글이 존재하지 않습니다.");
        }

        return new PostResponse(post);
    }

    public void updatePost(Long id, String title, String content) {
        Post post = postRepository.findById(id);
        postValidator.validate(title, content);

        if (post == null) {
            throw new PostNotFoundException("게시글이 존재하지 않습니다.");
        }

        post.updateTitle(title);
        post.updateContent(content);

        postRepository.save(post);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id);

        if (post == null) {
            throw new PostNotFoundException("게시글이 존재하지 않습니다.");
        }

        postRepository.deletePost(id);
    }
}
