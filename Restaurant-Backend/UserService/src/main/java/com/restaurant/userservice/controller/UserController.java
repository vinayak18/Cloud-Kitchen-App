package com.restaurant.userservice.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.restaurant.userservice.constants.SecurityConstants;
import com.restaurant.userservice.constants.UserServiceConstants;
import com.restaurant.userservice.exception.InvalidUsernameOrPasswordException;
import com.restaurant.userservice.exception.UserAlreadyExistsException;
import com.restaurant.userservice.exception.UserNotFoundException;
import com.restaurant.userservice.model.AuthSuccessResponse;
import com.restaurant.userservice.model.BlobImage;
import com.restaurant.userservice.model.CartItemsInfo;
import com.restaurant.userservice.model.Product;
import com.restaurant.userservice.model.ProfilePicture;
import com.restaurant.userservice.model.ResetPwdObject;
import com.restaurant.userservice.model.SocialLoginToken;
import com.restaurant.userservice.model.UserDetails;
import com.restaurant.userservice.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@Slf4j
@RestController
public class UserController {
	
	
	
	@Autowired
	private UserService userService;
	

	@Value("${google.clientId}")
    private String googleClientId;
	
	@Value("${spring.social.facebook.appId}")
	private String fbClientId;
	
	@Value("${spring.social.facebook.appSecret}")
	private String fbClientToken;
		
	
	@PostMapping(value = UserServiceConstants.VALIDATEGOOGLESOCIALLOGIN_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> validateSocialLogin(@RequestBody SocialLoginToken token) throws IOException, UserNotFoundException{
		log.info("inside user service to get all users");
		final NetHttpTransport transport = new NetHttpTransport();
        final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder verifier =
                new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                .setAudience(Collections.singletonList(googleClientId));
        final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), token.getValue());
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();        
        if(userService.isUserExists(payload.getEmail())) {
            return userService.getUserByEmailForSocialLogin(payload.getEmail());
        }
        else {
            return userService.addNewUserViaGoogleSocialLogin(payload);
        }
	}

	@PostMapping(value = UserServiceConstants.VALIDATEFACEBOOKSOCIALLOGIN_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> validateFbLogin(@PathVariable("id") String id, @RequestBody SocialLoginToken token) throws IOException, UserNotFoundException{
		log.info("inside FB service to get all users");
		FacebookConnectionFactory factory = new FacebookConnectionFactory(fbClientId, fbClientToken);
		OAuth2Operations operations = factory.getOAuthOperations();
		AccessGrant accessToken = new AccessGrant(token.getValue());
		Connection<Facebook> connection =factory.createConnection(accessToken);
		Facebook facebook = connection.getApi();
		String[] fields = {"id","name","picture"};
		User userProfile = facebook.fetchObject("me", User.class, fields);
		ObjectMapper mapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String,Object> data = mapper.convertValue(userProfile.getExtraData().getOrDefault("picture",new LinkedHashMap<>()), LinkedHashMap.class);
		ProfilePicture picture = mapper.convertValue(data.getOrDefault("data",new ProfilePicture()), ProfilePicture.class);
		System.out.println(userProfile.getId());
		if(userService.isUserExists(userProfile.getId())) {
            return userService.getUserByEmailForSocialLogin(userProfile.getId());
        }
        else {
        	return userService.addNewUserViaFacebookSocialLogin(userProfile,picture);
        }
	}
	
	@PostMapping(value = UserServiceConstants.REGISTERNEWUSER_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> registerUser(@RequestBody UserDetails user) throws UserAlreadyExistsException{
		log.info("inside user service to get all users");
        return userService.addNewUser(user);
	}
	
	@GetMapping(value = UserServiceConstants.VALIDATEAPPLOGIN_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> loginUser(Authentication user, HttpServletResponse response) throws UserNotFoundException, InvalidUsernameOrPasswordException{
		log.info("inside user service to get all users");
		AuthSuccessResponse authResponse = new AuthSuccessResponse();
		authResponse.setEmail(user.getPrincipal().toString());
		authResponse.setToken(response.getHeader(SecurityConstants.JWT_HEADER));
		authResponse.setAuthenticated(true);
		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}
	
	@GetMapping(value = UserServiceConstants.RETRIEVEALLUSERS_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllUsers() throws UserNotFoundException{
		log.info("inside user update service to get all users");		
		return userService.getAllUsers();
	}
	
	@PutMapping(value = UserServiceConstants.UPDATEUSER_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateUser(@RequestBody UserDetails user) throws UserNotFoundException, InvalidUsernameOrPasswordException{
		log.info("inside user update service to get all users");		
		return userService.updateUser(user);
	}
	
	@GetMapping(value = UserServiceConstants.RETRIEVEUSERBYEMAIL_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUserByEmail(@PathVariable("email") String email ) throws UserNotFoundException, InvalidUsernameOrPasswordException{
		log.info("inside user update service to get all users");		
		return userService.getUserByEmail(email);
	}
	
	@GetMapping(value = UserServiceConstants.RETRIEVEUSERBYUSERID_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUserByUserId(@PathVariable("userId") String userId ) throws UserNotFoundException, InvalidUsernameOrPasswordException{
		log.info("inside user update service to get all users");		
		return userService.getUserByUserId(userId);
	}
	
	@PutMapping(value = UserServiceConstants.UPDATECARTITEMS_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateUserCart(@PathVariable("userId") String userId, @RequestBody List<CartItemsInfo> cartItems) throws UserNotFoundException, InvalidUsernameOrPasswordException{
		log.info("inside user update service to get all users");		
		return userService.updateUserCart(userId, cartItems);
	}
	
	@GetMapping(value = UserServiceConstants.VALIDATEUSER)
	public void validateUser(@RequestHeader("Authorization") String token){
		log.info("inside validate user");
	}
	
	@GetMapping(value = UserServiceConstants.ISUSEREXISTS)
	public String isUserExists(@PathVariable("email") String email) throws UserNotFoundException{
		return userService.checkUser(email);
	}
	
	@PostMapping(value = UserServiceConstants.UPLOADUSERIMAGE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> uploadUserImage(@PathVariable("userId") String userId, @RequestParam("imageFile") MultipartFile file) throws IOException, UserNotFoundException {
		return userService.uploadUserImage(userId,file);
	}
	
	@PostMapping(value = UserServiceConstants.RETRIEVEUSERIMAGE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUserImage(@RequestBody BlobImage image) throws IOException, DataFormatException {
		return userService.getUserImage(image);
	}
	
	@PutMapping(value = UserServiceConstants.RESETPASSWORD, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> resetPassword(@RequestBody ResetPwdObject resetPwdObj) throws UserNotFoundException {
		return userService.resetPassword(resetPwdObj);
	}
	
	@GetMapping(value = UserServiceConstants.RETRIEVEALLCUSTOMERS_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllCustomers() throws UserNotFoundException {
		return userService.getAllCustomers();
	}
}
