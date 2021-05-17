package com.abnamro.assessment.service;

import com.abnamro.assessment.SpringBootH2Application;
import com.abnamro.assessment.exception.PersonException;
import com.abnamro.assessment.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * PersonServiceTest
 */
@ActiveProfiles("service")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootH2Application.class)
public class PersonServiceTest {

    @Autowired
    private PersonService personService;
    

    /**
     * 1. Test a wrong name
     * @throws Exception
     */
    public void createPersonWithWrongName() throws Exception {
        Person person = new Person();
        person.setName("P");
        person.setBirthDate(LocalDate.parse("2014-02-14"));
        personService.createPerson(person);
    }

    /**
     * 2. Test an empty Birthday
     * @throws Exception
     */
    @Test(expected = PersonException.class)
    public void createPersonWithEmptyDB() throws Exception {
        Person person = new Person();
        person.setName("Peter");
        personService.createPerson(person);
    }

    /**
     * 3. Test a person with existing name
     * @throws Exception
    */
    @Test(expected = PersonException.class)
    public void createPersonWithExistingName() throws Exception {
        Person person = new Person();
        person.setName("Peter");
        person.setBirthDate(LocalDate.parse("2014-02-14"));
        personService.createPerson(person);
    }


    /**
     * 4. Test creating a new Person
     * @throws Exception
    */
    @Test
    public void createPerson() throws Exception {
        Person person = new Person();
        person.setName("Denny");
        person.setBirthDate(LocalDate.parse("2014-02-14"));
        Person p = personService.createPerson(person);
        assertNotNull("Created Person must not be NULL", p);

        List<Person> persons = personService.listFilteredPersons();
        boolean created = persons.stream().anyMatch(aPerson -> aPerson.getName().equals(person.getName()));
        assertTrue("A new Person with the name Denny is expected in DB",created);
    }

    /**
     * 5. Get filtered persons
     * @throws Exception
     */
    @Test
    public void listFilteredPersons() throws Exception {
        List<Person> lst = personService.listFilteredPersons();
        assertEquals(6, lst.size());
        //TODO: may also test that banned-years are not in the result lst
    } 
}