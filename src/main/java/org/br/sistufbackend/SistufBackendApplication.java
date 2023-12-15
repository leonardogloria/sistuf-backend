package org.br.sistufbackend;

import org.br.sistufbackend.model.Usuario;
import org.br.sistufbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class SistufBackendApplication {
    @Autowired
    UsuarioService usuarioService;
    @Bean
    public void configure(){

    }
    public static void main(String[] args) {
        SpringApplication.run(SistufBackendApplication.class, args);
    }

}
