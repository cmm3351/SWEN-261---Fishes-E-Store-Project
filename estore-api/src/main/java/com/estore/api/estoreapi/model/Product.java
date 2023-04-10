package com.estore.api.estoreapi.model;

import java.util.*;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    // private field members for the Product class
    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("info") private String info;
    @JsonProperty("price") private int price;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("imgSource") private String imgSource;
    @JsonProperty("reviews") private Map<String,Integer> reviews;

    /**
     * Create a product for the estore with given parameters.
     * @param id The id of the product
     * @param name The name of the product (fish)
     * @param info Extra info abot product (habitat, size, lifespan, etc.)
     * @param price Price of the product (fish)
     * @param quantity If the fish is in stock or not
     * @param imgSource String representing link to product's image
     * @param reviews Map representing reviews for a product
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Product(@JsonProperty("id") int id, @JsonProperty("name") String name,
    @JsonProperty("info") String info, @JsonProperty("price") int price,
    @JsonProperty("quantity") int quantity, @JsonProperty("imgSource") String imgSource,
    @JsonProperty("reviews") Map<String,Integer> reviews ){
        this.id = id;
        this.name = name;
        this.info = info;
        this.price = price;
        this.quantity = quantity;
        this.imgSource = imgSource;
        this.reviews = reviews;
    }

    /**
     * Retreives the id of the Product object
     * @return The id of the Product 
     */
    public int getId() {return id;}

    /**
     * Retreives the name of the Product object
     * @return The id name the Product 
     */
    public String getName() {return name;}

    /**
     * Retreives the info of the Product object
     * @return The info of the Product 
     */
    public String getInfo() {return info;}

    /**
     * Retreives the price of the Product object
     * @return The price of the Product 
     */
    public int getPrice() {return price;}

    /**
     * Retreives the quantity of the Product object
     * @return The quantity of the Product 
     */
    public int getQuantity() {return quantity;}

    /**
     * Sets the name of the product
     * @param name The new name of the product
     */
    public void setName(String name) {this.name = name;}

    /**
     * Sets the Price of the product
     * @param price The new price of the product
     */
    public void setPrice(int price) {this.price = price;}

    /**
     * Sets the new info of the product
     * @param info The new info of the product
     */
    public void setInfo(String info) {this.info = info;}

    /**
     * Sets the quantity of the product
     * @param quantity The new quantity of the product
     */
    public void setQuantity(int quantity) {this.quantity = quantity;}

    /**
     * Sets the imgSource of the product
     * @param imgSource The new source of the image
     */
    public void setImgSource(String imgSource) {this.imgSource = imgSource;}

    /**
     * Gets the source of the product's image
     */
    public String getImgSource(){return this.imgSource;}

    /**
     * Gets the reviews for a product
     */
    public Map<String,Integer> getReviews(){
        return this.reviews;
    }

    /**
     * Modifies the reviews for a product
     * @param newReviews New Map to represent reviews
     */
    public void setReviews(Map<String,Integer> newReviews){
        this.reviews = newReviews;
    }
}
