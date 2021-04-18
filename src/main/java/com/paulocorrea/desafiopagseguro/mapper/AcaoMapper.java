package com.paulocorrea.desafiopagseguro.mapper;

import com.paulocorrea.desafiopagseguro.dto.AcaoGetDTO;
import com.paulocorrea.desafiopagseguro.dto.AcaoPostDTO;
import com.paulocorrea.desafiopagseguro.entities.AcaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AcaoMapper {

    AcaoMapper INSTANCE = Mappers.getMapper(AcaoMapper.class);

    AcaoEntity toEntity(AcaoPostDTO acaoDTO);

    AcaoGetDTO toDTO(AcaoEntity acaoEntity);
}
