package com.estore.api.estoreapi.persistence;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Logger;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.User;

/**
 * Implements the functionality for JSON file-based persistance for Users
 * 
 * {@literal @Componenet} Spring annotation instatiates a single instance 
 * of this class and injects the instance into other classes as needed
 * 
 * @author Connor McRoberts cjm6653@rit.edu
 */
@Component
public class UserFileDAO implements UserDAO {
    private static final Logger LOG = Logger.getLogger(UserFileDAO.class.getName());
    Map<Integer, User> users; // refer to ProductFileDAO for documentation on fields
    
    private ObjectMapper objectMapper;

    private static int nextId;

    private String filename;

    /**
     * Creates a User File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public UserFileDAO(@Value("${user.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the products from the file
    }

    /**
     * Generates the next id for a new {@linkplain User user}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * {@inheritDoc}
     * @author Connor McRoberts cjm6653@rit.edu
     */
    public User findUser(String username, String password) throws IOException {

        for(User user : users.values()) {
            if(user.getUsername().equals(username) &&
            user.getPassword().equals(password)) {
                    return user;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * @author Connor McRoberts cjm6653@rit.edu
     */
    public User findUserByID(int id) throws IOException {

        for(User user : users.values()) {
            if(user.getId() == id){
                    return user;
            }
        }
        return null;
    }

    
    /**
     * {@inheritDoc}
     * @author Connor McRoberts cjm6653@rit.edu
     */
    public User createUser(User user) throws IOException {
        User newUser = new User(nextId(), user.getUsername(), user.getPassword(), user.getisAdmin(), user.showCart());

        users.put(newUser.getId(), newUser);
        save();
        return newUser;
    }

    /**
     *{@inheritDoc}
     * @author Harbor Wolff hmw2331@rit.edu
     */
    public int addProductToCart(int id, User user) throws IOException{
        User updateUser = new User(user.getId(), user.getUsername(), user.getPassword(), user.getisAdmin(), user.showCart());
        updateUser.addProductToCart(id);
        
        users.put(updateUser.getId(), updateUser);
        save();
        return id;
    }   

    /**
     *{@inheritDoc}
     *@author Harbor Wolff hmw2331@rit.edu
     * @throws IOException
     */
    public int removeProductFromCart(int id, User user) throws IOException{
        User updateUser = new User(user.getId(), user.getUsername(), user.getPassword(), user.getisAdmin(), user.showCart());
        updateUser.removeProductFromCart(id);

        users.put(updateUser.getId(), updateUser);
        save();
        return id;
    }

    /**
     * {@inheritDoc}}
     * @author Harbor Wolff hmw2331@rit.edu
     */
    public int[] showCart(User user){
        return user.showCart();
    }

    /**
     * Saves the {@linkplain User users} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link User user} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        User[] userArray;

        userArray = new User[users.keySet().size()];
        int i = 0;
        for(User user : users.values()) {
            userArray[i] = user;
            i++;
        }

        objectMapper.writeValue(new File(filename), userArray);
        return true;
    }

    /**
     * Loads {@linkplain Users users} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        users = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of products
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        User[] userArray = objectMapper.readValue(new File(filename),
        User[].class);

        for(User user : userArray) {
            users.put(user.getId(), user);
            if(user.getId() > nextId) {
                nextId = user.getId();
            }
        }
        ++nextId;
        return true;
    }


}
