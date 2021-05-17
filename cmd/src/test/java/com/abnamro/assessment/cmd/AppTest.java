package com.abnamro.assessment.cmd;

import com.abnamro.assessment.cmd.utils.Utils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void createJsonBody() {
        String json = Utils.createJsonBody("Anna", "2003-03-21");
        assertEquals("{\"name\": \"Anna\",\"birthDate\": \"2003-03-21\"}", json);
    }

    // TODO: For testing GetPerson and AddPerson it makes sense to create functional/integration tests
    // Anothet option is to mock api-service and create Unit-tests for both GetPerson and AddPerson 
}
