package com.paulocorrea.desafiopagseguro.repository;

import com.paulocorrea.desafiopagseguro.entities.AcaoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AcaoMongoRepository extends MongoRepository<AcaoEntity, String> {

    Optional<AcaoEntity> findByCodigo(String codigo);

    Optional<AcaoEntity> findByNome(String nome);
}
