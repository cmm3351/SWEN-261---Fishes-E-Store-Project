import { Component, OnInit } from '@angular/core';

import { Product } from '../product';
import { User } from '../user';
import { LoginComponent } from '../login/login.component';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: Product[] = [];
  currUser? : User;


  constructor(private productService: ProductService,
              private loginComponent: LoginComponent) { }

  ngOnInit(): void {
    this.getProducts();
    this.currUser = this.loginComponent.getCurrUser();
  }

  getProducts(): void {
    this.productService.getProducts()
    .subscribe(products => this.products = products);
  }

  add( name: string, info: string, priceStr: string, quantityStr: string): void {
    var id:number = -1;
    name = name.trim();
    info = info.trim();
    var price: number = +priceStr;
    var quantity: number = +quantityStr;
    if (!name) { return; }
    this.productService.addProduct({ id,name,info,price,quantity } as Product)
      .subscribe(product => {
        this.products.push(product);
      });
  }

  delete(product: Product): void {
    this.products = this.products.filter(p => p !== product);
    this.productService.deleteProduct(product.id).subscribe();
  }

}
