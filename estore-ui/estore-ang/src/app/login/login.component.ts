import { Component } from '@angular/core';

import { LoginService } from '../login.service';
import { User } from '../user';
import { DataService } from '../data.service';


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
				private dataService: DataService) {}

	CurrentUser?: User;
	isLoggedin: boolean = false;
	tempName: String = "";
	tempPass: String = "";
	currUser: User = {id:-1,username:"",password:"",isAdmin:false};

	/**
	 * A login function, attached to the 'button container' div
	 * in login.component.html, will activate when the user clicks the 
	 * button.
	 */
	login(): void {
		
		this.loginService.verifyLogin(this.tempName, this.tempPass)
			.subscribe((data: User) => {this.CurrentUser = data});

		//TODO find conditional to stop this from firing 
		if(this.CurrentUser == undefined) {
			this.loginService.createAccount(this.tempName, this.tempPass)
				.subscribe((data) => {this.CurrentUser = data})
		}
		this.isLoggedin = true;
		if (this.CurrentUser != undefined) {
			this.dataService.currMessage.subscribe(currUser => this.currUser = currUser);
			this.dataService.sendUser(this.CurrentUser);
		}
	}

}
