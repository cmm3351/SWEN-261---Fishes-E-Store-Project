package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;

/**
 * Handles the API requests for the Product resource
 * 
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework.
 * 
 * @authors Connor McRoberts cjm6653@rit.edu, Harbor Wolff hmw2331@rit.edu
 */
@RestController
@RequestMapping("users")
public class UserController {
    private static final Logger LOG = Logger.getLogger(
        UserController.class.getName());
    private UserDAO userDao;


    public UserController(UserDAO userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/")
    public ResponseEntity<User> findUser(@RequestParam String username,
    @RequestParam String password) {
        LOG.info("GET /users/?username="+ username + "&password=" + password);
        try {
            User user = userDao.findUser(username, password);
            if(user != null) {
                return new ResponseEntity<User>(user, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain User user} with the provided hero object
     * 
     * @param user - The {@link User user} to create
     * 
     * @return ResponseEntity with created {@link User user} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link User user} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * @author Connor McRoberts cjm6653@rit.edu
     */
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        LOG.info("POST /users " + user);

        try {
            if(userDao.findUser(user.getUsername(), user.getPassword()) == null) {
                User newUser = userDao.createUser(user);
                return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
            } 
            else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //TODO possibly deleteUser
    //TODO shopping cart api calls
    @PostMapping("/cart/")
    public ResponseEntity<Product> addProductToCart(@RequestBody Product product) {
        LOG.info("POST /cart/ " + product);

        try{
            //TODO once UserDao cart stuff is created
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch(IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


}
