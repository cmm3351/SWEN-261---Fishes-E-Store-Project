import { Component, OnInit, OnChanges, Input, SimpleChanges, ChangeDetectionStrategy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Product } from '../product';
import { User } from '../user';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: [ './product-detail.component.css' ],
  changeDetection: ChangeDetectionStrategy.Default
})
export class ProductDetailComponent implements OnInit, OnChanges {
  product: Product | undefined;
  @Input() currUser? : User;
  isAdmin : boolean = false;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private location: Location,
  ) {}


  ngOnInit(): void {
    this.getProduct();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['currUser'].currentValue != undefined) {
      this.isAdmin = changes['currUser'].currentValue.isAdmin;
    }
  }

  getProduct(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!);
    console.log(this.route);
    console.log(this.route.snapshot);
    console.log(this.route.snapshot.paramMap);
    console.log(id);
    if (!Number.isNaN(id)) {
    this.productService.getProduct(id)
      .subscribe(product => this.product = product);
    }
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
