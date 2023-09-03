package org.br.sistufbackend.security;

import org.br.sistufbackend.filter.CorsFilter;
import org.br.sistufbackend.service.impl.SistufUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
public class SecurityConfig {

    @Autowired
    private SistufUserDetailsServiceImpl customUserDetailsService;

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Bean
    CorsFilter corsFilter() {
        CorsFilter filter = new CorsFilter();
        return filter;
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addExposedHeader("total-size");
        configuration.setExposedHeaders(Arrays.asList("total-size"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/authorize").permitAll().
                        requestMatchers(antMatcher(HttpMethod.OPTIONS, "/**")).permitAll().
                        anyRequest().authenticated()
        ).httpBasic().authenticationEntryPoint(this.authEntryPoint);

        http.addFilterAfter(new CorsFilter(), BasicAuthenticationFilter.class);
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
