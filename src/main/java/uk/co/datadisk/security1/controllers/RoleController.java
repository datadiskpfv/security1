package uk.co.datadisk.security1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.datadisk.security1.entities.Role;
import uk.co.datadisk.security1.entities.User;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/role/")
public class RoleController {

    private List<Role> roleList;

    public RoleController() {

        Role role1 = new Role("Admin");
        Role role2 = new Role("User");
        Role role3 = new Role("Backup");

        roleList = new ArrayList<>();

        roleList.add(role1);
        roleList.add(role2);
        roleList.add(role3);

    }

    @GetMapping("roleList")
    public ResponseEntity<List<Role>> getUserList() {
        return new ResponseEntity(roleList, HttpStatus.OK);
    }
}