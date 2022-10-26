package com.finist.personwebapp.repository;

import com.finist.personwebapp.model.Person;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Transactional
    @Cacheable("person")
    List<Person> findAll() throws DataAccessException;
}
