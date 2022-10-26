package com.finist.personwebapp.controller;

import com.finist.personwebapp.model.Person;
import com.finist.personwebapp.model.PersonResponse;
import com.finist.personwebapp.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class PersonController {

    private final PersonRepository persones;

    public PersonController(PersonRepository persones) {
        this.persones = persones;
    }

    @GetMapping("/persons")
    public ResponseEntity<List<PersonResponse>> getAllPersons(){
        List<Person> personList = persones.findAll();
        List<PersonResponse> personResponses = new ArrayList<>();
        for (Person p: personList) {
            personResponses.add(new PersonResponse(p));
        }
        return new ResponseEntity<>(personResponses, HttpStatus.OK);
    }

}
