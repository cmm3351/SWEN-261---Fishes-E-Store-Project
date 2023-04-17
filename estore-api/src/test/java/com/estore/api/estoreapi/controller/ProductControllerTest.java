package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.*;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.persistence.UserDAO;

import org.apache.tomcat.jni.Proc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Product Controller class
 *  Derived from heroes-api
 * 
 * @author Harbor Wolff hmw2331@rit.edu
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class ProductControllerTest {
    private ProductController productController;
    private ProductDAO mockProductDAO;
    private UserDAO mockUserDAO;

     /**
      * Before each test, create a new ProductController object and inject
      * a mock Product DAO
      */
     @BeforeEach
     public void setUpProductController() {
        mockProductDAO = mock(ProductDAO.class);
        mockUserDAO = mock(UserDAO.class);
        productController = new ProductController(mockProductDAO,mockUserDAO);
     }

     @Test
     public void testGetProduct() throws IOException{
        //Setup
        Product product = new Product(0, "Catfish", "Fish that looks like a cat", 99, 1,null, null);
        when(mockProductDAO.getProduct(product.getId())).thenReturn(product);

        ResponseEntity<Product> response = productController.getProduct(product.getId());

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product,response.getBody());
     }

     @Test
     public void testGetProductNotFound() throws IOException{
        int productId = 99;

        when(mockProductDAO.getProduct(productId)).thenReturn(null);

        ResponseEntity<Product> response = productController.getProduct(productId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
     }

     @Test
     public void testGetProductHandledException() throws Exception{
        int productId = 99;

        doThrow(new IOException()).when(mockProductDAO).getProduct(productId);

        ResponseEntity<Product> response = productController.getProduct(productId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
     }

     @Test
     public void testCreateProduct() throws IOException {
         // Setup
         Product product = new Product(0, "Catfish", "Fish that looks like a cat", 99, 14, null,null);
        
         when(mockProductDAO.createProduct(product)).thenReturn(product);
 
         // Invoke
         ResponseEntity<Product> response = productController.createProduct(product);
 
         // Analyze
         assertEquals(HttpStatus.CREATED,response.getStatusCode());
         assertEquals(product,response.getBody());
     }
 
     @Test
     public void testCreateProductHandleException() throws IOException { 
         // Setup
         Product product = new Product(0, "Catfish", "Fish that looks like a cat", 99, 40, null,null);
 
         // When createProduct is called on the Mock Product DAO, throw an IOException
         doThrow(new IOException()).when(mockProductDAO).createProduct(product);
 
         // Invoke
         ResponseEntity<Product> response = productController.createProduct(product);
 
         // Analyze
         assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
     }
 
     @Test
     public void testUpdateProduct() throws IOException {
         // Setup
         Product product = new Product(0, "Catfish", "Fish that looks like a cat", 99, 30, null, null);
         
         // update and save
         when(mockProductDAO.updateProduct(product)).thenReturn(product);
         ResponseEntity<Product> response = productController.updateProduct(product);
         product.setName("FatCish");
 
         // Invoke
         response = productController.updateProduct(product);
 
         // Analyze
         assertEquals(HttpStatus.OK,response.getStatusCode());
         assertEquals(product,response.getBody());
     }
 
     @Test
     public void testUpdateProductFailed() throws IOException {
         // Setup
         Product product = new Product(0, "Catfish", "Fish that looks like a cat", 99, 1, null, null);
         
         // update and save
         when(mockProductDAO.updateProduct(product)).thenReturn(null);
 
         // Invoke
         ResponseEntity<Product> response = productController.updateProduct(product);
 
         // Analyze
         assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
     }
 
     @Test
     public void testUpdateProductHandleException() throws IOException { 
         // Setup
         Product product = new Product(0, "Catfish", "Fish that looks like a cat", 99, 10, null, null);
         // When updateProduct is called on the Mock Product DAO, throw an IOException
         doThrow(new IOException()).when(mockProductDAO).updateProduct(product);
 
         // Invoke
         ResponseEntity<Product> response = productController.updateProduct(product);
 
         // Analyze
         assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
     }
 
     @Test
     public void testGetProducts() throws IOException {
         // Setup
         Product[] products = new Product[2];
         products[0] = new Product(0, "Catfish", "Fish that looks like a cat", 99, 20, null, null);
         products[1] = new Product(100, "Dogfish", "Fish that doesn't look like a dog", 99, 11, null, null);
         // When getProducts is called return the products created above
         when(mockProductDAO.getProducts()).thenReturn(products);
 
         // Invoke
         ResponseEntity<Product[]> response = productController.getProducts();
 
         // Analyze
         assertEquals(HttpStatus.OK,response.getStatusCode());
         assertEquals(products,response.getBody());
     }
 
     @Test
     public void testGetProductsHandleException() throws IOException {
         // Setup
         // When getProducts is called on the Mock Product DAO, throw an IOException
         doThrow(new IOException()).when(mockProductDAO).getProducts();
 
         // Invoke
         ResponseEntity<Product[]> response = productController.getProducts();
 
         // Analyze
         assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
     }
 
     @Test
     public void testSearchProducts() throws IOException {
         // Setup
         String searchString = "la";
         Product[] products = new Product[2];
         products[0] = new Product(0, "Catfish", "Fish that looks like a cat", 99, 30, null, null);
         products[1] = new Product(100, "Dogfish", "Fish that doesn't look like a dog", 99, 30, null, null);
         // When findProducts is called with the search string, return the two
         /// products above
         when(mockProductDAO.findProducts(searchString)).thenReturn(products);
 
         // Invoke
         ResponseEntity<Product[]> response = productController.searchProduct(searchString);
 
         // Analyze
         assertEquals(HttpStatus.OK,response.getStatusCode());
         assertEquals(products,response.getBody());
     }
 
     @Test
     public void testSearchProductsHandleException() throws IOException {
         // Setup
         String searchString = "an";
         // When createProducts is called on the Mock Product DAO, throw an IOException
         doThrow(new IOException()).when(mockProductDAO).findProducts(searchString);
 
         // Invoke
         ResponseEntity<Product[]> response = productController.searchProduct(searchString);
 
         // Analyze
         assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
     }
 
     @Test
     public void testDeleteProduct() throws IOException { 
         // Setup
         int productId = 99;
         // when deleteProduct is called return true, simulating successful deletion
         when(mockProductDAO.deleteProduct(productId)).thenReturn(true);
 
         // Invoke
         ResponseEntity<Product> response = productController.deleteProduct(productId);
 
         // Analyze
         assertEquals(HttpStatus.OK,response.getStatusCode());
     }
 
     @Test
     public void testDeleteProductNotFound() throws IOException { 
         // Setup
         int productId = 99;
         // when deleteProduct is called return false, simulating failed deletion
         when(mockProductDAO.deleteProduct(productId)).thenReturn(false);
 
         // Invoke
         ResponseEntity<Product> response = productController.deleteProduct(productId);
 
         // Analyze
         assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
     }
 
     @Test
     public void testDeleteProductHandleException() throws IOException { 
         // Setup
         int productId = 99;
         // When deleteProduct is called on the Mock Product DAO, throw an IOException
         doThrow(new IOException()).when(mockProductDAO).deleteProduct(productId);
 
         // Invoke
         ResponseEntity<Product> response = productController.deleteProduct(productId);
 
         // Analyze
         assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
     }

     @Test
     public void testGetReviews() throws IOException {
        Map<String,Integer> reviews = new HashMap<String,Integer>();
        reviews.put("n/a",3);
        reviews.put("him",4);
        reviews.put("her",5);

        User user = new User(999, "n/a", "doesn't matter", false, null,0);
        Product product = new Product(99, "n/a", "doesn't matter", 100, 5, null, reviews);

        when(mockUserDAO.findUserByID(user.getId())).thenReturn(user);
        when(mockProductDAO.getProduct(product.getId())).thenReturn(product);
        when(mockProductDAO.getReviews(product)).thenReturn(reviews);

        ResponseEntity<Map<String,Integer>> response = productController.getReviews(product.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviews, response.getBody());
        assertEquals(3, response.getBody().get("n/a"));
     }

     @Test
     public void testGetReviewsProductNotFound() throws IOException {
        int productId = 99;
         
        ResponseEntity<Map<String,Integer>> response = productController.getReviews(productId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
     }

     @Test
     public void testGetReviewsHandledException() throws IOException {
        Map<String,Integer> reviews = new HashMap<String,Integer>();

        User user = new User(999, "n/a", "doesn't matter", false, null,0);
        Product product = new Product(99, "n/a", "doesn't matter", 100, 5, null, reviews);
         
        when(mockUserDAO.findUserByID(user.getId())).thenReturn(user);
        when(mockProductDAO.getProduct(product.getId())).thenReturn(product);
        doThrow(new IOException()).when(mockProductDAO).getReviews(product);

        ResponseEntity<Map<String,Integer>> response = productController.getReviews(product.getId());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
     }

     @Test
     public void testCreateReview() throws IOException {
        Map<String,Integer> reviews = new HashMap<String,Integer>();
        reviews.put("n/a",3);
        reviews.put("him",4);
        reviews.put("her",5);

        Map<String,Integer> newReviews = new HashMap<String,Integer>();
        newReviews.put("n/a",3);
        newReviews.put("him",4);
        newReviews.put("her",5);
        newReviews.put("test", 1);

        User user = new User(998, "test", "doesn't matter", false, null,0);
        Product product = new Product(99, "n/a", "doesn't matter", 100, 5, null, reviews);

        when(mockUserDAO.findUserByID(user.getId())).thenReturn(user);
        when(mockProductDAO.getProduct(product.getId())).thenReturn(product);
        when(mockProductDAO.getReviews(product)).thenReturn(reviews);
        when(mockProductDAO.createReview(user,product,1)).thenReturn(newReviews);
        

        ResponseEntity<Map<String,Integer>> response = productController.createReview(user.getId(),product.getId(),1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newReviews, response.getBody());
        assertEquals(1, response.getBody().get("test"));
     }

     @Test
     public void testCreateReviewRatingInvalid() throws IOException {
        Map<String,Integer> reviews = new HashMap<String,Integer>();
        reviews.put("n/a",3);
        reviews.put("him",4);
        reviews.put("her",5);


        User user = new User(998, "test", "doesn't matter", false, null,0);
        Product product = new Product(99, "n/a", "doesn't matter", 100, 5, null, reviews);

        when(mockUserDAO.findUserByID(user.getId())).thenReturn(user);
        when(mockProductDAO.getProduct(product.getId())).thenReturn(product);
        when(mockProductDAO.getReviews(product)).thenReturn(reviews);
        
        ResponseEntity<Map<String,Integer>> response = productController.createReview(user.getId(),product.getId(),6);

        assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
     }

     @Test
     public void testCreateReviewUserHasExistingReview() throws IOException {
        Map<String,Integer> reviews = new HashMap<String,Integer>();
        reviews.put("n/a",3);
        reviews.put("him",4);
        reviews.put("her",5);

        User user = new User(998, "him", "doesn't matter", false, null,0);
        Product product = new Product(99, "n/a", "doesn't matter", 100, 5, null, reviews);

        when(mockUserDAO.findUserByID(user.getId())).thenReturn(user);
        when(mockProductDAO.getProduct(product.getId())).thenReturn(product);
        when(mockProductDAO.getReviews(product)).thenReturn(reviews);        

        ResponseEntity<Map<String,Integer>> response = productController.createReview(user.getId(),product.getId(),1);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
     }

     @Test
     public void testCreateReviewProductNotFound() throws IOException {
        Map<String,Integer> reviews = new HashMap<String,Integer>();
        reviews.put("n/a",3);
        reviews.put("him",4);
        reviews.put("her",5);


        User user = new User(998, "test", "doesn't matter", false, null,0);
        int productId = 99;

        when(mockUserDAO.findUserByID(user.getId())).thenReturn(user);

        ResponseEntity<Map<String,Integer>> response = productController.createReview(user.getId(),productId,1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
     }

     @Test
     public void testCreateReviewUserNotFound() throws IOException {
        Map<String,Integer> reviews = new HashMap<String,Integer>();
        reviews.put("n/a",3);
        reviews.put("him",4);
        reviews.put("her",5);

        Product product = new Product(99, "n/a", "doesn't matter", 100, 5, null, reviews);
        int userId = 99;

        when(mockProductDAO.getProduct(product.getId())).thenReturn(product);

        ResponseEntity<Map<String,Integer>> response = productController.createReview(userId,product.getId(),1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
     }

     @Test
     public void testCreateReviewHandledException() throws IOException {
        Map<String,Integer> reviews = new HashMap<String,Integer>();
        reviews.put("n/a",3);
        reviews.put("him",4);
        reviews.put("her",5);

        User user = new User(998, "test", "doesn't matter", false, null,0);
        Product product = new Product(99, "n/a", "doesn't matter", 100, 5, null, reviews);

        when(mockUserDAO.findUserByID(user.getId())).thenReturn(user);
        when(mockProductDAO.getProduct(product.getId())).thenReturn(product);
        when(mockProductDAO.getReviews(product)).thenReturn(reviews);
        doThrow(new IOException()).when(mockProductDAO).createReview(user,product,1);
        

        ResponseEntity<Map<String,Integer>> response = productController.createReview(user.getId(),product.getId(),1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
     }

     @Test
     public void testEditReview() throws IOException {
        Map<String,Integer> reviews = new HashMap<String,Integer>();
        reviews.put("n/a",3);
        reviews.put("him",4);
        reviews.put("her",5);

        Map<String,Integer> newReviews = new HashMap<String,Integer>();
        newReviews.put("n/a",2);
        newReviews.put("him",4);
        newReviews.put("her",5);

        User user = new User(999, "n/a", "doesn't matter", false, null,0);
        Product product = new Product(99, "n/a", "doesn't matter", 100, 5, null, reviews);

        when(mockUserDAO.findUserByID(user.getId())).thenReturn(user);
        when(mockProductDAO.getProduct(product.getId())).thenReturn(product);
        when(mockProductDAO.getReviews(product)).thenReturn(reviews);
        when(mockProductDAO.editReview(user,product,2)).thenReturn(newReviews);
        

        ResponseEntity<Map<String,Integer>> response = productController.editReview(user.getId(),product.getId(),2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newReviews, response.getBody());
        assertEquals(2, response.getBody().get("n/a"));
     }

     @Test
     public void testEditReviewRatingInvalid() throws IOException {
        Map<String,Integer> reviews = new HashMap<String,Integer>();
        reviews.put("n/a",3);
        reviews.put("him",4);
        reviews.put("her",5);


        User user = new User(998, "him", "doesn't matter", false, null,0);
        Product product = new Product(99, "n/a", "doesn't matter", 100, 5, null, reviews);

        when(mockUserDAO.findUserByID(user.getId())).thenReturn(user);
        when(mockProductDAO.getProduct(product.getId())).thenReturn(product);
        when(mockProductDAO.getReviews(product)).thenReturn(reviews);
        
        ResponseEntity<Map<String,Integer>> response = productController.editReview(user.getId(),product.getId(),6);

        assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
     }

     @Test
     public void testEditReviewProductNotFound() throws IOException {
        Map<String,Integer> reviews = new HashMap<String,Integer>();
        reviews.put("n/a",3);
        reviews.put("him",4);
        reviews.put("her",5);


        User user = new User(998, "test", "doesn't matter", false, null,0);
        int productId = 99;

        when(mockUserDAO.findUserByID(user.getId())).thenReturn(user);

        ResponseEntity<Map<String,Integer>> response = productController.editReview(user.getId(),productId,1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
     }

     @Test
     public void testEditReviewUserNotFound() throws IOException {
        Map<String,Integer> reviews = new HashMap<String,Integer>();
        reviews.put("n/a",3);
        reviews.put("him",4);
        reviews.put("her",5);

        Product product = new Product(99, "n/a", "doesn't matter", 100, 5, null, reviews);
        int userId = 99;

        when(mockProductDAO.getProduct(product.getId())).thenReturn(product);

        ResponseEntity<Map<String,Integer>> response = productController.editReview(userId,product.getId(),1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
     }

     @Test
     public void testEditReviewHandledException() throws IOException {
        Map<String,Integer> reviews = new HashMap<String,Integer>();
        reviews.put("n/a",3);
        reviews.put("him",4);
        reviews.put("her",5);

        User user = new User(998, "him", "doesn't matter", false, null,0);
        Product product = new Product(99, "n/a", "doesn't matter", 100, 5, null, reviews);

        when(mockUserDAO.findUserByID(user.getId())).thenReturn(user);
        when(mockProductDAO.getProduct(product.getId())).thenReturn(product);
        when(mockProductDAO.getReviews(product)).thenReturn(reviews);
        doThrow(new IOException()).when(mockProductDAO).editReview(user,product,1);
        

        ResponseEntity<Map<String,Integer>> response = productController.editReview(user.getId(),product.getId(),1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
     }

     @Test
     public void testDeleteReview() throws IOException {
        Map<String,Integer> reviews = new HashMap<String,Integer>();
        reviews.put("n/a",3);
        reviews.put("him",4);
        reviews.put("her",5);

        Map<String,Integer> newReviews = new HashMap<String,Integer>();
        newReviews.put("him",4);
        newReviews.put("her",5);

        User user = new User(999, "n/a", "doesn't matter", false, null,0);
        Product product = new Product(99, "n/a", "doesn't matter", 100, 5, null, reviews);

        when(mockUserDAO.findUserByID(user.getId())).thenReturn(user);
        when(mockProductDAO.getProduct(product.getId())).thenReturn(product);
        when(mockProductDAO.getReviews(product)).thenReturn(reviews);
        when(mockProductDAO.deleteReview(user,product)).thenReturn(newReviews);
        

        ResponseEntity<Map<String,Integer>> response = productController.deleteReview(user.getId(),product.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newReviews, response.getBody());
        assertEquals(null, response.getBody().get("n/a"));
     }

     @Test
     public void testDeleteReviewProductNotFound() throws IOException {
        Map<String,Integer> reviews = new HashMap<String,Integer>();
        reviews.put("n/a",3);
        reviews.put("him",4);
        reviews.put("her",5);


        User user = new User(998, "test", "doesn't matter", false, null,0);
        int productId = 99;

        when(mockUserDAO.findUserByID(user.getId())).thenReturn(user);

        ResponseEntity<Map<String,Integer>> response = productController.deleteReview(user.getId(),productId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
     }

     @Test
     public void testDeleteReviewUserNotFound() throws IOException {
        Map<String,Integer> reviews = new HashMap<String,Integer>();
        reviews.put("n/a",3);
        reviews.put("him",4);
        reviews.put("her",5);

        Product product = new Product(99, "n/a", "doesn't matter", 100, 5, null, reviews);
        int userId = 99;

        when(mockProductDAO.getProduct(product.getId())).thenReturn(product);

        ResponseEntity<Map<String,Integer>> response = productController.deleteReview(userId,product.getId());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
     }

     @Test
     public void testDeleteReviewHandledException() throws IOException {
        Map<String,Integer> reviews = new HashMap<String,Integer>();
        reviews.put("n/a",3);
        reviews.put("him",4);
        reviews.put("her",5);

        User user = new User(998, "him", "doesn't matter", false, null,0);
        Product product = new Product(99, "n/a", "doesn't matter", 100, 5, null, reviews);

        when(mockUserDAO.findUserByID(user.getId())).thenReturn(user);
        when(mockProductDAO.getProduct(product.getId())).thenReturn(product);
        when(mockProductDAO.getReviews(product)).thenReturn(reviews);
        doThrow(new IOException()).when(mockProductDAO).deleteReview(user,product);
        

        ResponseEntity<Map<String,Integer>> response = productController.deleteReview(user.getId(),product.getId());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
     }
 }
