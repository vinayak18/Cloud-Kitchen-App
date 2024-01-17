import { DialogService } from './../../../../services/common/dialog.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { foodType } from 'src/app/models/foodType';
import { product } from 'src/app/models/product';
import { ScreenLoaderService } from 'src/app/services/common/screen-loader.service';
import { SnackbarService } from 'src/app/services/common/snackbar.service';
import { ProductService } from 'src/app/services/product-review/product.service';

@Component({
  selector: 'app-menu-item',
  templateUrl: './menu-item.component.html',
  styleUrls: ['../menu.component.scss', './menu-item.component.scss'],
})
export class MenuItemComponent implements OnInit {
  pid: string = '';
  productDetails: product = {} as product;
  isLoaded: boolean = false;
  selectedFile: File;
  fileBlob: Blob;
  productFormGroup: FormGroup;
  foodType: foodType[] = [
    foodType.BREAKFAST,
    foodType.LUNCH,
    foodType.DINNER,
    foodType.SPECIAL_DISH,
  ];
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private loader: ScreenLoaderService,
    private productService: ProductService,
    private snackbar: SnackbarService,
    private formBuilder: FormBuilder,
    private dialogService: DialogService
  ) {}
  ngOnInit(): void {
    this.loader.isLoading.subscribe((data) => {
      this.isLoaded = data;
    });
    this.createProductFromGroup();
    this.route.params.subscribe((val: Params) => {
      this.pid = val['pid'];
      this.getProductById(this.pid);
    });
  }

  getProductById(pid: string) {
    this.productService.getProductById(pid).subscribe((data: product) => {
      this.productDetails = data;
      this.createProductFromGroup();
    });
  }

  createProductFromGroup() {
    this.productFormGroup = this.formBuilder.group({
      name: [this.productDetails.name, Validators.required],
      desc: [this.productDetails.desc, Validators.required],
      price: [this.productDetails.price, Validators.required],
      category: [this.productDetails.category, Validators.required],
    });
  }

  //Gets called when the user selects an image
  async onFileChanged(event, idx: number) {
    //Select File
    this.selectedFile = event.target.files[0];
    this.fileBlob = new Blob(
      [new Uint8Array(await this.selectedFile.arrayBuffer())],
      { type: this.selectedFile.type }
    );
    const reader = new FileReader();
    reader.readAsDataURL(this.fileBlob);
    reader.onloadend = () => {
      const base64 = reader.result;
      this.productDetails.img_url[idx] = String(base64);
    };
  }

  saveChanges() {
    this.productDetails.name = this.productFormGroup.get('name').value;
    this.productDetails.desc = this.productFormGroup.get('desc').value;
    this.productDetails.price = this.productFormGroup.get('price').value;
    this.productDetails.category = this.productFormGroup.get('category').value;

    this.productService.updateProduct(this.productDetails).subscribe((data) => {
      this.snackbar.success('Product Details Updated Successfully', '');
      this.router.navigate(['../'], { relativeTo: this.route });
    });
  }

  deleteItem() {
    let content = {
      title: "Delete Item",
      msg: "Do you want to continue?"
    }
    const optionSelected = this.dialogService.showDialog(content);
    optionSelected.afterClosed().subscribe((result)=>{
      if(result === true){
        this.productService.deleteProduct(this.productDetails.pid).subscribe((data) => {
          this.snackbar.success('Menu Item Deleted Successfully', '');
          this.router.navigate(['../'], {relativeTo : this.route})
        });
      }
    })
    
  }

  discardChanges(){
    let content = {
      title: 'Discard Changes',
      msg: 'Do you want to continue?',
    };
    const optionSelected = this.dialogService.showDialog(content);
    optionSelected.afterClosed().subscribe((result) => {
      if (result === true) {
        this.router.navigate(['../'], { relativeTo: this.route });
      }
    });
  }

  changeType(type: foodType) {
    this.productDetails.type = type;
  }

  liveStatusChange() {
    this.productDetails.live = !this.productDetails.live;
  }
}
