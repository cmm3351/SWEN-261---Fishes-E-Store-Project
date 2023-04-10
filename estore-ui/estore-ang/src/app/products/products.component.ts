import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';

import { Product } from '../product';
import { User } from '../user';
import { ProductService } from '../product.service';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css'],
  changeDetection: ChangeDetectionStrategy.Default
})
export class ProductsComponent implements OnInit {
  products: Product[] = []; 
  isAdmin : boolean = false;
  currUser? : User;
  imgSource? : String;


  constructor(private productService: ProductService,
              private loginService: LoginService) { }

  ngOnInit(): void {
    this.getProducts();
    this.currUser = this.loginService.getUser();
  }


  getProducts(): void {
    this.productService.getProducts()
    .subscribe(products => this.products = products);
  }

  add( name: string, info: string, priceStr: string, quantityStr: string, imgSrc: string): void {
    var id:number = -1;
    name = name.trim();
    info = info.trim();
    let imgSource = "https://i.pinimg.com/originals/a4/9b/7e/a49b7ed6a8b8e96b29a38c69019bf6e3.png";
    if(imgSrc != ""){
      imgSource = imgSrc;
    }
    var price: number = +priceStr;
    var quantity: number = +quantityStr;
    var reviews = new Map<String,Number>();
    if (!name) { return; }
    this.productService.addProduct({ id,name,info,price,quantity,imgSource,reviews} as Product)
      .subscribe(product => {
        this.products.push(product);
      });
  }

  delete(product: Product): void {
    this.products = this.products.filter(p => p !== product);
    this.productService.deleteProduct(product.id).subscribe();
  }

}
