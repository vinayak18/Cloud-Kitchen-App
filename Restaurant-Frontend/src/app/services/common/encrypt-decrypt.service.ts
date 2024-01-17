/*eslint-disable */
import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';

@Injectable({
  providedIn: 'root',
})
export class EncryptDecryptService {
  constructor() {}

  encryption(stringToEncrypt: string, key: string) {
    if (stringToEncrypt != null) {
      var hash = CryptoJS.SHA1(key);
      const hash1 = CryptoJS.lib.WordArray.create(hash.words.slice(0, 4));
      var encrypted = CryptoJS.AES.encrypt(stringToEncrypt, hash1, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7,
      });
      return encrypted.toString();
    }
  }

  decryption(encryptedKey: string, key: string) {
    if (encryptedKey != null) {
      var hash = CryptoJS.SHA1(key);
      const hash1 = CryptoJS.lib.WordArray.create(hash.words.slice(0, 4));
      const decrypted = CryptoJS.AES.decrypt(encryptedKey, hash1, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7,
      }).toString(CryptoJS.enc.Utf8);
      return decrypted;
    }
  }
}
