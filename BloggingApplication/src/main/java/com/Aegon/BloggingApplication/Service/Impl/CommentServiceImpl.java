package com.Aegon.BloggingApplication.Service.Impl;

import com.Aegon.BloggingApplication.Entity.Comment;
import com.Aegon.BloggingApplication.Entity.Post;
import com.Aegon.BloggingApplication.Exception.BlogAPIException;
import com.Aegon.BloggingApplication.Exception.ResourceNotFoundException;
import com.Aegon.BloggingApplication.Payloads.CommentDto;
import com.Aegon.BloggingApplication.Repository.CommentRepository;
import com.Aegon.BloggingApplication.Repository.PostRepository;
import com.Aegon.BloggingApplication.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    //convert entity to dto
    public CommentDto mapToDto(Comment comment){
      CommentDto commentDto=new CommentDto();
      commentDto.setId(comment.getId());
      commentDto.setName(comment.getName());
      commentDto.setEmail(comment.getEmail());
      commentDto.setBody(comment.getBody());
      return commentDto;
    }

    //convert dto to entity
    public Comment mapToEntity(CommentDto commentDto){
        Comment comment=new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
    @Override
    public CommentDto createComment(long id, CommentDto commentDto) {

        Comment comment=mapToEntity(commentDto);

        //retrieve post entity by id
        Post post=postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",id)
        );
        comment.setPost(post);
        Comment newComment=commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments=commentRepository.findByPostId(postId);

        return comments.stream().map(comment->mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",postId)
        );
        Comment comment=commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comment","id",commentId)
        );

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComments(long postId, long commentId,CommentDto commentDto) {
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",postId)
        );
        Comment comment=commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comment","id",commentId)
        );

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        commentRepository.save(comment);
        return mapToDto(comment);
    }

    @Override
    public void deleteComments(long postId, long commentId) {
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",postId)
        );
        Comment comment=commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comment","id",commentId)
        );

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        commentRepository.delete(comment);

    }
}
