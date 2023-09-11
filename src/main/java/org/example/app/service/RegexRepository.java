package org.example.app.service;

import org.example.app.service.dao.RegexEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegexRepository extends CrudRepository<RegexEntity, Integer> {
}
