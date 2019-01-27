package uk.co.datadisk.security1.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import uk.co.datadisk.security1.entities.Role;
import uk.co.datadisk.security1.entities.User;
import uk.co.datadisk.security1.services.UserService;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Component
public class BespokeAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserService userService;

    @Autowired
    HttpSession session;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        System.out.println("=============================================================================");
        System.out.println("User authentication (Principle): " + authentication.getPrincipal().toString());
        System.out.println("User authentication (Details): " + authentication.getDetails().toString());
        System.out.println("User authentication (Credential): " + authentication.getCredentials().toString());
        System.out.println("User authentication (Authorities): " + authentication.getAuthorities().toString());
        System.out.println("=============================================================================");

        User user = userService.getUserByUsername(authentication.getPrincipal().toString());

        if(user == null) return null;

        System.out.println("Found user (authentication): " + user.getPassword());

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()){
            System.out.println("Adding role (authenticator): " + role.getRoleName());
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        System.out.println(user.getUsername() + " has " + grantedAuthorities.size() + " roles.");

        // do some checking, cause an exception to not login in
        System.out.println("User password credentials match? " + bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getPassword()));

        if( !bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getPassword()) ){
                return null;
        }

        if(user.isAccountNonExpired() == true || user.isEnabled() == false){
            System.out.println("User " + user.getUsername() + " is either not expired or disabled");
            return null;
        }

        //UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), grantedAuthorities);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, user.getPassword(), grantedAuthorities);

        System.out.println("\t-------------------------------------------------------");
        System.out.println("\t " + token.toString());
        System.out.println("\t Credentials: " + token.getCredentials());
        System.out.println("\t Principle: " + token.getPrincipal().toString());
        System.out.println("\t-------------------------------------------------------");

        session.setAttribute("roles", user.getRoles());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("id", user.getId());

        return token;
    }

    @Override
    public boolean supports(Class<?> auth) {
        return UsernamePasswordAuthenticationToken.class.equals(auth);
    }
}