package org.br.sistufbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import org.br.sistufbackend.model.GRU;
import org.br.sistufbackend.model.GRUIsenta;
import org.br.sistufbackend.model.payload.GRUFilters;
import org.br.sistufbackend.service.GruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/gru")
@CrossOrigin(origins = "*")
public class GRUController {
    @Autowired
    GruService gruService;
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        try{
            GRU gru = gruService.getById(id).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.ok(gru);
        }catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));
        }
    }
    @GetMapping("/filter")
    public ResponseEntity filter(@RequestParam Optional<String> status,
                                 @RequestParam Optional<LocalDate> emissaoStart,
                                 @RequestParam Optional<LocalDate> emissaoEnd,
                                 @RequestParam Optional<LocalDate> vencimentoStart,
                                 @RequestParam Optional<LocalDate> vencimentoEnd){
        GRUFilters gruFilters = new GRUFilters(status,emissaoStart,emissaoEnd,vencimentoStart,vencimentoEnd);
        List withFilter = null;
        if(status.isPresent() && status.get().equals("ISENTA")){
            withFilter = gruService.getIsentaWithFilters(gruFilters);
        }else {
            withFilter = gruService.getWithFilters(gruFilters);

        }
        return ResponseEntity.ok(withFilter);
    }
    @GetMapping("/{id}/isentar")
    public ResponseEntity isentarGru(@PathVariable Long id){
        try{
            GRU gru = gruService.getById(id).orElseThrow(EntityNotFoundException::new);
            gruService.isentarGru(id);
            return ResponseEntity.ok(gru);
        }catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Mensagem", "Não Localizado"));
        }
    }

}
