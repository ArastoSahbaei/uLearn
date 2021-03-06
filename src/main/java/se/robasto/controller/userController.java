package se.robasto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.robasto.model.UserModel;
import se.robasto.service.UserService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class userController {


    @Autowired
    private final UserService userService;

    public userController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserModel> RegisterNewUser(@RequestBody UserModel userModel) {
        UserModel newUserModel = userService.createNewUser(userModel);
        return new ResponseEntity<>(newUserModel, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> resultList = userService.findAllUsers();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.FindUserById(id), HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserModel> getUserByUsername(@PathVariable String username) {
        return new ResponseEntity(userService.FindUserByUserName(username), HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

}
