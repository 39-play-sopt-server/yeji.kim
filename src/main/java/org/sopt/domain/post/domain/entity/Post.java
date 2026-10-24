package org.sopt.domain.post.domain.entity;

import jakarta.persistence.*;
import org.sopt.global.entity.BaseTimeEntity;
import org.sopt.domain.member.domain.entity.Member;
import org.sopt.domain.post.domain.validator.PostValidator;

@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

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
            Member author
    ) {
        PostValidator.validate(title, content);
        this.title = title;
        this.content = content;
        this.category = category;
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
