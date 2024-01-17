export class blobImage {
  name: string;
  type: string;
  picByte: number[];

  constructor($name: string, $type: string, $picByte: number[]) {
    this.name = $name;
    this.type = $type;
    this.picByte = $picByte;
  }
}
