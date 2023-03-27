import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ProductsComponent } from './products/products.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { CartComponent } from './cart/cart.component';
import { LoginComponent } from './login/login.component';
import { CreateAccComponent } from './create-acc/create-acc.component';



const routes: Routes = [
  { path: 'products', component: ProductsComponent },
  { path: 'cart/:id', component: CartComponent},
  { path: '', redirectTo: '/products', pathMatch: 'full' },
  { path: 'detail/:id', component: ProductDetailComponent},
  { path: 'login', component: LoginComponent},
  { path: 'login/create', component: CreateAccComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{enableTracing : true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }

