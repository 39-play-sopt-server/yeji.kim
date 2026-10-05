package org.sopt.post.client;

import org.sopt.post.domain.Category;
import org.sopt.post.dto.response.PostResponse;

import java.util.Scanner;

public class PostView {
    private final Scanner scanner = new Scanner(System.in);

    public void printMenu() {
        System.out.println("\n=== 게시판 ===");
        System.out.println("1. 게시글 작성");
        System.out.println("2. 게시글 목록 조회");
        System.out.println("3. 게시글 단건 조회");
        System.out.println("4. 게시글 수정");
        System.out.println("5. 게시글 삭제");
        System.out.println("6. 종료");
    }

    public int readCommand() {
        System.out.print("선택: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String readTitle() {
        System.out.print("제목: ");
        return scanner.nextLine();
    }

    public String readContent() {
        System.out.print("내용: ");
        return scanner.nextLine();
    }

    public Category readCategory() {
        System.out.print("카테고리 선택(HOT, FREE, SECRET): ");
        String input = scanner.nextLine();
        return Category.valueOf(input);
    }

    public String readAuthor() {
        System.out.print("저자: ");
        return scanner.nextLine();
    }

    public int readPostNumber(String message) {
        System.out.print(message);
        return Integer.parseInt(scanner.nextLine());
    }

    public void printPost(PostResponse post) {
        System.out.println("\n=== 게시글 ===");
        System.out.println("제목: " + post.title());
        System.out.println("내용: " + post.content());
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
