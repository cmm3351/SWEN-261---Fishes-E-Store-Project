import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Product } from '../product';
import { ProductService } from '../product.service';
import { User } from '../user';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: [ './product-detail.component.css' ],
  changeDetection: ChangeDetectionStrategy.Default
})
export class ProductDetailComponent implements OnInit {
  currUser?: User;
  product: Product | undefined;
  isAdmin : boolean = false;
  imgSource? : String;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private location: Location,
  ) {}


  ngOnInit(): void {
    this.currUser = history.state.user;
    this.getProduct();
    this.isAdmin = history.state.isAdmin;
    if (this.product?.imgSource == ""){
      this.imgSource = "https://i.pinimg.com/originals/a4/9b/7e/a49b7ed6a8b8e96b29a38c69019bf6e3.png";
    }else{
      this.imgSource = this.product?.imgSource;
    }

  }

  getProduct(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!);
    if (!Number.isNaN(id)) {
    this.productService.getProduct(id)
      .subscribe(product => this.product = product);
    }
  }

  addToCart(){
    this.productService.addToCart(this.currUser!, this.product!).subscribe();
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
