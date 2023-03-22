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
    @JsonProperty("cart") private int[] cart;

    /**
     * Create a user for the estore with the given parameters.
     * 
     * Notice the lack of setters, the accounts should not be able
     * to change their password until further implementation.
     * 
     * @param id The id of the user
     * @param name The username of the user
     * @param password The password of the user (64-bit encryption)
     * @authors Connor McRoberts cjm6653@rit.edu, Harbor Wolff hmw2331@rit.edu
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
    
    /**
     *Adds a product's id to the cart
     *@param id: the id of the product to be added
     *@pre: the product id exists in the store
     */
    public void addProductToCart(int id){
        int[] newArr = new int[this.cart.length + 1];
        for(int i = 0; i < this.cart.length; i++){
            newArr[i] = this.cart[i];
        }
        newArr[this.cart.length] = id;
        this.cart = newArr;
    };

    /**
     *Removes a single instance (the first occurence) product's id from the cart
     *@param id: the id of the product to be removed
     *@pre: the product id exists in the cart
     */
    public void removeProductFromCart(int id){
        int[] newArr = new int[this.cart.length - 1];
        for(int i = 0, j = 0; i < this.cart.length; i++){
            if(this.cart[i] != id){
                newArr[j++] = this.cart[i];
            }
        }
        this.cart = newArr;
    };

    /**
     *Displays the entire cart
     *@return: the cart as an ArrayList of integers representing product ids
     */
    public int[] showCart(){return this.cart;};
}
