package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private static final String GET_METHOD = "GET";
    private static final String POST_METHOD = "POST";
    private static final String DELETE_METHOD = "DELETE";
    private static final String DEFAULT_PATH = "/api/posts";
    private static final String POST_PATH_REGEX = "/api/posts/\\d+";
    private static final String SUBSTRING_BEFORE_ID = "/";

    @Override
    public void init() {
        // отдаём список пакетов, в которых нужно искать аннотированные классы
        final var context = new AnnotationConfigApplicationContext("ru.netology");

        // получаем по классу бина
        final var repository = context.getBean(PostRepository.class);
        final var service = context.getBean(PostService.class);
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            // primitive routing all
            if (method.equals(GET_METHOD) && path.equals(DEFAULT_PATH)) {
                controller.all(resp);
                return;
            }

            if (method.equals(POST_METHOD) && path.equals(DEFAULT_PATH)) {
                controller.save(req.getReader(), resp);
                return;
            }

            if (method.equals(GET_METHOD) && path.matches(POST_PATH_REGEX)) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf(SUBSTRING_BEFORE_ID) + 1));
                controller.getById(id, resp);
                return;
            }

            if (method.equals(DELETE_METHOD) && path.matches(POST_PATH_REGEX)) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf(SUBSTRING_BEFORE_ID) + 1));
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}

