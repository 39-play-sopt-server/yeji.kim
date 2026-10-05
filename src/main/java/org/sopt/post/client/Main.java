package org.sopt.post.client;

import org.sopt.post.controller.PostController;
import org.sopt.post.repository.MemoryPostRepository;
import org.sopt.post.service.PostService;

public class Main {

    public static void main(String[] args) {
        PostView view = new PostView();
        MemoryPostRepository memoryPostRepository = new MemoryPostRepository();
        PostService postService = new PostService(memoryPostRepository);
        PostController controller = new PostController(view, postService);
        controller.run();
    }
}
