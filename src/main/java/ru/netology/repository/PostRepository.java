package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
@Repository
public class PostRepository {
    private final CopyOnWriteArrayList<Post> repository;
    private final AtomicLong counter;

    public PostRepository() {
        repository = new CopyOnWriteArrayList<>();
        counter = new AtomicLong(0);
    }

    public List<Post> all() {
        List<Post> listWithoutRemoved = new ArrayList<>();
        for(Post e: repository){
            if(!e.isRemoved())listWithoutRemoved.add(e);
        }
        return listWithoutRemoved;
    }

    public Optional<Post> getById(long id) {
        Optional<Post> p = null;
        for (Post post : repository) {
            if (post.getId() == id && !post.isRemoved()) {
                p = Optional.of(post);
            }
        }
        return p;
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(counter.incrementAndGet());
            repository.add(post);
            return post;
        }
        for (Post p : repository) {
            if (p.getId() == post.getId() && !p.isRemoved()) {
                p.setContent(post.getContent());
                return p;
            }
        }
        post.setId(counter.incrementAndGet());
        repository.add(post);
        return post;
    }

    public void removeById(long id) {
        for (Post post : repository) {
            if (post.getId() == id) {
                repository.remove(post);
            }
        }
    }
}
