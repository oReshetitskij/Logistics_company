package edu.netcracker.project.logistic.config.security;

import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.service.PersonService;
import edu.netcracker.project.logistic.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Autowired
    PersonService personService;

    @Autowired
    RoleService roleService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";

        Optional<Person> person = personService.findOne(username);

        if (!person.isPresent()){
            throw new BadCredentialsException("1000");
        }
        if (!passwordEncoder.matches(password, person.get().getPassword())){
            throw new BadCredentialsException("1000");
        }



        // Get user or employee roles
        List<String> userRights = new ArrayList<>();
        userRights.add(roleService.findAll());
        System.out.println(userRights);

        return new UsernamePasswordAuthenticationToken(username, password, userRights.stream().map(x -> new SimpleGrantedAuthority(x)).collect(Collectors.toList()));


    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class
        );
    }
}
