package uk.co.datadisk.security1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uk.co.datadisk.security1.entities.Role;
import uk.co.datadisk.security1.entities.User;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    BCryptPasswordEncoder bCryptPasswordEncoder;

    private Map<String, User> userList;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

        User user1 = new User(1000L, false, false,true, "user1");
        User user2 = new User(1001L, false, false,true,"user2");
        User user3 = new User(1002L, false, false,true,"user3");

        user1.setPassword(bCryptPasswordEncoder.encode("password1"));
        user2.setPassword(bCryptPasswordEncoder.encode("password2"));
        user3.setPassword(bCryptPasswordEncoder.encode("password3"));

        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");

        user1.addRole(adminRole);
        user2.addRole(userRole);
        user3.addRole(userRole);
        user3.addRole(adminRole);

        userList = new HashMap<>();

        userList.put("user1", user1);
        userList.put("user2", user2);
        userList.put("user3", user3);
    }

    public User getUserByUsername(String username) {
        return userList.get(username);
    }

    public Map<String, User> getUserList() {
        return userList;
    }
}
