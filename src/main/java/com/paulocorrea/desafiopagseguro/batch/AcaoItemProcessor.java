package com.paulocorrea.desafiopagseguro.batch;

import com.paulocorrea.desafiopagseguro.entities.AcaoEntity;
import com.paulocorrea.desafiopagseguro.enums.TipoAcao;
import org.springframework.batch.item.ItemProcessor;

public class AcaoItemProcessor implements ItemProcessor<AcaoEntity, AcaoEntity> {
    @Override
    public AcaoEntity process(AcaoEntity acaoEntity) {
        String codigo = acaoEntity.getCodigo();
        TipoAcao tipoAcao = TipoAcao.obterPorCodigo(codigo.substring(codigo.length() - 1));
        acaoEntity.setTipo(tipoAcao);
        return acaoEntity;
    }
}
