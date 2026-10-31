package org.sopt.domain.post.domain.entity;

import jakarta.persistence.*;
import org.sopt.domain.member.domain.entity.Member;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_post_like_member_post",
                columnNames = {"member_id", "post_id"}
        )
})
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    protected PostLike() {
    }

    public PostLike(Member member, Post post) {
        this.member = member;
        this.post = post;
    }

    public Member getMember() {
        return this.member;
    }

    public Post getPost() {
        return this.post;
    }
}
