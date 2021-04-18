package com.paulocorrea.desafiopagseguro.repository;

import com.paulocorrea.desafiopagseguro.entities.AcaoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AcaoMongoRepository extends MongoRepository<AcaoEntity, String> {
}
