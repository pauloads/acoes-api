package com.paulocorrea.desafiopagseguro.batch;

import com.paulocorrea.desafiopagseguro.entities.AcaoEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.paulocorrea.desafiopagseguro.enums.TipoAcao.ORDINARIA;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class AcaoItemProcessorTest {

    @Test
    public void deveSetarTipoAcaoCorretamente(){
        AcaoEntity acaoEntity = new AcaoEntity();
        acaoEntity.setCodigo("ABEV3");
        acaoEntity.setNome("AMBEV S/A ON");
        acaoEntity.setQuantidadeTeorica(new BigInteger("4355174839"));
        acaoEntity.setPercentualDeParticipacao(new BigDecimal("3.096"));

        AcaoItemProcessor acaoItemProcessor = new AcaoItemProcessor();
        AcaoEntity acaoEntityActual = acaoItemProcessor.process(acaoEntity);

        assertEquals(ORDINARIA, acaoEntityActual.getTipo());

    }
}