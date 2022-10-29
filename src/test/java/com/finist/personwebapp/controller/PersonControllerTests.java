package com.finist.personwebapp.controller;


import com.finist.personwebapp.model.Person;
import com.finist.personwebapp.model.PersonResponse;
import com.finist.personwebapp.repository.PersonRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonControllerTests {


    //    @InjectMocks
//    private PersonController personController;
//    PersonRepository personRepository;
//    @Autowired
//    TestRestTemplate testRestTemplate;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonRepository personRepository;

    private final Person smith = new Person(1, "Smith", 40, "NYC", "Agent");
    private final Person johns = new Person(2, "Johns", 35, "NYC", "Agent");
    private final Person neo = new Person(3, "Neo", 30, "Matrix", "Chosen");
    private List<Person> allPersons;
    private Map<Integer, Person> allPersonsMap;

    @BeforeEach
    public void initEach() {
//        Person smith = new Person(1, "Smith", 40, "NYC", "Agent");
//        Person johns = new Person(2, "Johns", 35, "NYC", "Agent");
//        Person neo = new Person(3, "Neo", 30, "Matrix", "Chosen");

        this.allPersons = List.of(smith, johns, neo);
        this.allPersonsMap = new HashMap<>(Map.of(smith.getId(), smith, johns.getId(), johns, neo.getId(), neo));

        when(personRepository.findAll()).thenReturn(allPersons);
        when(personRepository.findById(3)).thenReturn(Optional.of(allPersonsMap.get(3)));
        when(personRepository.findById(1)).thenReturn(Optional.of(allPersonsMap.get(1)));


    }

    @Test
    public void testGetAll() throws Exception {
        this.mockMvc.perform(get("/api/v1/persons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpectAll(jsonPath("$[2].name", is(neo.getName())),
                        jsonPath("$[2].age", is(neo.getAge())),
                        jsonPath("$[2].work", is(neo.getWork())),
                        jsonPath("$[2].address", is(neo.getAddress())));

    }

    @Test
    public void testGetPersonByIdAndFound() throws Exception {

        this.mockMvc.perform(get("/api/v1/persons/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(3)))
                .andExpectAll(jsonPath("$.name", is(neo.getName())),
                        jsonPath("$.age", is(neo.getAge())),
                        jsonPath("$.work", is(neo.getWork())),
                        jsonPath("$.address", is(neo.getAddress())));

    }

    @Test
    public void testGetPersonByIdAndNotFound() throws Exception {
        this.mockMvc.perform(get("/api/v1/persons/3333"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Not found Person for ID")));
    }

    @Test
    public void testDeletePersonByIdAndDeleted() throws Exception {
        doAnswer(invocationOnMock -> {
            Object arg = invocationOnMock.getArgument(0);

            assertEquals(allPersonsMap.get(2), arg);
            allPersonsMap.remove(2);
            return null;
        }).when(personRepository).delete(any(Person.class));
//        when(personRepository.findById(2)).thenReturn(Optional.of(allPersonsMap.get(2)));
        doAnswer(invocationOnMock -> {
            Object arg = invocationOnMock.getArgument(0);
            assertEquals(2, arg);
            return Optional.ofNullable(allPersonsMap.get(2));
        }).when(personRepository).findById(any(Integer.class));

        this.mockMvc.perform(delete("/api/v1/persons/2"))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(delete("/api/v1/persons/2"))
                .andExpect(status().isNotFound());

    }

}
