package com.paulocorrea.desafiopagseguro.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcaoGetDTO {

    private String id;

    private String codigo;

    private String nome;

    private String tipo;

    private String quantidadeTeorica;

    private String percentualDeParticipacao;

}
