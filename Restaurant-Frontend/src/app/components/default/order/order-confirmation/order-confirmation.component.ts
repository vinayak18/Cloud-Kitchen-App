import { Component, OnInit } from '@angular/core';
import { EncryptDecryptService } from 'src/app/services/common/encrypt-decrypt.service';
import { ScreenLoaderService } from 'src/app/services/common/screen-loader.service';
import { OrderService } from 'src/app/services/user-coupon-order/order.service';
import { order } from '../../../../models/order';
import { secretKey } from '../../../../models/secretKey';
import { UserService } from 'src/app/services/user-coupon-order/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-order-confirmation',
  templateUrl: './order-confirmation.component.html',
  styleUrls: ['./order-confirmation.component.css'],
})
export class OrderConfirmationComponent implements OnInit {
  isLoaded: boolean = false;
  orderSummary: order;

  constructor(
    private encrypt_decrypt: EncryptDecryptService,
    private orderService: OrderService,
    private userService: UserService,
    private router: Router,
    private loader: ScreenLoaderService
  ) {}
  ngOnInit(): void {
    this.loader.isLoading.subscribe((data) => {
      this.isLoaded = data;
    });
    this.clearCart();
    this.addOrder();
  }
  addOrder() {
    const value = sessionStorage.getItem(
      this.encrypt_decrypt.encryption('Order Summary', secretKey)
    );
    this.orderSummary = JSON.parse(
      this.encrypt_decrypt.decryption(value, secretKey)
    );
    if (this.orderSummary != null && this.orderSummary.orderId === null) {
      this.orderService.addNewOrder(this.orderSummary).subscribe((data) => {
        this.orderSummary.orderId = data.orderId;
        this.orderSummary.dateOfOrder = data.dateOfOrder;
        sessionStorage.setItem(
          this.encrypt_decrypt.encryption('Order Summary', secretKey),
          this.encrypt_decrypt.encryption(
            JSON.stringify(this.orderSummary),
            secretKey
          )
        );
      });
    }
  }
  clearCart() {
    let currUser = this.userService.getCurrentUserDetails();
    currUser.cart = [];
    this.userService
      .updateUser(currUser)
      .subscribe((user) => {
        this.userService.setUserDetails(user);
      });
    sessionStorage.removeItem(
      this.encrypt_decrypt.encryption('Cart', secretKey)
    );
  }
  goToMenu() {
    sessionStorage.removeItem(
      this.encrypt_decrypt.encryption('Order Summary', secretKey)
    );
    this.router.navigateByUrl('/menu');
  }
}
