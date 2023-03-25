import { Component, OnInit, OnChanges, Input, SimpleChanges, ChangeDetectionStrategy } from '@angular/core';

import { User } from '../user';
import { Product } from '../product';
import { LoginService } from '../login.service';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit{
  @Input() currUser? : User; 
  cart: Product[] = [];

  constructor(private loginService: LoginService){}

  ngOnInit(): void {
    
  }
}