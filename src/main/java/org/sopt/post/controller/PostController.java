package org.sopt.post.controller;

import org.sopt.post.client.PostView;
import org.sopt.post.domain.Category;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.exception.PostValidationException;
import org.sopt.post.service.PostService;

import java.util.List;

public class PostController {
    private final PostView view;
    private final PostService postService;

    public PostController(
            PostView view,
            PostService postService
    ) {
        this.view = view;
        this.postService = postService;
    }

    public void run() {
        while (true) {
            view.printMenu();
            int command = view.readCommand();
            try {
                switch (command) {
                    case 1 -> createPost();
                    case 2 -> readPosts();
                    case 3 -> readPost();
                    case 4 -> updatePost();
                    case 5 -> deletePost();
                    case 6 -> {
                        view.printMessage("프로그램을 종료합니다.");
                        return;
                    }
                    default -> view.printMessage("잘못된 입력입니다.");
                }
            } catch (PostNotFoundException | PostValidationException e) {
                view.printMessage(e.getMessage());
            }
        }
    }

    private void createPost() {
        String title = view.readTitle();
        String content = view.readContent();
        Category category = view.readCategory();
        String author = view.readAuthor();

        postService.createPost(title, content, category, author);
    }

    private void readPosts() {
        List<PostResponse> posts = postService.readPosts();

        if (posts.isEmpty()) {
            view.printMessage("게시글이 없습니다.");
            return;
        }
        for (int i = 0; i < posts.size(); i++) {
            view.printMessage(posts.get(i).id() + ". " + posts.get(i).title());
        }
    }

    private void readPost() {
        long id = view.readPostNumber("조회할 게시글 번호: ");

        PostResponse post = postService.readPost(id);
        view.printPost(post);
    }

    private void updatePost() {
        long id = view.readPostNumber("수정할 게시글 번호: ");

        String newTitle = view.readTitle();
        String newContent = view.readContent();

        postService.updatePost(id, newTitle, newContent);

        view.printMessage("게시글이 수정되었습니다.");
    }

    private void deletePost() {
        long id = view.readPostNumber("삭제할 게시글 번호: ");

        postService.deletePost(id);

        view.printMessage("게시글이 삭제되었습니다.");
    }
}