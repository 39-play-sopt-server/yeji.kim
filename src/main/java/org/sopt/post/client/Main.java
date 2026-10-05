package org.sopt.post.client;

import org.sopt.post.controller.PostController;
import org.sopt.post.repository.MemoryPostRepository;
import org.sopt.post.service.PostService;
import org.sopt.post.validator.PostValidator;

public class Main {

    public static void main(String[] args) {
        PostView view = new PostView();
        MemoryPostRepository memoryPostRepository = new MemoryPostRepository();
        PostValidator postValidator = new PostValidator();
        PostService postService = new PostService(memoryPostRepository, postValidator);
        PostController controller = new PostController(view, postService);
        controller.run();
    }
}
