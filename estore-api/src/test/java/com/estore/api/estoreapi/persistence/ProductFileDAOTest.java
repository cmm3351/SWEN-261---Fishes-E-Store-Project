package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Product File DAO class
 * 
 * @author Cristian Malone
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class ProductFileDAOTest {
    ProductFileDAO productFileDAO;
    Product[] testProducts;
    Map<String,Integer> testReviews;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupProductFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testProducts = new Product[3];
        testReviews = new HashMap<String,Integer>();
        testReviews.put("n/a",3);
        testReviews.put("him",4);
        testReviews.put("her",5);
        testProducts[0] = new Product(99,"Red Fish","It's Red",100,1, null,testReviews);
        testProducts[1] = new Product(100,"Blue Fish","It's Blue",100,2, null,null);
        testProducts[2] = new Product(101,"One Fish","There's One",150, 3, null,null);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the product array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Product[].class))
                .thenReturn(testProducts);
        productFileDAO = new ProductFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetProducts() {
        // Invoke
        Product[] products = productFileDAO.getProducts();

        // Analyze
        assertEquals(products.length,testProducts.length);
        for (int i = 0; i < testProducts.length;++i)
            assertEquals(products[i],testProducts[i]);
    }

    @Test
    public void testFindProducts() {
        // Invoke
        Product[] products = productFileDAO.findProducts("lu");

        // Analyze
        assertEquals(products.length,1);
        assertEquals(products[0],testProducts[1]);
    }

    @Test
    public void testGetProduct() {
        // Invoke
        Product product = productFileDAO.getProduct(99);

        // Analzye
        assertEquals(product,testProducts[0]);
    }

    @Test
    public void testDeleteProduct() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> productFileDAO.deleteProduct(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test products array - 1 (because of the delete)
        // Because products attribute of ProductFileDAO is package private
        // we can access it directly
        assertEquals(productFileDAO.products.size(),testProducts.length-1);
    }

    @Test
    public void testCreateProduct() {
        // Setup
        Product product = new Product(102,"Two Fish","There's Two",200, 4, null,null);

        // Invoke
        Product result = assertDoesNotThrow(() -> productFileDAO.createProduct(product),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Product actual = productFileDAO.getProduct(product.getId());
        assertEquals(actual.getId(),product.getId());
        assertEquals(actual.getName(),product.getName());
    }

    @Test
    public void testUpdateProduct() {
        // Setup
        Product product = new Product(99,"Reddish Fish","It's Almost Red",250,3, null,null);

        // Invoke
        Product result = assertDoesNotThrow(() -> productFileDAO.updateProduct(product),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Product actual = productFileDAO.getProduct(product.getId());
        assertEquals(actual,product);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Product[].class));

        Product product = new Product(102,"Two Fish","There's Two",200,3, null,null);

        assertThrows(IOException.class,
                        () -> productFileDAO.createProduct(product),
                        "IOException not thrown");
    }

    @Test
    public void testGetProductNotFound() {
        // Invoke
        Product product = productFileDAO.getProduct(98);

        // Analyze
        assertEquals(product,null);
    }

    @Test
    public void testDeleteProductNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> productFileDAO.deleteProduct(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(productFileDAO.products.size(),testProducts.length);
    }

    @Test
    public void testUpdateProductNotFound() {
        // Setup
        Product product = new Product(98,"No Fish","...",500,3, null,null);

        // Invoke
        Product result = assertDoesNotThrow(() -> productFileDAO.updateProduct(product),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the ProductFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Product[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new ProductFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }

    @Test
    public void testGetProductReviews() {
        // Invoke
        Product product = productFileDAO.getProduct(99);
        Map<String,Integer> reviews = assertDoesNotThrow(() -> productFileDAO.getReviews(product),
                                                            "Unexpected exception thrown");

        // Analyze
        assertEquals(reviews.keySet().size(),testReviews.keySet().size());
        for (Map.Entry<String,Integer> entry : testReviews.entrySet()) {
            assertEquals(entry.getValue(),reviews.get(entry.getKey()));
        }

    }

    @Test
    public void testCreateReview() {
        // Setup
        Product product = new Product(99,"Reddish Fish","It's Almost Red",250,3, null,testReviews);
        User user = new User(999,"test","n/a",false,null, 0);

        // Invoke
        Map<String,Integer> result = assertDoesNotThrow(() -> productFileDAO.createReview(user,product,1),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Map<String,Integer> actual = assertDoesNotThrow(() -> productFileDAO.getReviews(product), 
                                                    "Unexpected exception thrown");
        assertEquals(1,actual.get("test"));
    }

    @Test
    public void testEditReview() {
        // Setup
        Product product = new Product(99,"Reddish Fish","It's Almost Red",250,3, null,testReviews);
        User user = new User(999,"him","n/a",false,null, 0);

        // Invoke
        Map<String,Integer> result = assertDoesNotThrow(() -> productFileDAO.editReview(user,product,5),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Map<String,Integer> actual = assertDoesNotThrow(() -> productFileDAO.getReviews(product), 
                                                    "Unexpected exception thrown");
        assertEquals(5,actual.get("him"));
    }

    @Test
    public void testDeleteReview() {
        // Setup
        Map<String,Integer> reviews = new HashMap<String,Integer>();
        reviews.put("him",4);
        reviews.put("her",5);
        Product product = new Product(99,"Reddish Fish","It's Almost Red",250,3, null,testReviews);
        User user = new User(999,"n/a","n/a",false,null, 0);

        // Invoke
        Map<String,Integer> result = assertDoesNotThrow(() -> productFileDAO.deleteReview(user,product),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        assertEquals(reviews, result);
        Map<String,Integer> actual = assertDoesNotThrow(() -> productFileDAO.getReviews(product), 
                                                    "Unexpected exception thrown");
        assertEquals(reviews.size(),actual.size());
    }
}