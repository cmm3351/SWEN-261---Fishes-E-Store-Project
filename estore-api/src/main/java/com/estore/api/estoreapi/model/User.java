package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode; // possiblbly for shopping cart

public class User {
    private static final Logger Log = Logger.getLogger(User.class.getName());

    // private field memebers of the User class
    @JsonProperty("id") private int id;
    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;
    @JsonProperty("isAdmin") private boolean isAdmin;
    // @JsonProperty("cart") private JsonNode cart;

    //TODO shopping cart implementation

    /**
     * Create a user for the estore with the given parameters.
     * 
     * Notice the lack of setters, the accounts should not be able
     * to change their password until further implementation.
     * 
     * @param id The id of the user
     * @param name The username of the user
     * @param password The password of the user (64-bit encryption)
     * @author Connor McRoberts cjm6653@rit.edu
     */
    public User(@JsonProperty("id") int id, @JsonProperty("username") String username,
    @JsonProperty("password") String password, @JsonProperty("isAdmin") boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    /**
     * Retrieves the id of the user
     * @return The id of the user
     */
    public int getId() {return id;}

    /**
     * Retrieve the username of the user
     * @return The username of the user
     */
    public String getUsername() {return username;}

    /**
     * Retrieve the password of the user
     * @return The password of the user
     */
    public String getPassword() {return password;}

    public boolean getisAdmin() {return isAdmin;}

    //TODO getters and setters for shopping cart

}
