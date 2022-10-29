package com.finist.personwebapp.repository;

import com.finist.personwebapp.model.Person;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Transactional
    @Cacheable("person")
    List<Person> findAll() throws DataAccessException;
}
