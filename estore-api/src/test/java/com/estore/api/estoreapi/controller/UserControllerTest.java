package com.estore.api.estoreapi.controller;

import static org.mockito.Mockito.mock;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.persistence.UserDAO;

/**
 * Tests the user controller class
 * based off of ProductControllerTest.java
 * 
 * @author Connor McRoberts
 */
@Tag("Controller-tier")
public class UserControllerTest {
    
    private UserController userController;
    private UserDAO userDAO;

    /**
     * Before each test, create a new ProductController object and inject
     * a mock Product DAO
     */
    @BeforeEach
    public void setUpUserController() {
        userDAO = mock(UserDAO.class);
        userController = new UserController(userDAO);
    }

    @Test
    public void testFindUser() throws IOException {

    }

    @Test
    public void testFindUserNotFound() throws IOException {

    }

    @Test
    public void testFindUserHandledException() throws Exception {

    }

    @Test
    public void testCreateUser() throws IOException {

    }

    @Test
    public void testCreateUserConflict() throws IOException {

    }

    @Test
    public void testCreateUserHandledException() throws IOException {
        
    }

}
