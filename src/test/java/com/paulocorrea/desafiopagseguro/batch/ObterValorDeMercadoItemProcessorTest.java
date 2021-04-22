package com.paulocorrea.desafiopagseguro.batch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.paulocorrea.desafiopagseguro.entities.AcaoEntity;
import com.paulocorrea.desafiopagseguro.repository.YahooFinancesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ObterValorDeMercadoItemProcessorTest {

    @Mock
    private YahooFinancesRepository yahooFinancesRepository;

    @InjectMocks
    private ObterValorDeMercadoItemProcessor processor;

    @Test
    public void deveSetarValorMedioDeMercadoDeUmaAcao() throws JsonProcessingException {
        AcaoEntity acaoEntity = new AcaoEntity();
        acaoEntity.setCodigo("ABC");

        when(yahooFinancesRepository.obterSimboloDaAcao(anyString())).thenReturn("ABC");
        when(yahooFinancesRepository.obterValorDaAcao(anyString())).thenReturn("20.2");

        acaoEntity = processor.process(acaoEntity);

        assertEquals(new BigDecimal("20.2"), acaoEntity.getValorMedioDeMercado());
    }
}