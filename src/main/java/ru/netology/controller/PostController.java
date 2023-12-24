package ru.netology.controller;

import com.google.gson.Gson;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;


public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;
    final Gson gson = new Gson();


    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data = service.all();
        response.getWriter().print(gson.toJson(data));
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        // если элемент есть в репозитории, считываем и печатаем
        if (service.getRepository().getItemsList()
                .stream()
                .anyMatch(c -> c.getId() == id - 1)) {
            response.getWriter().print(service.getRepository().getItemsList().get((int) id - 1).toString());
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var post = gson.fromJson(body, Post.class);
        final var data = service.save(post);

        if (data.getId() == 0) {
            service.getRepository().addPost(new Post(service.getRepository().getItemsCounter(), data.getContent()));
            service.getRepository().iterateCounter();

        } else if (service.getRepository().getItemsList()
                .stream()
                .anyMatch(c -> c.getId() == data.getId())) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
        response.getWriter().print(gson.toJson(data));
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        // если элемент есть в репозитории, то удаляем
        if (service.getRepository().getItemsList()
                .stream()
                .anyMatch(c -> c.getId() == id - 1)) {
            service.removeById(id);
            response.getWriter().print("Пост " + id + " удален!");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }

}
