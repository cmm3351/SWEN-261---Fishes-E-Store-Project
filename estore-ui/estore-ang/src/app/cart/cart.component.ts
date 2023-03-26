import { Component, OnInit, OnChanges, Input, SimpleChanges, ChangeDetectionStrategy } from '@angular/core';

import { User } from '../user';
import { Product } from '../product';
import { LoginService } from '../login.service';
import { ProductService } from '../product.service';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit{
  @Input() currUser? : User; 
  cart: Product[] = [];

  constructor(private loginService: LoginService, private productService: ProductService){}

  ngOnInit(): void {
    let idArr: number[] | any[] = [];
    this.loginService.getCart(this.currUser!).subscribe(
      array => idArr = array
    );
    let prodArr: Product[] | any[] = [];
    for(let i = 0; i < idArr.length; i++){
      this.productService.getProduct(idArr[i]).subscribe(
        product => prodArr[i] = product
      );
    }
    this.cart = prodArr;
  }
}