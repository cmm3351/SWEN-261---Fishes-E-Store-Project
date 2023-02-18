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
import com.estore.api.estoreapi.model.Product;

/**
 * Handles the API requests for the Product resource
 * 
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Connor McRoberts
 */
public class ProductController {
    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());
    private ProductDAO productDao;


    public ProductController(ProductDAO productDAO) {
        this.productDao = productDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain Hero hero} for the given id
     * 
     * @param id The id used to locate the {@link Hero hero}
     * 
     * @return ResponseEntity with {@link Hero hero} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getHero(@PathVariable int id) {
        LOG.info("GET /heroes/" + id);
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

    @GetMapping("/") 
    public ResponseEntity<Product[]> searchProduct(@RequestParam String name) {

    }

    /**
     * Creates a {@linkplain Product product} with the provided hero object
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
        LOG.info("POST /heroes " + product);

        // Replace below with your implementation
        try{
            Product newProduct = productDao.createProduct(product);
            return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {

    }

    


}
