package org.sopt.post.client;

import org.sopt.post.controller.PostController;
import org.sopt.post.domain.Category;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.exception.PostValidationException;
import org.sopt.post.repository.MemoryPostRepository;
import org.sopt.post.service.PostService;
import org.sopt.post.validator.PostValidator;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        PostView view = new PostView();
        MemoryPostRepository memoryPostRepository = new MemoryPostRepository();
        PostValidator postValidator = new PostValidator();
        PostService postService = new PostService(memoryPostRepository, postValidator);
        PostController controller = new PostController(postService);

        while (true) {
            try {
                view.printMenu();
                int command = view.readCommand();

                switch (command) {
                    case 1 -> {
                        String title = view.readTitle();
                        String content = view.readContent();
                        Category category = view.readCategory();
                        String author = view.readAuthor();

                        controller.createPost(title, content, category, author);
                    }
                    case 2 -> {
                        List<PostResponse> posts = controller.readPosts();

                        if (posts.isEmpty()) {
                            view.printMessage("게시글이 없습니다.");
                        }
                        for (int i = 0; i < posts.size(); i++) {
                            view.printMessage(posts.get(i).id() + ". " + posts.get(i).title());
                        }
                    }
                    case 3 -> {
                        long id = view.readPostNumber("조회할 게시글 번호: ");
                        PostResponse post = controller.readPost(id);
                        view.printPost(post);
                    }
                    case 4 -> {
                        long id = view.readPostNumber("수정할 게시글 번호: ");
                        String newTitle = view.readTitle();
                        String newContent = view.readContent();
                        controller.updatePost(id, newTitle, newContent);
                        view.printMessage("게시글이 수정되었습니다.");
                    }
                    case 5 -> {
                        long id = view.readPostNumber("삭제할 게시글 번호: ");
                        controller.deletePost(id);
                        view.printMessage("게시글이 삭제되었습니다.");
                    }
                    case 6 -> view.printMessage("프로그램을 종료합니다.");
                    default -> view.printMessage("잘못된 입력입니다.");
                }

                if (command == 6) {
                    break;
                }
            } catch (PostNotFoundException | PostValidationException e) {
                view.printMessage(e.getMessage());
            }
        }
    }
}
