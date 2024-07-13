package com.Aegon.BloggingApplication.Service;

import com.Aegon.BloggingApplication.Entity.Comment;
import com.Aegon.BloggingApplication.Payloads.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long id, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);
    CommentDto getCommentById(long postId,long commentId);
    CommentDto updateComments(long postId,long commentId,CommentDto commentDto);
    void deleteComments(long postId,long commentId);
}
