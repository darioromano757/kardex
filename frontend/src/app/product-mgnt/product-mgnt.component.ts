import { Component, OnInit, Inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Product } from './product';

@Component({
  selector: 'app-load-menu',
  templateUrl: './product-mgnt.component.html',
  styleUrls: ['./product-mgnt.component.css']
})
@Injectable()
export class ProductManagmentComponent implements OnInit {
  
  products = [];
  newProduct: Product = new Product();

  //products: any[];

  productId: number;

  baseUrl = "http://localhost:8080/api/products";
  
  constructor(private http: HttpClient) {
    
  }

  ngOnInit() {
    this.loadProducts();
  }

  loadProducts() {
    this.http.get(this.baseUrl).subscribe((response:Product[]) => {
      this.products =  response;
      return this.products;
    });
  }

  addStock(prodId) {
    console.log(prodId);
    this.http.put(this.baseUrl + "/stock/add/" + prodId, {}).subscribe((response:Product) => {
      this.products.forEach(element => {
        if (element.id == prodId) {
          element.quantity = response.quantity;
        }
      });
      alert("Se ha agregado un producto al stock")
    });
  }



  removeStock (prodId) {
    console.log(prodId);
    this.http.put(this.baseUrl + "/stock/remove/" + prodId, { }).subscribe((response:Product) => {
      this.products.forEach(element => {
        if (element.id == prodId) {
          element.quantity = response.quantity;
        }
      });
      alert("Se ha quitado un producto al stock");
    });
  }
   
  updateStock (product:Product) {
    this.newProduct = new Product();
    this.newProduct.id = product.id;
    this.newProduct.code = product.code;
    this.newProduct.description = product.description;
    this.newProduct.quantity = product.quantity;
    this.newProduct.price = product.price;
  }

  addProduct() {
    this.http.post(this.baseUrl, this.newProduct).subscribe((response: Product) => {
      this.clearProduct();
      alert("El producto se ha creado correctamente");
      this.loadProducts();
    });
  }

  editProduct() {
    this.http.put(this.baseUrl, this.newProduct).subscribe((response: Product) => {
      this.clearProduct();
      alert("El producto se ha editado correctamente");
      this.loadProducts();
    });
  }

  clearProduct() {
    this.newProduct = new Product();
  }
}