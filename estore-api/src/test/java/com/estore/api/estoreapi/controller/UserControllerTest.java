package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.ProductDAO;
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
    private ProductDAO productDao;

    /**
     * Before each test, create a new ProductController object and inject
     * a mock Product DAO
     */
    @BeforeEach
    public void setUpUserController() {
        userDAO = mock(UserDAO.class);
        productDao = mock(ProductDAO.class);
        userController = new UserController(userDAO,productDao);
    }

    @Test
    public void testFindUser() throws IOException {
        // setup
        User user = new User(0, "username", "pass", false, new int[0]);
        when(userDAO.findUser(user.getUsername(), user.getPassword())).thenReturn(user);

        ResponseEntity<User> response = userController
        .findUser(user.getUsername(), user.getPassword());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testFindUserNotFound() throws IOException {
        User user = new User(999, "n/a",
        "doesnt matter", false, new int[0]);

        when(userDAO.findUser(user.getUsername(), user.getPassword()))
        .thenReturn(null);

        ResponseEntity<User> respose = userController
        .findUser(user.getUsername(), user.getPassword());

        assertEquals(HttpStatus.NOT_FOUND, respose.getStatusCode());
    }

    @Test
    public void testFindUserHandledException() throws Exception {
        User user = new User(999, "n/a",
        "doesnt matter", false, new int[0]);

        doThrow(new IOException()).when(userDAO)
        .findUser(user.getUsername(), user.getPassword());

        ResponseEntity<User> response = userController
        .findUser(user.getUsername(), user.getPassword());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testCreateUser() throws IOException {
        // setup
        User user = new User(999, "n/a",
        "doesnt matter", false, new int[0]);

        when(userDAO.createUser(user)).thenReturn(user);

        // invoke
        ResponseEntity<User> response = userController
        .createUser(user);

        // analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testCreateUserConflict() throws IOException {
        // setup
        User user = new User(999, "n/a",
        "doesnt matter", false, new int[0]);

        // causes the user to 'already exist'
        when(userDAO.findUser(user.getUsername(), user.getPassword()))
        .thenReturn(user);

        // invoke
        ResponseEntity<User> response = userController
        .createUser(user);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateUserHandledException() throws IOException {
        // setup
        User user = new User(999, "n/a",
        "doesnt matter", false, new int[0]);

        doThrow(new IOException()).when(userDAO).createUser(user);

        // invoke
        ResponseEntity<User> response = userController
        .createUser(user);

        // analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testAddProductToCart() throws IOException{
        //Setup
        User user = new User(999, "n/a", "doesn't matter", false, new int[0]);
        int newInt = 99;
        
        when(userDAO.addProductToCart(newInt, user)).thenReturn(newInt);

        ResponseEntity<Integer> response = userController.addProductToCart(user.getId(), newInt);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newInt, response.getBody());
    }

    @Test
    public void testRemoveProductFromCart() throws IOException {
        //Setup
        int[]  cart ={1};
        User user = new User(999, "n/a", "doesn't matter", false, cart);
        int newInt = 1;

        when(userDAO.removeProductFromCart(newInt, user)).thenReturn(newInt);

        ResponseEntity<Integer> response = userController.removeProductFromCart(user.getId(), newInt);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newInt, response.getBody());
    }

    @Test
    public void testShowCart() throws IOException {
        int[] cart = {99};
        User user = new User(999, "n/a", "doesn't matter", false, cart);

        when(userDAO.findUserByID(user.getId())).thenReturn(user);
        when(userDAO.showCart(user)).thenReturn(cart);

        ResponseEntity<int[]> response = userController.showCart(user.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testCheckout() throws IOException {
        int[] cart = {99,99,99};
        User user = new User(999, "n/a", "doesn't matter", false, cart);
        Product product = new Product(99, "n/a", "doesn't matter", 100, 5);
        int[] emptyCart = null;

        when(userDAO.findUserByID(user.getId())).thenReturn(user);
        when(userDAO.showCart(user)).thenReturn(cart);
        when(productDao.getProduct(product.getId())).thenReturn(product);

        ResponseEntity<int[]> response = userController.checkout(user.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emptyCart, response.getBody());
        //assertEquals(2, productDao.getProduct(99).getQuantity());
    }
}
