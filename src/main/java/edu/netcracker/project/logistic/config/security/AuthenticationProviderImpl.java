package edu.netcracker.project.logistic.config.security;

import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.model.Role;
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


    PersonService personService;
    RoleService roleService;
    BCryptPasswordEncoder passwordEncoder;
    JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthenticationProviderImpl(PersonService personService,
                                      RoleService roleService,
                                      BCryptPasswordEncoder passwordEncoder,
                                      JdbcTemplate jdbcTemplate) {
        this.personService = personService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.jdbcTemplate = jdbcTemplate;
    }



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
        List<Role> userRights = roleService.findRolesByPersonId(person.get().getId());

        return new UsernamePasswordAuthenticationToken(username, password, userRights.stream().map(x -> new SimpleGrantedAuthority(x.getRoleName())).collect(Collectors.toList()));


    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class
        );
    }
}
