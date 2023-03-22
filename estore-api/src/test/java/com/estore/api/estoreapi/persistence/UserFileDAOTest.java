package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test file for the UserFileDAO class 
 * 
 * @author Connor McRoberts
 */
@Tag("Persistance-tier")
public class UserFileDAOTest {
    
    UserFileDAO userFileDAO;
    User[] users;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupProductFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        users = new User[3];
        users[0] = new User(0, "harbor", "password", false, new int[0]);
        users[1] = new User(1, "pokemon", "1234", false, new int[0]);
        users[2] = new User(2,"admin","password", true, new int[0]);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the product array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),User[].class))
                .thenReturn(users);
        userFileDAO = new UserFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetUser() {
        // Invoke
        User user = userFileDAO.findUser("harbor", "password");

        // Analzye
        assertEquals(user, users[0]);
    }
    
    @Test
    public void testGetUserNull() {
        // Invoke
        User user = userFileDAO.findUser("elmo", "1234");

        // Analzye
        assertNull(user);
    }

    @Test
    public void testCreateUser() {
        // Setup
        User user = new User(3, "connor", "geo", false);

        // Invoke
        User result = assertDoesNotThrow(() -> 
        userFileDAO.createUser(user), "Unexpected error occured");

        // Analyze
        assertNotNull(result);
        User actual = userFileDAO.findUser(result.getUsername(), result.getPassword());
        assertEquals(actual.getUsername(), user.getUsername());
        assertEquals(actual.getPassword(), user.getPassword());
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the ProductFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),User[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new UserFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
