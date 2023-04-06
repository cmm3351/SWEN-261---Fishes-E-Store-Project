import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';

import { Product } from '../product';
import { User } from '../user';
import { ProductService } from '../product.service';

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


  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.getProducts();
    this.currUser = history.state.currUser;
  }


  getProducts(): void {
    this.productService.getProducts()
    .subscribe(products => this.products = products);
  }

  add( name: string, info: string, priceStr: string, quantityStr: string): void {
    var id:number = -1;
    name = name.trim();
    info = info.trim();
    let imgSource = "";
    var price: number = +priceStr;
    var quantity: number = +quantityStr;
    if (!name) { return; }
    this.productService.addProduct({ id,name,info,price,quantity,imgSource } as Product)
      .subscribe(product => {
        this.products.push(product);
      });
  }

  delete(product: Product): void {
    this.products = this.products.filter(p => p !== product);
    this.productService.deleteProduct(product.id).subscribe();
  }

}
