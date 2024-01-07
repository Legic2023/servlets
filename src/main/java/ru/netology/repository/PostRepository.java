package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PostRepository {

    private ConcurrentHashMap<Long, Post> postsMap;
    private Long postsCounter;

    public PostRepository() {
        this.postsMap = new ConcurrentHashMap<Long, Post>();
        this.postsCounter = 0L;

    }

    public List<Post> all() {
        return new ArrayList<>(postsMap.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(postsMap.get(id));
    }

    public Post save(Post post) {
        long id = post.getId();
        if (getById(id).isPresent()) {
            return postsMap.put(id, post);
        } else if (id == 0) {
            post.setId(++postsCounter);
            return postsMap.put(postsCounter, post);
        }
        return post;
    }

    public boolean removeById(long id) {
        if (getById(id).isPresent()) {
            postsMap.remove(id);
            return true;
        } else {
            return false;
        }
    }


}
