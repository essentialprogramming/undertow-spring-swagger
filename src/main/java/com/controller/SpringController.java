package com.controller;

import com.model.User;
import com.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@Api(value = "/", description = "Simple User Controller")
public class SpringController {

    private final AtomicInteger counter = new AtomicInteger();

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/users")
    @ApiOperation("Get list of all users")
    @ResponseBody
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    @ApiOperation("Register a user")
    @ResponseBody
    public User register(@RequestParam(name = "name", required = false, defaultValue = "Stranger") String name) {
        User newUser = new User(counter.incrementAndGet(), "Hello, " + name + "!");
        return userRepository.addUser(newUser);
    }

    @PutMapping("/users/{id}")
    @ApiOperation("Change name of a user")
    @ResponseBody
    public User updateUser(@PathVariable(value = "id") int id, String newName) {
        return userRepository.updateUser(id, newName).orElseThrow(() -> new EntityNotFoundException("Error! User not found!"));
    }

    @DeleteMapping("/users/{id}")
    @ApiOperation("Delete a user")
    @ResponseBody
    public Boolean deleteUser(@PathVariable(value = "id") int id) {
        return userRepository.deleteUser(id);
    }


}

 
