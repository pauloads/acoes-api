package com.paulocorrea.desafiopagseguro.batch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.paulocorrea.desafiopagseguro.entities.AcaoEntity;
import com.paulocorrea.desafiopagseguro.repository.YahooFinancesRepository;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;

public class ObterValorDeMercadoItemProcessor implements ItemProcessor<AcaoEntity, AcaoEntity> {

    private YahooFinancesRepository yahooFinancesRepository = new YahooFinancesRepository();

    @Override
    public AcaoEntity process(AcaoEntity acaoEntity) throws JsonProcessingException {
        String simbolo = yahooFinancesRepository.obterSimboloDaAcao(acaoEntity.getCodigo());
        String valor = yahooFinancesRepository.obterValorDaAcao(simbolo);
        acaoEntity.setValorMedioDeMercado(new BigDecimal(valor));
        return acaoEntity;
    }
}
