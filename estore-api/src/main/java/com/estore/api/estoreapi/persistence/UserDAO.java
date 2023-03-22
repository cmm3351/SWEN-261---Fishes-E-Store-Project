package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;

/**
 * Defines the interface for the User object persistance data.
 * You may notice wthere are functions that are in ProductDAO
 * that are not implemented in this file, this is due to 
 * 
 * @author Connor McRoberts
 */
public interface UserDAO {
    
    /**
     * Retrieves a {@linkplain User user} that matches the same credentials
     * that are passed as parameters.
     * 
     * @param username The username of the {@link User}
     * @param password The password of the {@link User}
     * 
     * @return The {@link User} with matching credentials or nukll
     * if not found.
     * 
     * @throws IOException
     */
    User findUser(String username, String password) throws IOException;

    
    /**
     * Creates a new {@linkplain User user} inside the persistance data
     * 
     * @param user The given {@link User user} object
     * 
     * @return The created {@link User user}
     * 
     * @throws IOException If issue with phyiscal hardware arrives
     */
    User createUser(User user) throws IOException;

    /**
     * Adds an existing store product to the User cart inside the persistance data
     * 
     * @param product the product to add to the User
     * @param user the user of the cart
     * @return the added product
     * @throws IOException If an issue arises
     */
    Product addProductToCart(Product product, User user) throws IOException;

    /**
     * Removes an existing product from the User cart inside the persistance data
     * 
     * @param product the product to be removed
     * @param user the user of the cart
     * @return the removed product
     * @throws IOException if an issue arises
     */
    Product removeProductFromCart(Product product, User user) throws IOException;

    /**
     * Displays the contents of a user's cart
     * 
     * @param user the user of the cart
     * @return an arraylist of integers, corresponding to Product IDs
     * @throws IOException
     */
    int[] showCart(User user) throws IOException;
}
