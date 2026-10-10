package org.sopt.post.domain;

import org.sopt.post.domain.validator.PostValidator;

public class Post {
    private final Long id;
    private String title;
    private String content;
    private final Category category;
    private final String createdAt;
    private final String author;

    public Post(
            Long id,
            String title,
            String content,
            Category category,
            String createdAt,
            String author
    ) {
        PostValidator.validate(title, content);
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.createdAt = createdAt;
        this.author = author;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public Category getCategory() {
        return this.category;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public String getAuthor() {
        return this.author;
    }

    public void update(String title, String content) {
        PostValidator.validate(title, content);

        this.title = title;
        this.content = content;
    }
}
