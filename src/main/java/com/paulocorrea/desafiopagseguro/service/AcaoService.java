package com.paulocorrea.desafiopagseguro.service;

import com.paulocorrea.desafiopagseguro.dto.AcaoGetDTO;
import com.paulocorrea.desafiopagseguro.dto.AcaoPostDTO;
import com.paulocorrea.desafiopagseguro.entities.AcaoEntity;
import com.paulocorrea.desafiopagseguro.enums.TipoAcao;
import com.paulocorrea.desafiopagseguro.exception.AcaoNaoEncontradaException;
import com.paulocorrea.desafiopagseguro.mapper.AcaoMapper;
import com.paulocorrea.desafiopagseguro.repository.AcaoMongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AcaoService {

    private AcaoMongoRepository acaoMongoRepository;

    private static final Logger LOG = LoggerFactory.getLogger(AcaoService.class);

    public AcaoService(AcaoMongoRepository acaoMongoRepository) {
        this.acaoMongoRepository = acaoMongoRepository;
    }

    @Transactional
    public AcaoGetDTO salvarAcao(AcaoPostDTO acaoPostDTO) {
        AcaoEntity entity = AcaoMapper.INSTANCE.toEntity(acaoPostDTO);
        String codigo = entity.getCodigo();
        entity.setTipo(TipoAcao.obterPorCodigo(codigo.substring(codigo.length() - 1)));
        return AcaoMapper.INSTANCE.toDTO(acaoMongoRepository.save(entity));
    }

    public AcaoGetDTO obterAcaoPorCodigo(String codigo) {
        AcaoEntity acaoEntity = acaoMongoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new AcaoNaoEncontradaException("Acao nao encontrada"));
        return AcaoMapper.INSTANCE.toDTO(acaoEntity);
    }

    public AcaoGetDTO obterAcaoPorNome(String nome) {
        AcaoEntity acaoEntity = acaoMongoRepository.findByNome(nome)
                .orElseThrow(() -> new AcaoNaoEncontradaException("Acao nao encontrada"));
        return AcaoMapper.INSTANCE.toDTO(acaoEntity);
    }

    public List<AcaoGetDTO> listarAcoes() {
        return AcaoMapper.INSTANCE.toDTOList(acaoMongoRepository.findAll());
    }

    @Transactional
    public void removerAcao(String id) {
        AcaoEntity acao = acaoMongoRepository.findById(id)
                .orElseThrow(() -> new AcaoNaoEncontradaException("Acao nao encontrada"));
        acaoMongoRepository.delete(acao);
    }
}
