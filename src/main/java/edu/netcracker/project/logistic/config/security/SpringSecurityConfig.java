package edu.netcracker.project.logistic.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;



@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private AccessDeniedHandler accessDeniedHandler;
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationProvider authenticationProvider;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public SpringSecurityConfig(AccessDeniedHandler accessDeniedHandler,
                                AuthenticationSuccessHandler authenticationSuccessHandler,
                                AuthenticationProvider authenticationProvider,
                                BCryptPasswordEncoder passwordEncoder
                                ){
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationProvider = authenticationProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index", "/registration", "/registration/complete", "/registration/confirm", "/test", "/login/forgot/password").permitAll()
                .antMatchers("/employee").hasAnyRole("ADMIN", "MANAGER", "COURIER", "CALL_CENTER_AGENT")
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/manager/**").hasAnyRole("MANAGER")
                .antMatchers("/courier/**").hasAnyRole("COURIER")
                .antMatchers("/call-center-agent/**").hasAnyRole("CALL_CENTER_AGENT")
                .antMatchers("/user").hasAnyRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    @Override
    protected void configure(
            AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }
}
