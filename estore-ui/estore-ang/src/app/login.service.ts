import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './user';
import { Observable } from 'rxjs/internal/Observable';


/**
 * Contacts the api for anything 'user' related, 
 * involves login, account creation, grabbing accounts
 * shopping cart.
 * 
 * May be expanded to delete accounts??
 * @author Connor McRoberts
 */
@Injectable({
	providedIn: 'root'
})
export class LoginService {

	// local api url
	private usersUrl = 'http://localhost:8080/users'

	// headers neaded for create account
	headers = new HttpHeaders({'Content-Type':'application/json; charset=utf-8'});
	
  	constructor(private http: HttpClient) { }

	/**
	 * 
	 * @param username The username, taken from 'tempName' in login.component.ts
	 * @param password The password, taken from 'tempPass' in login.component.ts
	 * @returns A observable HttpResponse object, can be typecast into a User 
	 * object 
	 * 
	 * @author Connor McRoberts
	 */
	verifyLogin(username: String, password: String): Observable<User> {

		return this.http.get<User>(this.usersUrl + '/?username=' + username 
		+ '&password=' + password);

	}

	/**
	 * TODO: format the headers to avoid the Http error 415
	 * 
	 * 
	 * @param username The username, taken from 'tempName' in login.component.ts
	 * @param password The password, taken from 'tempPass' in login.component.ts
	 * @returns A observable HttpResponse object, can be typecast into a User 
	 * object  
	 * 
	 * @author Connor McRoberts
	 */
	createAccount(username: String, password: String): Observable<User> {

		return this.http.post<User>(this.usersUrl, '{\"id\": 999, \"username\": ' + 
		username + ', \"password\":' + password + '\"isAdmin\": false}');
	}
  
	//TODO add to cart, remove from cart, show cart
	showCart(): 

}
