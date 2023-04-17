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
import java.util.Map;
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
 * method handler to the Spring framework
 * 
 * @author Connor McRoberts
 */
@RestController
@RequestMapping("products")
public class ProductController {
    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());
    private ProductDAO productDao;
    private UserDAO userDao;


    public ProductController(ProductDAO productDAO, UserDAO userDao) {
        this.productDao = productDAO;
        this.userDao = userDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Product product} for the given id
     * 
     * @param id The id used to locate the {@link Product product}
     * 
     * @return ResponseEntity with {@link Product product} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * @author Connor McRoberts
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        LOG.info("GET /products/" + id);
        try {
            Product product = productDao.getProduct(id);
            if (product != null)
                return new ResponseEntity<Product>(product,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 
    
    /**
     * Responds to the GET request for all {@linkplain Product products}
     * 
     * @return ResponseEntity with array of {@link Product product} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * @author Harbor Wolff hmw2331@rit.edu
     */
    @GetMapping("")
    public ResponseEntity<Product[]> getProducts() {
        LOG.info("GET /products");

        try{
            Product[] productArray = productDao.getProducts();
            return new ResponseEntity<Product[]>(productArray, HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Product products} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Product products}
     * 
     * @return ResponseEntity with array of {@link Product product} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * 
     * @author Cristian Malone
     */
    @GetMapping("/") 
    public ResponseEntity<Product[]> searchProduct(@RequestParam String name) {
        LOG.info("GET /products/?name="+name);
        try {
            Product[] results = productDao.findProducts(name);
            return new ResponseEntity<Product[]>(results,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Product product} with the provided product object
     * 
     * @param product - The {@link Product product} to create
     * 
     * @return ResponseEntity with created {@link Product product} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Product product} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * @author Harbor Wolff hmw2331@rit.edu
     */
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        LOG.info("POST /products " + product);

        // Replace below with your implementation
        try{
            if(productDao.getProduct(product.getId()) == null){
                Product newProduct = productDao.createProduct(product);
                return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Updates the {@linkplain Product product} with the provided {@linkplain Product product} object, if it exists
     * 
     * @param product The {@link Product product} to update
     * 
     * @return ResponseEntity with updated {@link Product product} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * @author Cristian Malone
     */
    @PutMapping("")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        LOG.info("PUT /products " + product);
        try {
            Product updatedProduct = productDao.updateProduct(product);
            if (updatedProduct != null)
                return new ResponseEntity<Product>(updatedProduct,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 
     * @param id
     * @return
     * 
     * @author Connor McRoberts
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        LOG.info("DELETE /products/" + id);

        try{
            if(productDao.deleteProduct(id)){
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for a product's reviews for the given id
     * 
     * @param pid The id used to locate the {@link Product product}
     * 
     * @return ResponseEntity with reviews map and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * @author Cristian Malone
     */
    @GetMapping("/{pid}/reviews")
    public ResponseEntity<Map<String,Integer>> getReviews(@PathVariable int pid) {
        LOG.info("GET /products/" + pid + "/reviews");
        try {
            Product product = productDao.getProduct(pid);
            if (product != null) {
                Map<String,Integer> reviews = productDao.getReviews(product);
                return new ResponseEntity<>(reviews, HttpStatus.OK);
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
     * Creates a product review with the provided review information 
     * 
     * @param uid - The user's id to create the review for
     * @param pid = id for product to be reviewed
     * @param rating - new user rating to be stored
     * 
     * 
     * @return ResponseEntity with new review map and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of NOT_FOUND if the specfied user or product don't exist
     * ResponseEntity with HTTP status of CONFLICT if review already exists<br>
     * ResponseEntity with HTTP status of EXPECTATION_FAILED if rating is not between 0 to 5
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * @author Cristian Malone
     */
    @PostMapping("/{pid}/reviews/")
    public ResponseEntity<Map<String,Integer>> createReview(@RequestParam int uid, @PathVariable int pid, @RequestParam int rating) {
        LOG.info("PUT /products/" + pid + "/reviews/?uid=" + uid + "&rating=" + rating);
        try{
            User user = userDao.findUserByID(uid);
            Product product = productDao.getProduct(pid);
            if(user != null && product != null){
                Map<String,Integer> reviews = product.getReviews();
                if (!reviews.containsKey(user.getUsername())) {
                    if (rating >= 0 && rating <= 5) {
                        Map<String,Integer> newReviews = productDao.createReview(user,product,rating);
                        return new ResponseEntity<>(newReviews, HttpStatus.CREATED);
                    }
                    else {
                        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
                    }
                }
                else {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates a review in the review map with the provided information, if it exists
     * 
     * @param uid - The user's id to edit the review for
     * @param pid = id for product to be reviewed
     * @param rating - new user rating to be stored
     * 
     * @return ResponseEntity with updated review Map and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of EXPECTATION_FAILED if rating is not between 0 to 5
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * @author Cristian Malone
     */
    @PutMapping("/{pid}/reviews/")
    public ResponseEntity<Map<String,Integer>> editReview(@RequestParam int uid, @PathVariable int pid, @RequestParam int rating) {
        LOG.info("PUT /products/" + pid + "/reviews/?uid=" + uid + "&rating=" + rating);
        try {
            User user = userDao.findUserByID(uid);
            Product product = productDao.getProduct(pid);
            if(user != null && product != null){
                Map<String,Integer> reviews = product.getReviews();
                if (reviews.containsKey(user.getUsername())) {
                    if (rating >= 0 && rating <= 5) {
                        Map<String,Integer> newReviews = productDao.editReview(user,product,rating);
                        return new ResponseEntity<>(newReviews, HttpStatus.OK);
                    }
                    else {
                        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
                    }
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a user's review from the review Map
     * 
     * @param uid - The user's id to delete the review for
     * @param pid = id for product to be reviewed
     * 
     * @return ResponseEntity with updated review Map and HTTP status of OK if review deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * @author Cristian Malone
     */
    @DeleteMapping("/{pid}/reviews/")
    public ResponseEntity<Map<String,Integer>> deleteReview(@RequestParam int uid, @PathVariable int pid) {
        LOG.info("DELETE /products/" + pid + "/reviews/?uid=" + uid);

        try{
            User user = userDao.findUserByID(uid);
            Product product = productDao.getProduct(pid);
            if(user != null && product != null){
                Map<String,Integer> reviews = product.getReviews();
                if (reviews.containsKey(user.getUsername())) {
                    Map<String,Integer> newReviews = productDao.deleteReview(user,product);
                    return new ResponseEntity<>(newReviews, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
