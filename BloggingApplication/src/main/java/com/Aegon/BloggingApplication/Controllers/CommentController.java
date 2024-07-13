package com.Aegon.BloggingApplication.Controllers;

import com.Aegon.BloggingApplication.Payloads.CommentDto;
import com.Aegon.BloggingApplication.Service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable long id, @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(id,commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{id}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable long id){
        return commentService.getCommentsByPostId(id);
    }

    @GetMapping("/posts/{postId}/comments/{commentsId}")
    public ResponseEntity<CommentDto> getComments(@PathVariable long postId,@PathVariable long commentsId){
        return new ResponseEntity<>(commentService.getCommentById(postId, commentsId),HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{commentsId}")
    public ResponseEntity<CommentDto> updateComments(@PathVariable long postId,@PathVariable long commentsId,@Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.updateComments(postId, commentsId, commentDto),HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentsId}")
    public ResponseEntity<String> deleteComments(@PathVariable long postId,@PathVariable long commentsId){
       commentService.deleteComments(postId, commentsId);
       return new ResponseEntity<>("Comment deleted successfully",HttpStatus.OK);
    }
}
