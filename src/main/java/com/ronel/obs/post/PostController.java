package com.ronel.obs.post;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final JsonPlaceHolderService service;

    public PostController(JsonPlaceHolderService service) {
        this.service = service;
    }

    @GetMapping("")
    List<Post> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    Post findById(@PathVariable Integer id){
        return service.findById(id);
    };
}
