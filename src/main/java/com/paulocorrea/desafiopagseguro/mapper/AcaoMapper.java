package com.paulocorrea.desafiopagseguro.mapper;

import com.paulocorrea.desafiopagseguro.dto.AcaoGetDTO;
import com.paulocorrea.desafiopagseguro.dto.AcaoPostDTO;
import com.paulocorrea.desafiopagseguro.entities.AcaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AcaoMapper {

    AcaoMapper INSTANCE = Mappers.getMapper(AcaoMapper.class);

    AcaoEntity toEntity(AcaoPostDTO acaoDTO);

    AcaoGetDTO toDTO(AcaoEntity acaoEntity);

    default List<AcaoGetDTO> toDTOList(List<AcaoEntity> entityList) {
        List<AcaoGetDTO> dtos = new ArrayList<>();
        for (AcaoEntity entity : entityList) {
            dtos.add(toDTO(entity));
        }
        return dtos;
    }
}
