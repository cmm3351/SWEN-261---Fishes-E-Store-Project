import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { User } from './user';
import { Observable } from 'rxjs/internal/Observable';
import { BehaviorSubject, catchError, of } from 'rxjs';
import { Product } from './product';


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

	user?: User 

	// headers neaded for create account
	headers = new HttpHeaders({'Content-Type':'application/json; charset=utf-8'});

	httpOptions = {
		headers: new HttpHeaders({ 'Content-Type': 'application/json' })
	  };

	isInCart: Map<number,number> = new Map<number,number>();

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

		 this.http.get<User>(this.usersUrl + '/?username=' + username 
		+ '&password=' + password).subscribe((data) => this.user = data)


		return this.http.get<User>(this.usersUrl + '/?username=' + username 
		+ '&password=' + password).pipe(
			catchError(this.handleError<User>('verifyLogin', undefined))
		);

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

		let data = {
			id: 999,
			username: username,
			password: password,
			isAdmin: false
		}

		return this.http.post<User>(this.usersUrl, data, { headers: this.headers});
	}

	/**
 	* Handle Http operation that failed.
 	* Let the app continue.
 	*
 	* @param operation - name of the operation that failed
 	* @param result - optional value to return as the observable result
 	*/
	private handleError<T>(operation = 'operation', result?: T) {
		return (error: any): Observable<T> => {
  
		  // TODO: send the error to remote logging infrastructure
		  console.error(error); // log to console instead
	  
		  // Let the app keep running by returning an empty result.
		  return of(result as T);
		};
  	}
  
	//TODO add to cart, remove from cart, show cart
	getCart(user: User){
		return this.http.get<number[]>(this.usersUrl + '/cart/?uid=' + user.id);
	}

	deleteFromCart(user: User, product: Product){
		let url = this.usersUrl + '/cart/?uid=' + user.id + '&pid=' + product.id;
		return this.http.delete<any>(url, this.httpOptions)
	}

	checkout(user: User) {
		let url = this.usersUrl + '/cart/checkout/?uid=' + user.id;
		return this.http.put<any>(url,this.httpOptions);
	}

	/** Returns Map detailing current contents of the user's cart */
	getIsInCart() : Map<number,number> {
		return this.isInCart;
	  }
	
	/** Modifies contents of Map to correctly represent contents of the cart */
	setIsInCart(id: number, value: number) : void {
		this.isInCart.set(id,value);
	}

	getUser() {
		return this.user;
	}
}
