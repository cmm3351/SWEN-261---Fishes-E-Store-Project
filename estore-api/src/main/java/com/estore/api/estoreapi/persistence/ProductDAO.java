/***
 * Defines the interface for the Product Object persistence
 *  Code is derived from heroes-api/.../HeroDAO.java
 * 
 * @author Harbor Wolff hmw2331@rit.edu
 * @author SWEN faculty
 */
package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import java.util.Map;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;

public interface ProductDAO {
    /**
     * Retrieves all {@linkplain Product products}
     * 
     * @return returns an array of {@link Product Product} objects, can be empty
     * 
     * @throws IOException if an issue with storage arises
     */
    Product[] getProducts() throws IOException;

    /**
     * Finds all {@linkplain Product products} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Product products} whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product[] findProducts(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Product product} with the given id
     * 
     * @param id The id of the {@link Product product} to get
     * 
     * @return a {@link Product product} object with the matching id
     * 
     * null if no {@link Product product} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product getProduct(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Product product}
     * 
     * @param product {@linkplain Product product} object to be created and saved
     * 
     * The id of the product object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Product product} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product createProduct(Product product) throws IOException;

    /**
     * Updates and saves a {@linkplain Product product}
     * 
     * @param {@link Product product} object to be updated and saved
     * 
     * @return updated {@link Product product} if successful, null if
     * {@link Product product} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product updateProduct(Product product) throws IOException;

    /**
     * Deletes a {@linkplain Product product} with the given id
     * 
     * @param id The id of the {@link Product product}
     * 
     * @return true if the {@link Product product} was deleted
     * 
     * false if product with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteProduct(int id) throws IOException;

    /**
     * Retrieves the user reviews for a certain product
     * 
     * @param product Product for which reviews will be retrieved
     * @return Map representing user reviews with corresponsing usernames
     * @throws IOException
     */
    Map<String,Integer> getReviews(Product product) throws IOException;

    /**
     * Creates a new review for a product if the user hasn't made one already
     * 
     * @param user User creating the review
     * @param product Product for which the review is created
     * @param rating intger value of rating out of 5
     * @return Map representing user reviews with corresponsing usernames
     * @throws IOException
     */
    Map<String,Integer> createReview(User user, Product product, int rating) throws IOException;

    /**
     * Edits a review for a product if user has made one already
     * 
     * @param user User editing the review
     * @param product Product for which the review is edited
     * @param rating intger value of rating out of 5
     * @return Map representing user reviews with corresponsing usernames
     * @throws IOException
     */
    Map<String,Integer> editReview(User user, Product product, int rating) throws IOException;

    /**
     * Deletes a review for a product if the user has made one already
     * 
     * @param user User deleting the review
     * @param product Product for which the review is deleted
     * @return Map representing user reviews with corresponsing usernames
     * @throws IOException
     */
    Map<String,Integer> deleteReview(User user, Product product) throws IOException;
}
