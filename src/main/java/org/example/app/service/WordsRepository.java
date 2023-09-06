package org.example.app.service;

import org.example.app.service.dao.Word;
import org.example.app.service.dao.WordId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordsRepository extends CrudRepository<Word, WordId> {
}
