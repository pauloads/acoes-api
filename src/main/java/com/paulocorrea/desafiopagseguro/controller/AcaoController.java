package com.paulocorrea.desafiopagseguro.controller;

import com.paulocorrea.desafiopagseguro.dto.AcaoPostDTO;
import com.paulocorrea.desafiopagseguro.service.AcaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/acoes")
public class AcaoController {

    private AcaoService acaoService;

    public AcaoController(AcaoService acaoService) {
        this.acaoService = acaoService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AcaoPostDTO acaoPostDTO) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(acaoService.salvarAcao(acaoPostDTO).getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
