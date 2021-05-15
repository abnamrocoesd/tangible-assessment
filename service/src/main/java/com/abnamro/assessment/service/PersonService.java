package com.abnamro.assessment.service;

import java.util.Collections;
import java.util.List;

import com.abnamro.assessment.dao.PersonRepository;
import com.abnamro.assessment.exception.PersonException;
import com.abnamro.assessment.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PersonService
 */
@Service
public class PersonService {

    private static final Logger logger = LogManager.getLogger("PersonService");
    private final PersonRepository personRepository;
    private final List<Integer> bannedYears;

    public PersonService(final PersonRepository personRepository) throws Exception {
        this.personRepository = personRepository;
        bannedYears = retrieveBannedYears();
    }


    public Person createPerson(Person p) throws PersonException{
        if(p.getBirthDate() == null){
            throw new PersonException("BirthDay must NOT be empty");
        }
        if(p.getName() == null || p.getName().length() < 2){
            throw new PersonException("Name requires at least two characters");
        }
        List<Person> allPersons = (List<Person>)personRepository.findAll();
        boolean exist = allPersons.stream().anyMatch(onePerson ->(onePerson.getName().equalsIgnoreCase(p.getName())));
        if(exist){
            throw new PersonException(String.format("A person with the name [%s] already exists", p.getName()));
        }
        return personRepository.save(p);
    }

    public List<Person> listFilteredPersons() {
        List<Person> allPersons = (List<Person>)personRepository.findAll();

        List<Person> filtered = allPersons.stream().filter(
                aPerson -> bannedYears.stream().allMatch(year ->
                        aPerson.getBirthDate().getYear() != year
                )
        ).collect(Collectors.toList());

        return filtered;
    }

    /**
     * Get banned years
     * // TODO: it is better to move it to dedicated class Utils
     * 
     */
    private List<Integer> retrieveBannedYears() throws Exception {
        List<Integer> years = null;
        try {
            File file = ResourceUtils.getFile("classpath:banned-years");
            InputStream inputStream = new FileInputStream(file);
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            String by = new String(bdata, StandardCharsets.UTF_8);
            String lines[] = by.split("\\r?\\n");
            years = Arrays.stream(lines).map(Integer::parseInt).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Can not create PersonService", e);
            throw e;
        }
        return years;
    }
   
}