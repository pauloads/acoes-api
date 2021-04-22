package com.paulocorrea.desafiopagseguro.controller;

import com.paulocorrea.desafiopagseguro.dto.AcaoGetDTO;
import com.paulocorrea.desafiopagseguro.dto.AcaoPostDTO;
import com.paulocorrea.desafiopagseguro.service.AcaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<AcaoGetDTO>> obterAcao(@RequestParam(required = false) String codigo,
                                                      @RequestParam(required = false) String nome) {
        List<AcaoGetDTO> dtos = new ArrayList<>();
        if (StringUtils.hasText(codigo)) {
            dtos.add(acaoService.obterAcaoPorCodigo(codigo));
        } else if (StringUtils.hasText(nome)) {
            dtos.add(acaoService.obterAcaoPorNome(nome));
        } else if (!StringUtils.hasText(codigo) && !StringUtils.hasText(nome)) {
            dtos = acaoService.listarAcoes();
        }
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAcao(@PathVariable("id") String id) {
        acaoService.removerAcao(id);
        return ResponseEntity.noContent().build();
    }
}
