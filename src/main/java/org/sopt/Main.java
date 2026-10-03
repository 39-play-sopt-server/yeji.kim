package org.sopt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Post> posts = new ArrayList<>();

        while (true) {
            System.out.println("\n=== 게시판 ===");
            System.out.println("1. 게시글 작성");
            System.out.println("2. 게시글 목록 조회");
            System.out.println("3. 게시글 단건 조회");
            System.out.println("4. 게시글 수정");
            System.out.println("5. 게시글 삭제");
            System.out.println("6. 종료");
            System.out.print("선택: ");

            int command = Integer.parseInt(scanner.nextLine());

            switch (command) {
                case 1:
                    // 게시글 작성
                    System.out.print("제목: ");
                    String title = scanner.nextLine();

                    System.out.print("내용: ");
                    String content = scanner.nextLine();

                    Post post = new Post(title, content);
                    posts.add(post);

                    System.out.println("게시글이 작성되었습니다.");
                    break;

                case 2:
                    // 게시글 목록 조회
                    System.out.println("\n=== 게시글 목록 ===");

                    if (posts.isEmpty()) {
                        System.out.println("게시글이 없습니다.");
                        break;
                    }

                    for (int i = 0; i < posts.size(); i++) {
                        Post currentPost = posts.get(i);

                        System.out.println(
                                (i + 1) + ". " + currentPost.title
                        );
                    }
                    break;

                case 3:
                    // 게시글 단건 조회
                    if (posts.isEmpty()) {
                        System.out.println("게시글이 없습니다.");
                        break;
                    }

                    System.out.print("조회할 게시글 번호: ");
                    int readIndex = Integer.parseInt(scanner.nextLine()) - 1;

                    if (readIndex < 0 || readIndex >= posts.size()) {
                        System.out.println("존재하지 않는 게시글입니다.");
                        break;
                    }

                    Post readPost = posts.get(readIndex);

                    System.out.println("\n=== 게시글 ===");
                    System.out.println("제목: " + readPost.title);
                    System.out.println("내용: " + readPost.content);
                    break;

                case 4:
                    // 게시글 수정
                    if (posts.isEmpty()) {
                        System.out.println("게시글이 없습니다.");
                        break;
                    }

                    System.out.print("수정할 게시글 번호: ");
                    int updateIndex = Integer.parseInt(scanner.nextLine()) - 1;

                    if (updateIndex < 0 || updateIndex >= posts.size()) {
                        System.out.println("존재하지 않는 게시글입니다.");
                        break;
                    }

                    Post updatePost = posts.get(updateIndex);

                    System.out.print("새로운 제목: ");
                    String newTitle = scanner.nextLine();

                    System.out.print("새로운 내용: ");
                    String newContent = scanner.nextLine();

                    updatePost.title = newTitle;
                    updatePost.content = newContent;

                    System.out.println("게시글이 수정되었습니다.");
                    break;

                case 5:
                    // 게시글 삭제
                    if (posts.isEmpty()) {
                        System.out.println("게시글이 없습니다.");
                        break;
                    }

                    System.out.print("삭제할 게시글 번호: ");
                    int deleteIndex = Integer.parseInt(scanner.nextLine()) - 1;

                    if (deleteIndex < 0 || deleteIndex >= posts.size()) {
                        System.out.println("존재하지 않는 게시글입니다.");
                        break;
                    }

                    posts.remove(deleteIndex);

                    System.out.println("게시글이 삭제되었습니다.");
                    break;

                case 6:
                    System.out.println("프로그램을 종료합니다.");
                    return;

                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }
}
