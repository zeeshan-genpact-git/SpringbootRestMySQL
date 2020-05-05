package com.example.restservices.RestServicesExample.controllers;

import com.example.restservices.RestServicesExample.DAOs.PostsServiceDAO;
import com.example.restservices.RestServicesExample.DAOs.UserServiceDAO;
import com.example.restservices.RestServicesExample.beans.Post;
import com.example.restservices.RestServicesExample.beans.UserEntity;
import com.example.restservices.RestServicesExample.utilities.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserServiceDAO userServiceDAO;

    @Autowired
    private PostsServiceDAO postsServiceDAO;


    @GetMapping(path = "/users")
    public List<UserEntity> getAllUsers(){

        return userServiceDAO.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public UserEntity getUserById(@PathVariable int id){
        Optional<UserEntity> user = userServiceDAO.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("No user found with id %s. Please search with a different id", id));
        }
        return user.get();
    }

    @PostMapping(path="/users/")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserEntity user){
        UserEntity savedUser = userServiceDAO.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("location", location.toString()); // location of new resource
        //Use below statement if only new resource's location is to be returned in the header
        //return new ResponseEntity.created(location).build();
        //Use below statement to send success message with custom headers and response status. Headers is an optional parameter
        return new ResponseEntity<>("User is created successfully", headers, HttpStatus.OK); //.created(location).build();
    }

    @DeleteMapping(path="/users/{id}")
    public String deleteUser(@PathVariable int id) {
        userServiceDAO.deleteById(id);
        return String.format("User with id %s deleted successfully !!", id);
    }

    @GetMapping(path = "/users/{id}/posts")
    public List<Post> getAllUserPosts(@PathVariable int id){
        Optional<UserEntity> user =  userServiceDAO.findById(id);
        if(!user.isPresent()) {
            throw new UserNotFoundException(String.format("No user found with id %s. Please search with a different id", id));
        }
        return user.get().getPost();
    }

    @PostMapping(path="/users/{id}/posts")
    public ResponseEntity<String> createUserPost(@PathVariable int id, @Valid @RequestBody Post post){
        Optional<UserEntity> user =  userServiceDAO.findById(id);
        if(!user.isPresent()) {
            throw new UserNotFoundException(String.format("No user found with id %s. Please search with a different id", id));
        }
        UserEntity savedUser = user.get();
        post.setUser(savedUser);
        postsServiceDAO.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("location", location.toString()); // location of new resource
        //Use below statement if only new resource's location is to be returned in the header
        //return new ResponseEntity.created(location).build();
        //Use below statement to send success message with custom headers and response status. Headers is an optional parameter
        return new ResponseEntity<>("Post created for the user successfully", headers, HttpStatus.OK); //.created(location).build();
    }


}