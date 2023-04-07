import { Component, OnInit, OnChanges, Input, SimpleChanges, ChangeDetectionStrategy } from '@angular/core';

import { User } from '../user';
import { Product } from '../product';
import { LoginService } from '../login.service';
import { ProductService } from '../product.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit{
  currUser? : User; 
  cart: Product[] = [];
  checkoutMessage?: string;

  constructor(private loginService: LoginService, private productService: ProductService, private location: Location){}

  ngOnInit(): void {
    this.currUser = history.state.user;

    let idArr: number[] | any[] = [];
    let prodArr: Product[] | any[] = [];
    this.loginService.getCart(this.currUser!).subscribe(
      (array) => {idArr = array;
      for(let i = 0; i < idArr.length; i++){
        this.productService.getProduct(idArr[i]).subscribe(
          product => prodArr[i] = product
        );
      }
    }
    );
    this.cart = prodArr;
  }

  deleteFromCart(product: Product) {
    let isInCart = this.productService.getIsInCart().get(product.id);
    this.productService.setIsInCart(product.id,isInCart! - 1);
    this.loginService.deleteFromCart(this.currUser!, product).subscribe();
    location.reload();
  }

  checkout() : void {
    if (this.cart.length != 0) {
      this.loginService.checkout(this.currUser!).subscribe();
      location.reload();
    }
    else {
      this.checkoutMessage = "No Fish Present in the Cart to Purchase!";
    }
  }

  goBack(): void {
    this.location.back();
  }
}