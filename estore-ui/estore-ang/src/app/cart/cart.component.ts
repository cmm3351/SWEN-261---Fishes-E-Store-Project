import { Component, OnInit} from '@angular/core';

import { User } from '../user';
import { Product } from '../product';
import { LoginService } from '../login.service';
import { ProductService } from '../product.service';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit{
  currUser? : User; 
  cart: Product[] = [];
  checkoutMessage?: string;
  totalPrice: number = 0;

  constructor(private loginService: LoginService, private productService: ProductService, 
              private location: Location, private router: Router){}

  ngOnInit(): void {
    this.currUser = this.loginService.getUser();
    this.totalPrice = 0;

    let idArr: number[] | any[] = [];
    let prodArr: Product[] | any[] = [];
    this.loginService.getCart(this.currUser!).subscribe(
      (array) => {idArr = array;
      for(let i = 0; i < idArr.length; i++){
        this.productService.getProduct(idArr[i]).subscribe(
          (product) => {
            prodArr[i] = product;
            this.totalPrice += product.price;
      });
      }
    }
    );
    this.cart = prodArr;
  }

  deleteFromCart(product: Product) {
    let isInCart = this.loginService.getIsInCart().get(product.id);
    this.loginService.setIsInCart(product.id,isInCart! - 1);
    this.loginService.deleteFromCart(this.currUser!, product).subscribe(
      () => this.ngOnInit());
  }

  checkout() : void {
    if (this.cart.length != 0) {
      this.loginService.checkout(this.currUser!).subscribe(
        () => { 
          this.ngOnInit();
          this.checkoutMessage = "Thank You for Your Purchase!"
      });
      
    }
    else {
      this.checkoutMessage = "No Fish Present in the Cart to Purchase!";
    }
  }

  /** TODO */
  useRewardsPoints(cid: number) : void {
    this.loginService.useRewardsPoints(this.currUser!,cid).subscribe(
      () => {
        this.ngOnInit();
        this.checkoutMessage = "Thank You for Using Your Rewards!";
      });

  }

  goBack(): void {
    this.location.back();
  }
}