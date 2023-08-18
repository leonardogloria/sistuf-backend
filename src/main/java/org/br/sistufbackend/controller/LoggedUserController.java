package org.br.sistufbackend.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/logged/user")
public class LoggedUserController {

    @GetMapping
    public UserDetails getUser(
            @AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }
}
