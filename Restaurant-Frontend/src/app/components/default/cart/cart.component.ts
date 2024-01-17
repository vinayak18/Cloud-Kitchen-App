import { Component, OnInit } from '@angular/core';
import { foodType } from '../../../models/foodType';
import { product } from '../../../models/product';
import { EncryptDecryptService } from 'src/app/services/common/encrypt-decrypt.service';
import { secretKey } from '../../../models/secretKey';
import { AuthService } from 'src/app/services/auth/auth.service';
import { UserService } from 'src/app/services/user-coupon-order/user.service';
import { ScreenLoaderService } from 'src/app/services/common/screen-loader.service';
import { CartItemsInfo } from 'src/app/models/cartItemsInfo';
import { ProductService } from 'src/app/services/product-review/product.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SnackbarService } from 'src/app/services/common/snackbar.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  totalAmount: number = 0;
  cartItems: product[] = [];
  cartItemsInfo: CartItemsInfo[] = [];
  isLoggedIn: boolean = false;
  isLoaded: boolean = false;
  isSaved: boolean = false;

  constructor(
    private encrypt_decrypt: EncryptDecryptService,
    private authService: AuthService,
    private userService: UserService,
    private productService: ProductService,
    private loader: ScreenLoaderService,
    private snackbar: SnackbarService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loader.isLoading.subscribe((data) => {
      this.isLoaded = data;
    });
    this.authService.isLoggedIn.subscribe((data) => {
      this.isLoggedIn = data;
    });
    if (this.isLoggedIn) {
      const currUser = this.userService.getCurrentUserDetails();
      this.cartItemsInfo = currUser.cart;
    } else {
      let data = sessionStorage.getItem(
        this.encrypt_decrypt.encryption('Cart', secretKey)
      );
      if (data !== null && data !== undefined) {
        this.cartItemsInfo = JSON.parse(
          this.encrypt_decrypt.decryption(data, secretKey)
        );
      }
    }
    this.productService.getMultiProductById(this.cartItemsInfo).subscribe((data) => {
      this.cartItems = data;
      this.getTotalAmount();
    });

  }

  removeProduct(idx: number, type: string) {
    if (type === 'input') {
      this.isSaved = true;
      this.cartItemsInfo[idx].quantity = this.cartItems[idx].quantity;
      if (this.cartItems[idx].quantity === 0) {
        this.cartItems.splice(idx, 1);
        this.cartItemsInfo.splice(idx, 1);
        if (this.cartItems.length == 0) {
          this.saveChanges();
        }
      }
    } else {
      this.cartItems.splice(idx, 1);
      this.cartItemsInfo.splice(idx, 1);
      this.saveChanges();
    }
    this.getTotalAmount();
  }

  saveChanges() {
    sessionStorage.setItem(
      this.encrypt_decrypt.encryption('Cart', secretKey),
      this.encrypt_decrypt.encryption(JSON.stringify(this.cartItemsInfo), secretKey)
    );

    if (this.isLoggedIn) {
      const currUser = this.userService.getCurrentUserDetails();
      currUser.cart = this.cartItemsInfo;
      this.userService
        .updateUser(currUser)
        .subscribe((data) => {
          this.userService.setUserDetails(data);
        });
    }
    this.isSaved = false;
  }
  getTotalAmount() {
    this.totalAmount = 0;
    for (let product of this.cartItems) {
      this.totalAmount = this.totalAmount + product.price * product.quantity;
    }
  }
  checkout() {
    let isCheckoutPossible = true;
    this.productService.getAllProducts().subscribe((data) => {
      for (let product of data) {
        this.cartItems.forEach(cartItem => {
          if (product.pid === cartItem.pid && !product.live) {
            this.snackbar.error("<b>" + cartItem.name + "</b> is currently not available. </br>Please remove to proceed.", '', { enableHtml: true });
            isCheckoutPossible = false;
          }
        });
      }
      if (isCheckoutPossible)
        this.router.navigate(['checkout'], { relativeTo: this.route });
    });

  }
}
