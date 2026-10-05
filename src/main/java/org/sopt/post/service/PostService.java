package org.sopt.post.service;

import org.sopt.post.domain.Category;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostService {
    private final PostRepository postRepository;

    public PostService(
            PostRepository postRepository
    ) {
        this.postRepository = postRepository;
    }

    private long nextId = 1;

    public void createPost(
            String title,
            String content,
            Category category,
            String author
    ) {
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

        return new PostResponse(post);
    }

    public void updatePost(Long id, String title, String content) {
        Post post = postRepository.findById(id);

        post.updateTitle(title);
        post.updateContent(content);

        postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deletePost(id);
    }
}
