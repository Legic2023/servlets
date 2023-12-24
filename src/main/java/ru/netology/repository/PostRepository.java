package ru.netology.repository;

import ru.netology.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

// Stub
public class PostRepository {

    private List<Post> itemsList;
    private Long itemsCounter;

    public PostRepository() {
        this.itemsList = new CopyOnWriteArrayList<>();
        this.itemsCounter = 0L;

    }

    public List<Post> all() {
        return Collections.emptyList();
    }

    public Optional<Post> getById(long id) {
        return Optional.empty();
    }

    public Post save(Post post) {
        return post;
    }

    public void removeById(long id) {
        itemsList.remove((int) id - 1);
    }

    public List<Post> getItemsList() {
        return itemsList;
    }

    public void addPost(Post post) {
        itemsList.add(post);
    }

    public Long getItemsCounter() {
        return itemsCounter;
    }

    public void iterateCounter() {
        this.itemsCounter++;
    }

}
