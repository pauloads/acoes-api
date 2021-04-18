package com.paulocorrea.desafiopagseguro.service;

import com.paulocorrea.desafiopagseguro.dto.AcaoGetDTO;
import com.paulocorrea.desafiopagseguro.dto.AcaoPostDTO;
import com.paulocorrea.desafiopagseguro.mapper.AcaoMapper;
import com.paulocorrea.desafiopagseguro.repository.AcaoMongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcaoService {

    private AcaoMongoRepository acaoMongoRepository;

    private static final Logger LOG = LoggerFactory.getLogger(AcaoService.class);

    public AcaoService(AcaoMongoRepository acaoMongoRepository) {
        this.acaoMongoRepository = acaoMongoRepository;
    }

    @Transactional
    public AcaoGetDTO salvarAcao(AcaoPostDTO acaoPostDTO) {
        return AcaoMapper.INSTANCE.toDTO(acaoMongoRepository.save(AcaoMapper.INSTANCE.toEntity(acaoPostDTO)));
    }
}
