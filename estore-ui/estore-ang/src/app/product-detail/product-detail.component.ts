import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Product } from '../product';
import { User } from '../user';
import { ProductService } from '../product.service';
import { DataService } from '../data.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: [ './product-detail.component.css' ]
})
export class ProductDetailComponent implements OnInit {
  product: Product | undefined;
  currUser: User = {id:-1,username:"",password:"",isAdmin:false};

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private location: Location,
    private dataService: DataService
  ) {}

  ngOnInit(): void {
    this.getProduct();
    this.dataService.currMessage.subscribe(currUser => this.currUser = currUser);
  }

  getProduct(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.productService.getProduct(id)
      .subscribe(product => this.product = product);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.product) {
      this.productService.updateProduct(this.product)
        .subscribe(() => this.goBack());
    }
  }
}
