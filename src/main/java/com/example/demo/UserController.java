package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class UserController {
    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    List<User> all() {
        return repository.findAll();
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("users/{id}")
    User one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        return repository.findById(id).map(user -> {
                                                user.setName(newUser.getName());
                                                user.setAge(newUser.getAge());
                                                return repository.save(user);
                                            }).orElseGet(() -> {
                                                                newUser.setId(id);
                                                                return repository.save(newUser);
                                                            });
    }

    @DeleteMapping("users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
