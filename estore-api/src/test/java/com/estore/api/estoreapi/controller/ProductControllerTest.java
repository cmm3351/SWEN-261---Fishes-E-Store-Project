package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.ProductDAO;

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

    /**
     * Before each test, create a new ProductController object and inject
     * a mock Product DAO
     */
    @BeforeEach
    public void setUpProductController() {
        mockProductDAO = mock(ProductDAO.class);
        productController = new ProductController(mockProductDAO);
    }

    @Test
    public void testGetProduct() throws IOException{
        //Setup
        Product product = new Product(0, "Catfish", "Fish that looks like a cat", 99, 1,null);
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
         Product product = new Product(0, "Catfish", "Fish that looks like a cat", 99, 14, null);
        
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
         Product product = new Product(0, "Catfish", "Fish that looks like a cat", 99, 40, null);
 
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
         Product product = new Product(0, "Catfish", "Fish that looks like a cat", 99, 30, null);
         
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
         Product product = new Product(0, "Catfish", "Fish that looks like a cat", 99, 1, null);
         
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
         Product product = new Product(0, "Catfish", "Fish that looks like a cat", 99, 10, null);
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
         products[0] = new Product(0, "Catfish", "Fish that looks like a cat", 99, 20, null);
         products[1] = new Product(100, "Dogfish", "Fish that doesn't look like a dog", 99, 11, null);
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
         products[0] = new Product(0, "Catfish", "Fish that looks like a cat", 99, 30, null);
         products[1] = new Product(100, "Dogfish", "Fish that doesn't look like a dog", 99, 30, null);
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
 }
