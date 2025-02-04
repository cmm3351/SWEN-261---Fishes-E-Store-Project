import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms'

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './login/login.component';
import { ProductsComponent } from './products/products.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { ProductSearchComponent } from './product-search/product-search.component';

import { CartComponent } from './cart/cart.component';
import { CreateAccComponent } from './create-acc/create-acc.component';
import { LoginService } from './login.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    CreateAccComponent,
    ProductsComponent,
    ProductDetailComponent,
    ProductSearchComponent,
    CartComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [LoginComponent,
              ProductsComponent,
              ProductDetailComponent,
              LoginService],
  bootstrap: [AppComponent]
})
export class AppModule { }
