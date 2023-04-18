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
    private ProductDAO productDao;

    public UserController(UserDAO userDao, ProductDAO productDao) {
        this.userDao = userDao;
        this.productDao = productDao;
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
                return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
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

    /**
     * Adds a product to a user's cart
     * 
     * @param product the product to add
     * @param user the user of the cart
     * @return the id of the product that was added
     */
    @PutMapping("/cart/")
    public ResponseEntity<Integer> addProductToCart(@RequestParam int uid, @RequestParam int pid) {
        LOG.info("PUT /cart/?uid="+uid+"&pid="+pid);

        try{
            User user = userDao.findUserByID(uid);
            userDao.addProductToCart(pid, user);
            return new ResponseEntity<Integer>(pid, HttpStatus.OK);
        }catch(IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Removes a product from the user's cart
     * 
     * @param product the product to remove
     * @param user the user of the cart
     * @return the id of the product that was removed
     */
    @DeleteMapping("/cart/")
    public ResponseEntity<Integer> removeProductFromCart(@RequestParam int uid, @RequestParam int pid){
        LOG.info("DELETE /cart/?uid="+uid+"&pid="+pid);

        try{
            User user = userDao.findUserByID(uid);
            userDao.removeProductFromCart(pid, user);
            return new ResponseEntity<Integer>(pid, HttpStatus.OK);
        }catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Displays a user's cart
     * 
     * @param user the user of the cart
     * @return the contents of the user's cart, as an int[] of product ids
     */
    @GetMapping("/cart/")
    public ResponseEntity<int[]> showCart(@RequestParam int uid){
        LOG.info("GET /cart/?uid=" + uid);

        try{
            User user = userDao.findUserByID(uid);
            int[] array = userDao.showCart(user);
            return new ResponseEntity<int[]>(array, HttpStatus.OK);
        }catch(IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * When the customer checks out from their shopping cart,
     * this function removes all items from their cart and
     * updates each product's quantity accordingly.
     * 
     * @param uid integer id of the current user
     * @return Empty integer representing an empty cart after checkout
     */
    @PutMapping("/cart/checkout") 
    public ResponseEntity<int[]> checkout(@RequestParam int uid){
        LOG.info("PUT /cart/checkout/?uid=" + uid);
        try {
            User user = userDao.findUserByID(uid);
            int[] cart = userDao.showCart(user);
            for (int i = 0; i < cart.length; i++) {
                Product currProduct = productDao.getProduct(cart[i]);
                if (currProduct.getQuantity() != 0) {
                    Product updatedProduct = new Product(currProduct.getId(), currProduct.getName(), 
                                                         currProduct.getInfo(), currProduct.getPrice(), 
                                                         currProduct.getQuantity() - 1, currProduct.getImgSource(),
                                                         currProduct.getReviews());
                    productDao.updateProduct(updatedProduct);
                }
            }
            int[] emptyCart = userDao.checkout(user);
            return new ResponseEntity<>(emptyCart, HttpStatus.OK);
        }catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Returns the current rewards points of a user
     * 
     * @param uid integer id of the user
     * @return integer representing current rewars points of a user
     */
    @GetMapping("/rewards/")
    public ResponseEntity<Integer> getRewardsPoints(@RequestParam int uid) {
        LOG.info("GET /rewards/?uid=" + uid);
        try {
            User user = userDao.findUserByID(uid);
            if (user != null) {
                int points = userDao.getRewardsPoints(user);
                return new ResponseEntity<>(points, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * When the customer has 10 or more rewards points, 
     * they can purchase an item for free in exchange
     * for 10 rewards points
     * 
     * @param uid integer id of the current user
     * @param cid integer index of the cart item to be purchased
     * @return modified integer representing the user's rewards
     */
    @PutMapping("/cart/rewards") 
    public ResponseEntity<Integer> useRewardsPoints(@RequestParam int uid, @RequestParam int cid){
        LOG.info("PUT /cart/rewards/?uid=" + uid + "&cid=" + cid);
        try {
            User user = userDao.findUserByID(uid);
            int[] cart = userDao.showCart(user);
            if (user.getRewards() >= 10) {
                Product currProduct = productDao.getProduct(cart[cid]);
                if (currProduct.getQuantity() != 0) {
                    Product updatedProduct = new Product(currProduct.getId(), currProduct.getName(), 
                                                         currProduct.getInfo(), currProduct.getPrice(), 
                                                         currProduct.getQuantity() - 1, currProduct.getImgSource(),
                                                         currProduct.getReviews());
                    productDao.updateProduct(updatedProduct);
                }
            int points = userDao.useRewardsPoints(user,cid);
            return new ResponseEntity<>(points, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }
        }catch (IOException e){
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
