import { Component } from '@angular/core';
import { LoginService } from '../login.service';

@Component({
	selector: 'app-create-acc',
	templateUrl: './create-acc.component.html',
	styleUrls: ['./create-acc.component.css']
})
export class CreateAccComponent {

	constructor(private loginServoce: LoginService) {}

	tempName: String = "";
	tempPass: String = "";
	failCreateAcc: String = "";
	success: boolean = false;

	create_account(): void {
		this.loginServoce.createAccount(this.tempName, this.tempPass)
		.subscribe((data) => {
			if(data == undefined) {
				this.failCreateAcc = "failed to create account, \
				please try again with a different username"
			}
			else {
				this.success = true;
			}
		})
	}
}
