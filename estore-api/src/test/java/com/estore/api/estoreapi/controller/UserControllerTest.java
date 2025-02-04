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
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

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
        User user = new User(0, "username", "pass", false, new int[0],0);
        when(userDAO.findUser(user.getUsername(), user.getPassword())).thenReturn(user);

        ResponseEntity<User> response = userController
        .findUser(user.getUsername(), user.getPassword());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testFindUserNotFound() throws IOException {
        User user = new User(999, "n/a",
        "doesnt matter", false, new int[0],0);

        when(userDAO.findUser(user.getUsername(), user.getPassword()))
        .thenReturn(null);

        ResponseEntity<User> respose = userController
        .findUser(user.getUsername(), user.getPassword());

        assertEquals(HttpStatus.NOT_FOUND, respose.getStatusCode());
    }

    @Test
    public void testFindUserHandledException() throws Exception {
        User user = new User(999, "n/a",
        "doesnt matter", false, new int[0],0);

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
        "doesnt matter", false, new int[0],0);

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
        "doesnt matter", false, new int[0],0);

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
        "doesnt matter", false, new int[0],0);

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
        User user = new User(999, "n/a", "doesn't matter", false, new int[0],0);
        int newInt = 99;

        when(userDAO.findUserByID(user.getId())).thenReturn(user);
        when(userDAO.addProductToCart(newInt, user)).thenReturn(newInt);

        ResponseEntity<Integer> response = userController.addProductToCart(user.getId(), newInt);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newInt, response.getBody());
    }

    @Test
    public void testAddProductToCartUserNotFound() throws IOException{
        int newInt = 99;
        int userId = 999;

        ResponseEntity<Integer> response = userController.addProductToCart(userId, newInt);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddProductToCartHandledException() throws IOException{
        //Setup
        User user = new User(999, "n/a", "doesn't matter", false, new int[0],0);
        int newInt = 99;

        when(userDAO.findUserByID(user.getId())).thenReturn(user);
        doThrow(new IOException()).when(userDAO).addProductToCart(newInt, user);

        ResponseEntity<Integer> response = userController.addProductToCart(user.getId(), newInt);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testRemoveProductFromCart() throws IOException {
        //Setup
        int[]  cart ={1};
        User user = new User(999, "n/a", "doesn't matter", false, cart,0);
        int newInt = 1;

        when(userDAO.findUserByID(user.getId())).thenReturn(user);
        when(userDAO.removeProductFromCart(newInt, user)).thenReturn(newInt);

        ResponseEntity<Integer> response = userController.removeProductFromCart(user.getId(), newInt);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newInt, response.getBody());
    }

    @Test
    public void testRemoveProductFromCartUserNotFound() throws IOException {
        //Setup
        int userId = 999;
        int newInt = 1;

        ResponseEntity<Integer> response = userController.removeProductFromCart(userId, newInt);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRemoveProductFromCartHandledException() throws IOException {
        //Setup
        int[]  cart ={1};
        User user = new User(999, "n/a", "doesn't matter", false, cart,0);
        int newInt = 1;

        when(userDAO.findUserByID(user.getId())).thenReturn(user);
        doThrow(new IOException()).when(userDAO).removeProductFromCart(newInt, user);

        ResponseEntity<Integer> response = userController.removeProductFromCart(user.getId(), newInt);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testShowCart() throws IOException {
        int[] cart = {99};
        User user = new User(999, "n/a", "doesn't matter", false, cart,0);

        when(userDAO.findUserByID(user.getId())).thenReturn(user);
        when(userDAO.showCart(user)).thenReturn(cart);

        ResponseEntity<int[]> response = userController.showCart(user.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testShowCartUserNotFound() throws IOException {
        int userId = 999;

        ResponseEntity<int[]> response = userController.showCart(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test 
    public void testShowCartHandledException() throws IOException{
        int[] cart = {99};
        User user = new User(999, "n/a", "doesn't matter", false, cart,0);

        when(userDAO.findUserByID(user.getId())).thenReturn(user);
        doThrow(new IOException()).when(userDAO).showCart(user);

        ResponseEntity<int[]> response = userController.showCart(user.getId());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }    

    @Test
    public void testCheckout() throws IOException {
        int[] cart = {99,99,99};
        User user = new User(999, "n/a", "doesn't matter", false, cart,0);
        Product product = new Product(99, "n/a", "doesn't matter", 100, 5, null, null);
        int[] emptyCart = {};

        when(userDAO.findUserByID(user.getId())).thenReturn(user);
        when(userDAO.showCart(user)).thenReturn(cart);
        when(productDao.getProduct(product.getId())).thenReturn(product);
        when(userDAO.checkout(user)).thenReturn(emptyCart);

        ResponseEntity<int[]> response = userController.checkout(user.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emptyCart, response.getBody());
    }

    @Test
    public void testCheckoutInvalidProductQuantity() throws IOException {
        int[] cart = {99,99,99};
        User user = new User(999, "n/a", "doesn't matter", false, cart,0);
        Product product = new Product(99, "n/a", "doesn't matter", 100, 0, null, null);

        when(userDAO.findUserByID(user.getId())).thenReturn(user);
        when(userDAO.showCart(user)).thenReturn(cart);
        when(productDao.getProduct(product.getId())).thenReturn(product);

        ResponseEntity<int[]> response = userController.checkout(user.getId());

        assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
    }

    @Test
    public void testCheckoutUserNotFound() throws IOException {
        int userId = 999;

        ResponseEntity<int[]> response = userController.checkout(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCheckoutHandledException() throws IOException {
        int[] cart = {99,99,99};
        User user = new User(999, "n/a", "doesn't matter", false, cart,0);
        Product product = new Product(99, "n/a", "doesn't matter", 100, 5, null, null);

        when(userDAO.findUserByID(user.getId())).thenReturn(user);
        when(userDAO.showCart(user)).thenReturn(cart);
        when(productDao.getProduct(product.getId())).thenReturn(product);
        doThrow(new IOException()).when(userDAO).checkout(user);

        ResponseEntity<int[]> response = userController.checkout(user.getId());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetRewardsPoints() throws IOException {
        int[] cart = {99,98,98};
        User user = new User(999, "n/a", "doesn't matter", false, cart,10);

        when(userDAO.findUserByID(user.getId())).thenReturn(user);
        when(userDAO.getRewardsPoints(user)).thenReturn(10);

        ResponseEntity<Integer> response = userController.getRewardsPoints(user.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10, response.getBody());
    }

    @Test
    public void testGetRewardsPointsUserNotFound() throws IOException {
        int userId = 999;

        ResponseEntity<Integer> response = userController.getRewardsPoints(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetRewardsPointsHandledException() throws IOException {
        int[] cart = {99,98,98};
        User user = new User(999, "n/a", "doesn't matter", false, cart,10);

        when(userDAO.findUserByID(user.getId())).thenReturn(user);
        doThrow(new IOException()).when(userDAO).getRewardsPoints(user);

        ResponseEntity<Integer> response = userController.getRewardsPoints(user.getId());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUseRewardsPoints() throws IOException {
        int[] cart = {99,98,98};
        User user = new User(999, "n/a", "doesn't matter", false, cart,10);
        Product product = new Product(99, "n/a", "doesn't matter", 100, 5, null, null);

        when(userDAO.findUserByID(user.getId())).thenReturn(user);
        when(userDAO.showCart(user)).thenReturn(cart);
        when(productDao.getProduct(product.getId())).thenReturn(product);
        when(userDAO.useRewardsPoints(user,0)).thenReturn(0);

        ResponseEntity<Integer> response = userController.useRewardsPoints(user.getId(), 0);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody());
    }

    @Test
    public void testUseRewardsPointsInvalidProductQuantity() throws IOException {
        int[] cart = {99,98,98};
        User user = new User(999, "n/a", "doesn't matter", false, cart,10);
        Product product = new Product(99, "n/a", "doesn't matter", 100, 0, null, null);

        when(userDAO.findUserByID(user.getId())).thenReturn(user);
        when(userDAO.showCart(user)).thenReturn(cart);
        when(productDao.getProduct(product.getId())).thenReturn(product);

        ResponseEntity<Integer> response = userController.useRewardsPoints(user.getId(), 0);

        assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
    }

    @Test
    public void testNotEnoughRewardsPoints() throws IOException {
        int[] cart = {99,98,98};
        User user = new User(999, "n/a", "doesn't matter", false, cart,9);
        Product product = new Product(99, "n/a", "doesn't matter", 100, 5, null, null);

        when(userDAO.findUserByID(user.getId())).thenReturn(user);
        when(userDAO.showCart(user)).thenReturn(cart);
        when(productDao.getProduct(product.getId())).thenReturn(product);
        when(userDAO.useRewardsPoints(user,0)).thenReturn(9);

        ResponseEntity<Integer> response = userController.useRewardsPoints(user.getId(), 0);

        assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
        assertEquals(null, response.getBody());
        assertEquals(9,user.getRewards());
    }

    @Test
    public void testUseRewardsPointsHandledException() throws IOException {
        int[] cart = {99,98,98};
        User user = new User(999, "n/a", "doesn't matter", false, cart,10);
        Product product = new Product(99, "n/a", "doesn't matter", 100, 5, null, null);

        when(userDAO.findUserByID(user.getId())).thenReturn(user);
        when(userDAO.showCart(user)).thenReturn(cart);
        when(productDao.getProduct(product.getId())).thenReturn(product);
        doThrow(new IOException()).when(userDAO).useRewardsPoints(user,0);

        ResponseEntity<Integer> response = userController.useRewardsPoints(user.getId(), 0);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
