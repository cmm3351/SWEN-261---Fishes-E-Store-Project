import { Component, OnInit, OnChanges, Input, SimpleChanges, ChangeDetectionStrategy } from '@angular/core';

import { Product } from '../product';
import { User } from '../user';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css'],
  changeDetection: ChangeDetectionStrategy.Default
})
export class ProductsComponent implements OnInit, OnChanges {
  products: Product[] = []; 
  @Input() currUser? : User; 
  isAdmin : boolean = false;


  constructor(private productService: ProductService,
              ) { }

  ngOnInit(): void {
    this.getProducts();
  }

  ngOnChanges(changes: SimpleChanges) {
    console.log(changes['currUser'].currentValue);
    if (changes['currUser'].currentValue != undefined) {
      this.isAdmin = changes['currUser'].currentValue.isAdmin;
    }
    console.log(this.isAdmin);
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
