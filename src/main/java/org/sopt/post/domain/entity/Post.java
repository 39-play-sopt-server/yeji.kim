package org.sopt.post.domain.entity;

import jakarta.persistence.*;
import org.sopt.member.domain.entity.Member;
import org.sopt.post.domain.validator.PostValidator;

import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Member author;

    private long viewCount = 0;

    protected Post() {
    }

    public Post(
            String title,
            String content,
            Category category,
            LocalDateTime createdAt,
            Member author
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

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Member getAuthor() {
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
