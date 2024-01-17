import { Component, HostListener, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { BreakpointObserver } from '@angular/cdk/layout';
import { StepperOrientation, MatStepper } from '@angular/material/stepper';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { order } from 'src/app/models/order';
import { status } from 'src/app/models/status';
import { foodType } from 'src/app/models/foodType';
import { address } from 'src/app/models/address';
import { customerInfo } from 'src/app/models/customerInfo';
import { product } from 'src/app/models/product';
import { CouponService } from 'src/app/services/user-coupon-order/coupon.service';
import { SnackbarService } from 'src/app/services/common/snackbar.service';
import { loadStripe } from '@stripe/stripe-js';
import { environment } from 'src/environments/environment';
import { OrderService } from 'src/app/services/user-coupon-order/order.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/services/user-coupon-order/user.service';
import { EncryptDecryptService } from 'src/app/services/common/encrypt-decrypt.service';
import { userDetails } from 'src/app/models/userDetails';
import { coupon } from 'src/app/models/coupon';
import { secretKey } from 'src/app/models/secretKey';
import { ProductService } from 'src/app/services/product-review/product.service';
declare var Razorpay: any;

/**
 * @title Stepper responsive
 */
@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
})
export class CheckoutComponent implements OnInit {
  currUser: userDetails;
  stripePromise = loadStripe(environment.stripe);
  editable = true;
  discountAmount: number = 0;
  breakfastList: product[] = [];
  lunchList: product[] = [];
  dinnerList: product[] = [];
  specialDishList: product[] = [];
  deliverySwitch: boolean = true;
  paymentForm: FormGroup;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup = this._formBuilder.group({
    thirdCtrl: ['', Validators.required],
  });
  stepperOrientation: Observable<StepperOrientation>;
  orderSummary: order;
  constructor(
    private _formBuilder: FormBuilder,
    private breakpointObserver: BreakpointObserver,
    private couponService: CouponService,
    private orderService: OrderService,
    private snackbarService: SnackbarService,
    private userService: UserService,
    private productService: ProductService,
    private encrypt: EncryptDecryptService,
    private router: Router
  ) {
    this.stepperOrientation = this.breakpointObserver
      .observe('(min-width: 800px)')
      .pipe(map(({ matches }) => (matches ? 'horizontal' : 'vertical')));
  }
  ngOnInit(): void {
    this.currUser = this.userService.getCurrentUserDetails();
    this.paymentForm = this._formBuilder.group({
      paymentType: ['stripe', Validators.required],
    });
    this.firstFormGroup = this._formBuilder.group({
      userId: [this.currUser.userId, Validators.required],
      name: [this.currUser.name, Validators.required],
      email: [this.currUser.email, Validators.required],
      phoneNo: [
        this.currUser.phoneNo != '0' ? this.currUser.phoneNo : '',
        Validators.required,
      ],
    });
    this.secondFormGroup = this._formBuilder.group({
      deliveryType: ['Delivery', Validators.required],
      streetAddress: ['', Validators.required],
      flatNo: ['', Validators.required],
      landmark: [''],
      city: ['', Validators.required],
      state: ['', Validators.required],
      pincode: ['', Validators.required],
    });
    this.setDeliveryAddress();
    this.orderSummary = {
      orderId: null,
      userId: null,
      dateOfOrder: null,
      orderDetails: null,
      actualAmount: 0,
      tax: 0,
      deliveryFee: 0,
      coupon: {
        couponCode: 'DEFAULT0',
        totalAmount: 1000,
        discountAmount: 0.0,
      },
      netAmount: 0,
      deliveryType: null,
      customerInfo: null,
      status: status.PLACED,
      paymentType: 'UPI',
      paymentId: null,
      rating: 0,
    };
    this.productService.getMultiProductById(this.currUser.cart).subscribe((data) => {
      this.orderSummary.orderDetails = data;
      this.breakfastList = this.orderSummary.orderDetails.filter(
        (value) => value.type === foodType.BREAKFAST
      );
      this.lunchList = this.orderSummary.orderDetails.filter(
        (value) => value.type === foodType.LUNCH
      );
      this.dinnerList = this.orderSummary.orderDetails.filter(
        (value) => value.type === foodType.DINNER
      );
      this.specialDishList = this.orderSummary.orderDetails.filter(
        (value) => value.type === foodType.SPECIAL_DISH
      );
      for(let product of this.orderSummary.orderDetails){
        product.img_url = null;
      }
    });

  }
  setDeliveryAddress() {
    if (0 !== this.currUser.address.length) {
      let activeAddress: address = this.currUser.address.filter(
        (value) => value.active
      )[0];

      this.secondFormGroup.setValue({
        deliveryType: 'Delivery',
        streetAddress: activeAddress.streetAddress,
        flatNo: activeAddress.flatNo,
        landmark: activeAddress.landmark,
        pincode: activeAddress.pincode,
        state: activeAddress.state,
        city: activeAddress.city,
      });
    }
    console.log('x');
    console.log(this.secondFormGroup);
  }
  toggleDeliverySwitch(deliveryType: string) {
    if (deliveryType === 'Pick-Up') {
      this.deliverySwitch = false;
    } else {
      this.deliverySwitch = true;
    }
    this.secondFormGroup.value.deliveryType = deliveryType;
  }
  updatePersonalInfo() {
    this.orderSummary.userId = this.firstFormGroup.value.userId;
    let personalInfo = new customerInfo(
      this.firstFormGroup.value.name,
      this.firstFormGroup.value.email,
      this.firstFormGroup.value.phoneNo,
      null
    );
    this.orderSummary.customerInfo = personalInfo;
    console.log(this.orderSummary);
  }
  setDefaultAddress() {
    let defaultAddress = new address(
      '207, Bangur Avenue',
      '',
      'Akash Sutra Lane',
      700055,
      'West Bengal',
      'Kolkata',
      false
    );
    return defaultAddress;
  }
  updateAddress(): Promise<any> {
    if (this.secondFormGroup.valid) {
      this.deliverySwitch = false;
    }
    this.orderSummary.deliveryType = this.secondFormGroup.value.deliveryType;
    let deliveryAddress: address = null;
    if (this.orderSummary.deliveryType === 'Delivery') {
      deliveryAddress = new address(
        this.secondFormGroup.value.streetAddress,
        this.secondFormGroup.value.flatNo,
        this.secondFormGroup.value.landmark,
        this.secondFormGroup.value.pincode,
        this.secondFormGroup.value.state,
        this.secondFormGroup.value.city,
        false
      );
    } else {
      deliveryAddress = this.setDefaultAddress();
    }
    this.orderSummary.customerInfo.deliveryAddress = deliveryAddress;
    return new Promise((resolve, reject) => {
      if (this.orderSummary.deliveryType === 'Delivery') {
        let matrixObject = {
          source:
            '207, Bangur Avenue, Block - B, Akash Sutra Lane, Kolkata - 700055, West Bengal.',
          destination: this.orderSummary.customerInfo.getAddress(),
        };
        console.log(matrixObject);
        this.orderService
          .calculateDistanceMatrix(matrixObject)
          .subscribe((data) => {
            console.log(data);
            let element = data.rows[0].elements[0];
            this.calulateDeliveryFee(element.distance.value);
            resolve(true);
          });
      }
      else {
        resolve(true);
      }
    });
  }
  calulateDeliveryFee(distance) {
    distance = Math.ceil(distance / 1000);
    if (distance <= 2) {
      this.orderSummary.deliveryFee = 50;
    } else if (distance > 2 && distance <= 7) {
      this.orderSummary.deliveryFee = 50 + (distance - 2) * 15;
    } else {
      this.orderSummary.deliveryFee = 125 + (distance - 7) * 20;
    }
  }
  async selectionChange(event: any) {
    if (event.selectedIndex == 1) {
      if (this.secondFormGroup.value.deliveryType === 'Delivery') {
        this.deliverySwitch = true;
      }
      this.updatePersonalInfo();
    } else if (event.selectedIndex == 2) {
      await this.updateAddress();
      this.getTotalAmount();
    }
    if (event.selectedIndex != 1 && this.secondFormGroup.valid) {
      this.deliverySwitch = false;
    }
  }
  getTotalAmount() {
    let totalAmount = 0;
    for (let product of this.orderSummary.orderDetails) {
      totalAmount = totalAmount + product.price * product.quantity;
    }
    this.orderSummary.actualAmount = totalAmount;
    this.calculateTax();
  }
  calculateTax() {
    let tax =
      (this.orderSummary.actualAmount -
        this.orderSummary.coupon.discountAmount) *
      0.05;
    this.orderSummary.tax = tax;
    this.setNetTotal();
  }
  setNetTotal() {
    this.orderSummary.netAmount =
      this.orderSummary.actualAmount -
      this.orderSummary.coupon.discountAmount +
      this.orderSummary.tax +
      this.orderSummary.deliveryFee;
  }
  validateCouponCode(code: string) {
    const couponData = {
      couponCode: code,
      totalAmount: this.orderSummary.actualAmount,
    };
    this.couponService.getCouponViaCode(couponData).subscribe(
      (data) => {
        this.orderSummary.coupon = data;
        this.calculateTax();
      },
      (err) => {
        this.snackbarService.error('Invalid Coupon Code', '');
        this.orderSummary.coupon = new coupon('DEFAULT0', 0, 0);
        this.calculateTax();
      }
    );
  }
  async onPaymentViaStripe() {
    const payment = {
      name: 'Total Amount',
      email: this.orderSummary.customerInfo.email,
      currency: 'inr',
      amount: this.orderSummary.netAmount,
      quantity: '1',
      cancelUrl: 'https://localhost:4200/cart/checkout',
      successUrl: 'https://localhost:4200/order/confirmation',
    };
    const stripe = await this.stripePromise;

    this.orderService.initiateStripePayment(payment).subscribe((data: any) => {
      // I use stripe to redirect To Checkout page of Stripe platform
      console.log(data);
      this.orderSummary.paymentId = data.id;
      sessionStorage.setItem(
        this.encrypt.encryption('Order Summary', secretKey),
        this.encrypt.encryption(JSON.stringify(this.orderSummary), secretKey)
      );
      stripe.redirectToCheckout({
        sessionId: data.id,
      });
    });
  }
  onPaymentViaRazorPay() {
    const payment = {
      name: 'Total Amount',
      currency: 'inr',
      amount: this.orderSummary.netAmount,
      quantity: '1',
      cancelUrl: 'https://localhost:4200/cart/checkout',
      successUrl: 'https://localhost:4200/order/confirmation',
    };
    let options = {
      key: '',
      amount: '',
      name: 'AMMA KI KADAI',
      description: 'Web Development',
      image: '../../../assets/img/hero-1.jpg',
      order_id: '',
      handler: function (response) {
        var event = new CustomEvent('payment.success', {
          detail: response,
          bubbles: true,
          cancelable: true,
        });
        window.dispatchEvent(event);
      },
      prefill: {
        name: '',
        email: '',
        contact: '',
      },
      notes: {
        address: '',
      },
      theme: {
        color: '#ffb633',
      },
    };
    let paymentId = '';
    let error = '';
    this.orderService.initiateRazorPayPayment(payment).subscribe(
      (data) => {
        options.key = data.secretId;
        options.order_id = data.razorpayOrderId;
        options.amount = data.applicationFee; //paise
        options.prefill.name = this.orderSummary.customerInfo.name;
        options.prefill.email = this.orderSummary.customerInfo.email;
        options.prefill.contact = '' + this.orderSummary.customerInfo.phoneNo;

        var rzp = new Razorpay(options);
        rzp.open();

        rzp.on('payment.failed', function (response) {
          // Todo - store this information in the server
          console.log(response);
          console.log(response.error.code);
          console.log(response.error.description);
          console.log(response.error.source);
          console.log(response.error.step);
          console.log(response.error.reason);
          console.log(response.error.metadata.order_id);
          console.log(response.error.metadata.payment_id);
          this.error = response.error.reason;
        });
      },
      (err) => {
        console.log(err.error.message);
      }
    );
  }

  @HostListener('window:payment.success', ['$event'])
  onPaymentSuccess(event): void {
    console.log(event.detail);
    this.orderSummary.paymentId = event.detail.razorpay_payment_id;
    sessionStorage.setItem(
      this.encrypt.encryption('Order Summary', secretKey),
      this.encrypt.encryption(JSON.stringify(this.orderSummary), secretKey)
    );
    this.router.navigateByUrl('/order/confirmation');
  }
  async placeOrder() {
    this.orderSummary.paymentType = this.paymentForm.get('paymentType').value;
    sessionStorage.setItem(
      this.encrypt.encryption('Order Summary', secretKey),
      this.encrypt.encryption(JSON.stringify(this.orderSummary), secretKey)
    );

    if (this.orderSummary.paymentType === 'stripe') {
      this.onPaymentViaStripe();
    } else {
      this.onPaymentViaRazorPay();
    }
  }
}
