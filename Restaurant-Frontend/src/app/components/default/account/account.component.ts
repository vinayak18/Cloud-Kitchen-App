import { Component } from '@angular/core';
import { userDetails } from '../../../models/userDetails';
import { UserService } from 'src/app/services/user-coupon-order/user.service';
import { SnackbarService } from 'src/app/services/common/snackbar.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css'],
})
export class AccountComponent {
  constructor(
    private userService: UserService,
    private snacbar: SnackbarService
  ) { }

  currUser: userDetails;
  selectedFile: File;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message: string;
  imageName: any;

  ngOnInit(): void {
    this.currUser = this.userService.getCurrentUserDetails();
    if (null !== this.currUser.blobImage) this.getImage();
  }
  //Gets called when the user selects an image
  public onFileChanged(event) {
    //Select File
    this.selectedFile = event.target.files[0];
    this.onUpload();
  }

  //Gets called when the user clicks on submit to upload the image
  onUpload() {
    console.log(this.selectedFile);

    //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
    const uploadImageData = new FormData();
    uploadImageData.append(
      'imageFile',
      this.selectedFile,
      this.selectedFile.name
    );

    //Make a call to the Spring Boot Application to save the image
    this.userService
      .uploadUserImage(this.currUser.userId, uploadImageData)
      .subscribe(
        (response) => {
          console.log(response);
          this.currUser = response;
          this.userService.setUserDetails(this.currUser);
          this.snacbar.success('Image uploaded successfully', '');
          this.getImage();
        },
        (error) => {
          this.snacbar.error('Image size must not exceed 1MB', '');
        }
      );
  }

  //Gets called when the user clicks on retieve image button to get the image from back end
  getImage() {
    //Make a call to Sprinf Boot to get the Image Bytes.
    this.userService.getUserImage(this.currUser.blobImage).subscribe((res) => {
      this.retrieveResonse = res;
      this.base64Data = this.retrieveResonse.picByte;
      this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
      this.currUser.img_url = this.retrievedImage;
    });
  }
}
