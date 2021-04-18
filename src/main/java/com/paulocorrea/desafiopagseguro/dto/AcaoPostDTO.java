package com.paulocorrea.desafiopagseguro.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcaoPostDTO {

    private String codigo;

    private String nome;

    private String quantidadeTeorica;

    private String percentualDeParticipacao;
}
