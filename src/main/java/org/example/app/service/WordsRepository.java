package org.example.app.service;

import org.example.app.service.dao.WordEntity;
import org.example.app.service.dao.WordId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordsRepository extends CrudRepository<WordEntity, WordId> {
}
