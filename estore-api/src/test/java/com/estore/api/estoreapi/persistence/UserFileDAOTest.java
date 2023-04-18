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
        users[0] = new User(0, "harbor", "password", false, new int[0],10);
        users[1] = new User(1, "pokemon", "1234", false, new int[0],0);
        users[2] = new User(2,"admin","password", true, new int[0],0);

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
    public void testGetUserInvalidUsername() {
        // Invoke
        User user = userFileDAO.findUser("elmo", "1234");

        // Analzye
        assertNull(user);
    }

    @Test
    public void testGetUserInvalidPassword() {
        // Invoke
        User user = userFileDAO.findUser("harbor", "1234");

        // Analzye
        assertNull(user);
    }

    @Test
    public void testGetUserInvalidId() {
        // Invoke
        User user = assertDoesNotThrow(() -> userFileDAO.findUserByID(4),
                                       "Unexpected exception thrown");

        // Analzye
        assertNull(user);
    }

    @Test
    public void testCreateUser() {
        // Setup
        User user = new User(3, "connor", "geo", false, new int[0],0);

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
    public void testAddProductToCart() throws IOException{
        int[] cart = {};
        User user = new User(3, "connor", "geo", false, cart,0);

        userFileDAO.addProductToCart(1, user);

        assertEquals(user.showCart(), userFileDAO.showCart(user));
    }

    @Test
    public void testRemoveProductFromCart() throws IOException{
        int[] cart = {1};
        User user = new User(3, "connor", "geo", false, cart,0);

        userFileDAO.removeProductFromCart(1, user);

        assertEquals(user.showCart(), userFileDAO.showCart(user));
    }

    @Test
    public void testShowCart() throws IOException{
        int[] cart = {1};
        User user = new User(3, "connor", "geo", false, cart,0);

        assertEquals(cart, userFileDAO.showCart(user));
    }

    @Test
    public void testCheckout() {
        int[] cart = {1,1,1};
        User user = new User(3, "connor", "geo", false, cart,0);

        int[] result = assertDoesNotThrow(() -> userFileDAO.checkout(user),
                                                "Unexpected exception thrown");

        assertEquals(0,result.length);
        assertEquals(3,user.getRewards());
    }

    @Test
    public void testGetRewardsPoints() {
        // Invoke
        User user = assertDoesNotThrow(() -> userFileDAO.findUserByID(0),
                                     "Unexpected exception thrown");
        int rewards = assertDoesNotThrow(() -> userFileDAO.getRewardsPoints(user),
                                               "Unexpected exception thrown");

        // Analyze
        assertEquals(10,rewards);

    }

    @Test
    public void testUseRewardsPoints() {
        int[] cart = {1,2,2};
        User user = new User(3, "connor", "geo", false, cart,10);

        int rewards = assertDoesNotThrow(() -> userFileDAO.useRewardsPoints(user,0),
                                               "Unexpected exception thrown");

        assertEquals(0,rewards);
        assertEquals(0,user.getRewards());

        int[] newCart = assertDoesNotThrow(() -> userFileDAO.showCart(user), 
                                                 "Unexpected exception thrown");

        assertEquals(2,newCart.length);
        for (int i = 0; i < 2; i++) {
            assertEquals(2,newCart[i]);
        }
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
