package com.Aegon.BloggingApplication.Controllers;

import com.Aegon.BloggingApplication.Payloads.PostDto;
import com.Aegon.BloggingApplication.Service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    //create blog post
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //get all posts
    @GetMapping
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

    //get post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        return new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
    }

    //update post by id
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable long id){
        return new ResponseEntity<>(postService.updatePost(postDto,id),HttpStatus.OK);
    }

    //delete post by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post deleted successfully",HttpStatus.OK);
    }
}
