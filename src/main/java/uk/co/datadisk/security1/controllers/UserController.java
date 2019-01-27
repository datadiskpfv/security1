package uk.co.datadisk.security1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.datadisk.security1.entities.User;
import uk.co.datadisk.security1.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    public UserController() { }

    @GetMapping("/userList")
    public ResponseEntity<List<User>> getUserList() {
        return new ResponseEntity(userService.getUserList(), HttpStatus.OK);
    }
}