package com.estore.api.estoreapi.persistence;

import java.io.IOException;
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

}
