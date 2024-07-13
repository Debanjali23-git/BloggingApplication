package com.Aegon.BloggingApplication.Service.Impl;

import com.Aegon.BloggingApplication.Entity.Post;
import com.Aegon.BloggingApplication.Exception.ResourceNotFoundException;
import com.Aegon.BloggingApplication.Payloads.PostDto;
import com.Aegon.BloggingApplication.Repository.PostRepository;
import com.Aegon.BloggingApplication.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    //convert entity to DTO
    private PostDto mapToDto(Post post){
        PostDto postDto=new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    //convert postDTO to entity
    private Post mapToEntity(PostDto postDto){
        Post post=new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
    @Override
    public PostDto createPost(PostDto postDto) {

        //convert DTO to entity
        Post post=mapToEntity(postDto);

        Post newPost=postRepository.save(post);

        //convert entity to DTO
        PostDto postDto1=mapToDto(newPost);

        return postDto1;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> list=postRepository.findAll();
       return list.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

    }

    @Override
    public PostDto getPostById(long id) {
        Post post=postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",id)
        );
        PostDto postDto=mapToDto(post);

        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post=postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",id)
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post post1=postRepository.save(post);
        PostDto postDto1=mapToDto(post1);
        return postDto1;
    }

    @Override
    public void deletePostById(long id) {
        Post post=postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",id)
        );
        postRepository.deleteById(id);
    }


}
