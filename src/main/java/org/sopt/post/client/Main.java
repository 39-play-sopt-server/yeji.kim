package org.sopt.post.client;

import org.sopt.global.response.ApiResponse;
import org.sopt.post.controller.PostController;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.repository.MemoryPostRepository;
import org.sopt.post.repository.PostRepository;
import org.sopt.post.service.PostService;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        PostView view = new PostView();
        PostRepository postRepository = new MemoryPostRepository();
        PostService postService = new PostService(postRepository);
        PostController controller = new PostController(postService);

        while (true) {
            view.printMenu();
            int command = view.readCommand();

            switch (command) {
                case 1 -> {
                    String title = view.readTitle();
                    String content = view.readContent();
                    String category = view.readCategory();
                    String author = view.readAuthor();

                    CreatePostRequest request = new CreatePostRequest(title, content, category, author);

                    ApiResponse<Void> response = controller.createPost(request);
                    view.printMessage(response.message());
                }
                case 2 -> {
                    ApiResponse<List<PostResponse>> response = controller.readPosts();
                    List<PostResponse> posts = response.data();

                    if (posts.isEmpty()) {
                        view.printMessage("게시글이 없습니다.");
                    }
                    for (int i = 0; i < posts.size(); i++) {
                        view.printMessage(posts.get(i).id() + ". " + posts.get(i).title());
                    }
                }
                case 3 -> {
                    long id = view.readPostNumber("조회할 게시글 번호: ");
                    ApiResponse<PostResponse> response = controller.readPost(id);

                    if (response.success()) {
                        PostResponse post = response.data();
                        view.printPost(post);
                    } else {
                        view.printMessage(response.message());
                    }
                }
                case 4 -> {
                    long id = view.readPostNumber("수정할 게시글 번호: ");
                    String newTitle = view.readTitle();
                    String newContent = view.readContent();

                    UpdatePostRequest request = new UpdatePostRequest(id, newTitle, newContent);
                    ApiResponse<Void> response = controller.updatePost(request);
                    view.printMessage(response.message());
                }
                case 5 -> {
                    long id = view.readPostNumber("삭제할 게시글 번호: ");

                    ApiResponse<Void> response = controller.deletePost(id);
                    view.printMessage(response.message());
                }
                case 6 -> view.printMessage("프로그램을 종료합니다.");
                default -> view.printMessage("잘못된 입력입니다.");
            }

            if (command == 6) {
                break;
            }
        }
    }
}
