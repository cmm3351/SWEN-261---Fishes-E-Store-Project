import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private source : BehaviorSubject<User> = new BehaviorSubject<User>({id:-1,username:"",password:"",isAdmin:false});
  currMessage = this.source.asObservable();

  constructor() { }

  sendUser(user: User) {
    this.source.next(user);
  }
}
