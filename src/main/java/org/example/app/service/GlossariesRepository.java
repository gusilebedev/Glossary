package org.example.app.service;

import org.example.app.service.dao.GlossaryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlossariesRepository extends CrudRepository <GlossaryEntity, String> {
}
