export class address {
  streetAddress: string;
  flatNo: string;
  landmark: string;
  pincode: number;
  state: string;
  city: string;
  active: boolean;

  constructor(
    $streetAddress: string,
    $flatNo: string,
    $landmark: string,
    $pincode: number,
    $state: string,
    $city: string,
    $active: boolean
  ) {
    this.streetAddress = $streetAddress;
    this.flatNo = $flatNo;
    this.landmark = $landmark;
    this.pincode = $pincode;
    this.state = $state;
    this.city = $city;
    this.active = $active;
  }
}
