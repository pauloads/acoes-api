package com.paulocorrea.desafiopagseguro.entities;

import com.paulocorrea.desafiopagseguro.enums.TipoAcao;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Document(collection = "acoes")
public class AcaoEntity {

    @Id
    private String id;

    private String codigo;

    private String nome;

    private TipoAcao tipo;

    private BigInteger quantidadeTeorica;

    private BigDecimal percentualDeParticipacao;
}
