package com.abnamro.assessment.api.person;

import com.abnamro.assessment.exception.PersonException;
import com.abnamro.assessment.model.Person;
import com.abnamro.assessment.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {
    private static final Logger logger = LogManager.getLogger("PersonController");
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService){
        this.personService = personService;
    }

    /**
     * <p>Get Filtered Persons</p>>
     */
    @GetMapping
    public ResponseEntity<List<Person>> findAll() {
        return ResponseEntity.ok(personService.listFilteredPersons());
    }

    /**
     * <p>Create a new Person</p>
     */
    @PostMapping
    public ResponseEntity create(@RequestBody Person person) {
        Person p = null;
        try {
            p = personService.createPerson(person);
        } catch (PersonException e) {
            logger.error("Cannot create a person", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity(p, HttpStatus.CREATED);
    }
}