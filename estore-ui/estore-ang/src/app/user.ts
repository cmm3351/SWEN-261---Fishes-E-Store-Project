/**
 * The interface for the User class in typescript
 * contains an id, username, password and
 * isAdmin fields for basic functionality
 * 
 * May be expanded to hold a shopppingCart object,
 * otherwise holds limited functionality.
 * 
 * @author Connor McRoberts
 */
export interface User {
    id: number;
    username: String;
    password: String;
    isAdmin: boolean;

}