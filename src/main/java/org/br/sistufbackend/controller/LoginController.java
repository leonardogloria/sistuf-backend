package org.br.sistufbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/authorize")
public class LoginController {

    @PostMapping
    public ResponseEntity doLogin() {
        return ResponseEntity.noContent().build();
    }

}
