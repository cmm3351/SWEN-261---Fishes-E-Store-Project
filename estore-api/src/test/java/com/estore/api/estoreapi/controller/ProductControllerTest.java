package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.ProductDAO;

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
        Product product = new Product(0, "Catfish", "Fish that looks like a cat", 99, true);
        when(mockProductDAO.getProduct(product.getId())).thenReturn(product);

        ResponseEntity<Product> response = productController.getProduct(product.getId());

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    @Test
    public void testGetProductNotFound(){
        int productId = 99;

        when(mockProductDAO.getProduct(productId)).thenReturn(null);

        ResponseEntity<Product> 

    }
}
