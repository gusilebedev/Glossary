package org.example.app.service;

import org.example.app.service.dao.Glossary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlossariesRepository extends CrudRepository <Glossary, String> {
}
