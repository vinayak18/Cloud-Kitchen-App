package com.restaurant.userservice.service;

import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.User;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.restaurant.userservice.exception.InvalidUsernameOrPasswordException;
import com.restaurant.userservice.exception.UserAlreadyExistsException;
import com.restaurant.userservice.exception.UserNotFoundException;
import com.restaurant.userservice.model.BlobImage;
import com.restaurant.userservice.model.CartItemsInfo;
import com.restaurant.userservice.model.Product;
import com.restaurant.userservice.model.ProfilePicture;
import com.restaurant.userservice.model.ResetPwdObject;
import com.restaurant.userservice.model.UserDetails;

public interface UserService {

	public boolean isUserExists(String email);
	public ResponseEntity<Object> getUserByEmailForSocialLogin(String email) throws UserNotFoundException;
	public ResponseEntity<Object> addNewUserViaGoogleSocialLogin(Payload payload);
	public ResponseEntity<Object> addNewUser(UserDetails user) throws UserAlreadyExistsException;
	public ResponseEntity<Object> validateUser(UserDetails user) throws UserNotFoundException, InvalidUsernameOrPasswordException;
	public ResponseEntity<Object> updateUser(UserDetails user) throws UserNotFoundException, InvalidUsernameOrPasswordException;
	public ResponseEntity<Object> getUserByEmail(String email) throws UserNotFoundException;
	public ResponseEntity<Object> getUserByUserId(String userId) throws UserNotFoundException;
	public ResponseEntity<Object> updateUserCart(String userId, List<CartItemsInfo> cartItems) throws UserNotFoundException;
	public ResponseEntity<Object> getAllUsers() throws UserNotFoundException;
	public ResponseEntity<Object> addNewUserViaFacebookSocialLogin(User user, ProfilePicture picture);
	public String checkUser(String email) throws UserNotFoundException;
	public ResponseEntity<Object> uploadUserImage(String userId, MultipartFile file) throws IOException, UserNotFoundException;
	public ResponseEntity<Object> getUserImage(BlobImage image) throws DataFormatException, IOException;
	public ResponseEntity<Object> resetPassword(ResetPwdObject resetPwdObj) throws UserNotFoundException;
	public ResponseEntity<Object> getAllCustomers();
}
