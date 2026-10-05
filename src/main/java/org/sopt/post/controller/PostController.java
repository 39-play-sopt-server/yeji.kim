package org.sopt.post.controller;

import org.sopt.post.domain.Category;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.service.PostService;

import java.util.List;

public class PostController {
    private final PostService postService;

    public PostController(
            PostService postService
    ) {
        this.postService = postService;
    }

    public void createPost(
            String title,
            String content,
            Category category,
            String author
    ) {
        postService.createPost(title, content, category, author);
    }

    public List<PostResponse> readPosts() {
        return postService.readPosts();
    }

    public PostResponse readPost(Long id) {
        return postService.readPost(id);
    }

    public void updatePost(Long id, String title, String content) {
        postService.updatePost(id, title, content);
    }

    public void deletePost(Long id) {
        postService.deletePost(id);
    }
}