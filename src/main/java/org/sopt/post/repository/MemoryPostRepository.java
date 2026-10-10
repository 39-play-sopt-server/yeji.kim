package org.sopt.post.repository;

import org.sopt.post.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class MemoryPostRepository implements PostRepository {
    private final HashMap<Long, Post> posts = new HashMap<>();

    @Override
    public void save(Post post) {
        posts.put(post.getId(), post);
    }

    @Override
    public Post findById(Long id) {
        return posts.get(id);
    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(posts.values());
    }

    @Override
    public List<Post> findPage(int offset, int limit) {
        List<Post> sortedPosts = findAll();
        sortedPosts.sort((a, b) -> b.getId().compareTo(a.getId()));

        if (offset >= sortedPosts.size()) { return List.of(); }

        int end = Math.min(offset + limit, sortedPosts.size());

        return sortedPosts.subList(offset, end);
    }

    @Override
    public void updatePost(Post post) {
        posts.put(post.getId(), post);
    }

    @Override
    public void deletePost(Long id) {
        posts.remove(id);
    }
}
