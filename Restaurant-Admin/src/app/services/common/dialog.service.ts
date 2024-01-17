import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from 'src/app/components/navbar/shared/dialog/dialog.component';

@Injectable({
  providedIn: 'root'
})
export class DialogService {

  constructor(private dialog: MatDialog) { }

  showDialog(content): any{
    return this.dialog.open(DialogComponent, {
      width: '320px',
      height: '170px',
      data: content,
    });
  }
}
