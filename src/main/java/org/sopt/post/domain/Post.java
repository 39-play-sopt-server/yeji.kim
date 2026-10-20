package org.sopt.post.domain;

import jakarta.persistence.*;
import org.sopt.post.domain.validator.PostValidator;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String createdAt;

    private String author;

    private long viewCount = 0;

    protected Post() {
    }

    public Post(
            String title,
            String content,
            Category category,
            String createdAt,
            String author
    ) {
        PostValidator.validate(title, content);
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

    public long getViewCount() {
        return viewCount;
    }

    public void increaseViewCount() {
        viewCount += 1;
    }
}
