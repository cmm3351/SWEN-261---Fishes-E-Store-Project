import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { catchError } from 'rxjs';

import { LoginService } from '../login.service';
import { User } from '../user';

/**
 * Login component for the web-application. Contains
 * functionality to log users in, create new accounts,
 * etc.
 * 
 * @author Connor McRoberts
 */
@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css']
})
export class LoginComponent {


	constructor(private loginService: LoginService,
				private router: Router) {}

	CurrentUser?: User;
	tempName: String = "";
	tempPass: String = "";
	failLogin: String = "";

	/**
	 * A login function, attached to the 'button container' div
	 * in login.component.html, will activate when the user clicks the 
	 * button.
	 */
	login(): void {
		
		this.loginService.verifyLogin(this.tempName, this.tempPass)
			.subscribe((data) => {
				this.CurrentUser = data;

				if(this.CurrentUser == undefined ) {
					this.failLogin = 
					"invalid password or username"
				}
				else {
					this.router.navigateByUrl('',{state : {currUser: this.CurrentUser}});
				}
			})
	}
}
 