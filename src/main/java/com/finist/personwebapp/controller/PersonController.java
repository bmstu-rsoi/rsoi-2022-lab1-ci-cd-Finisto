package com.finist.personwebapp.controller;

import com.finist.personwebapp.model.*;
import com.finist.personwebapp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

//    @Autowired
//    private RestTemplate restTemplate;
    @Autowired
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

    @PostMapping("/persons")
    public ResponseEntity<?> createNewPerson(@RequestBody PersonRequest requestBody){
        try {
            Person savedPerson = this.persones.save(new Person(requestBody));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/api/v1/persons/" + savedPerson.getId());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
        catch (Exception ex){
            return new ResponseEntity<ValidationErrorResponse>((new ValidationErrorResponse("Invalid data").addError(
                    ex.getClass().getCanonicalName(),
                    ex.getMessage())), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/persons/{personId}")
    public ResponseEntity<?> getPersonById(@PathVariable int personId){
        Optional<Person> optionalPerson = this.persones.findById(personId);
        if(optionalPerson.isPresent()){
            Person person = optionalPerson.get();
            return new ResponseEntity<>(new PersonResponse(person), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ErrorResponse("Not found Person for ID"), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/persons/{personId}")
    public ResponseEntity<?> deletePersonById(@PathVariable int personId){
        Optional<Person> optionalPerson = this.persones.findById(personId);
        if(optionalPerson.isPresent()){
            Person person = optionalPerson.get();
            this.persones.delete(person);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 code
        }
        else{
            return new ResponseEntity<>(new ErrorResponse("Not found Person for ID"), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/persons/{personId}")
    public ResponseEntity<?> updatePersonById(@PathVariable int personId, @RequestBody PersonRequest requestBody){
        Optional<Person> optionalPerson = this.persones.findById(personId);
        if(optionalPerson.isPresent()){
            Person person = optionalPerson.get();

            if(requestBody.age != null){
                person.setAge(requestBody.age);
            }
            if(requestBody.name != null){
                person.setName(requestBody.name);
            }
            if(requestBody.address != null){
                person.setAddress(requestBody.address);
            }
            if(requestBody.work != null){
                person.setWork(requestBody.work);
            }
            Person savedPerson = this.persones.save(person);

            return new ResponseEntity<>(new PersonResponse(savedPerson), HttpStatus.OK);

        }
        else{
            return new ResponseEntity<>(new ErrorResponse("Not found Person for ID"), HttpStatus.NOT_FOUND);
        }
    }

}
