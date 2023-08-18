package org.br.sistufbackend.security;

import org.br.sistufbackend.service.impl.SistufUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private SistufUserDetailsServiceImpl customUserDetailsService;

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Autowired
    private AuthEntryPoint authEntryPoint;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests((authorize) ->
                authorize.anyRequest().authenticated()
        ).httpBasic().authenticationEntryPoint(this.authEntryPoint);
        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder manager = http.getSharedObject(AuthenticationManagerBuilder.class);
        manager.userDetailsService(this.customUserDetailsService);
        manager.authenticationProvider(this.authenticationProvider);
        return manager.build();
    }

}
