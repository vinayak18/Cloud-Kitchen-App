export class coupon {
  couponCode: string;
  totalAmount: number;
  discountAmount: number;

  constructor(
    $couponCode: string,
    $totalAmount: number,
    $discountAmount: number
  ) {
    this.couponCode = $couponCode;
    this.totalAmount = $totalAmount;
    this.discountAmount = $discountAmount;
  }
}
