import { address } from './address';

export class customerInfo {
  name: string;
  email: string;
  phoneNo: number;
  deliveryAddress: address;
  constructor(
    $name: string,
    $email: string,
    $phoneNo: number,
    $deliveryAddress: address
  ) {
    this.name = $name;
    this.email = $email;
    this.phoneNo = $phoneNo;
    this.deliveryAddress = $deliveryAddress;
  }

  getAddress(): string {
    if (this.deliveryAddress === null) return '';
    return (
      (this.deliveryAddress.flatNo === ''
        ? ''
        : this.deliveryAddress.flatNo + ', ') +
      this.deliveryAddress.streetAddress +
      ', ' +
      (this.deliveryAddress.landmark === ''
        ? ''
        : this.deliveryAddress.landmark + ', ') +
      this.deliveryAddress.city +
      ' - ' +
      this.deliveryAddress.pincode +
      ', ' +
      this.deliveryAddress.state +
      '.'
    );
  }
}
