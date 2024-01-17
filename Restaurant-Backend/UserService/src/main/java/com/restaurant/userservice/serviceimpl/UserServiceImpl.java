package com.restaurant.userservice.serviceimpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.restaurant.userservice.constants.SecurityConstants;
import com.restaurant.userservice.constants.UserServiceConstants;
import com.restaurant.userservice.exception.InvalidUsernameOrPasswordException;
import com.restaurant.userservice.exception.UserAlreadyExistsException;
import com.restaurant.userservice.exception.UserNotFoundException;
import com.restaurant.userservice.feign.ReviewFeign;
import com.restaurant.userservice.model.AuthSuccessResponse;
import com.restaurant.userservice.model.BlobImage;
import com.restaurant.userservice.model.CartItemsInfo;
import com.restaurant.userservice.model.CustomSucccessResponse;
import com.restaurant.userservice.model.Product;
import com.restaurant.userservice.model.ProfilePicture;
import com.restaurant.userservice.model.ResetPwdObject;
import com.restaurant.userservice.model.UserDetails;
import com.restaurant.userservice.repository.UserRepository;
import com.restaurant.userservice.service.UserService;
import com.restaurant.userservice.utility.UniqueIdGeneratorService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UniqueIdGeneratorService uniqueIdGenerator;

	@Autowired
	private ReviewFeign reviewFeign;
	
	CustomSucccessResponse response;
	@Autowired
	PasswordEncoder passwordEncoder;

	public String generateToken(UserDetails user) {
		SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
		String token = Jwts.builder().setIssuer("AMMA KI KADAI").setSubject("JWT Token")
				.claim("username", user.getName()).claim("authorities", "ROLE_SOCIAL_USER").setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + 30000000)) // 8 hrs
				.signWith(key).compact();
		return token;
	}

	@Override
	public boolean isUserExists(String email) {
		// TODO Auto-generated method stub
		Optional<UserDetails> user = userRepository.findByEmail(email);
		return user.isEmpty() ? false : true;
	}

	@Override
	public ResponseEntity<Object> getUserByEmailForSocialLogin(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		UserDetails user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("User Not Found."));
		String token = generateToken(user);
		AuthSuccessResponse authResponse = new AuthSuccessResponse(user.getEmail(), token, true);
		return new ResponseEntity<>(authResponse, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<Object> addNewUserViaFacebookSocialLogin(User user, ProfilePicture picture) {
		// TODO Auto-generated method stub
		UserDetails socialUser = new UserDetails();
		socialUser.setEmail(user.getId());
		socialUser.setName(user.getName());
		socialUser.setImg_url(picture.getUrl());
		String token = generateToken(socialUser);
		String userId = uniqueIdGenerator.generateUniqueUsedId(user.getName().split(" ")[0]);
		socialUser.setUserId(userId);
		userRepository.save(socialUser);
		AuthSuccessResponse authResponse = new AuthSuccessResponse(socialUser.getEmail(), token, true);
		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> addNewUserViaGoogleSocialLogin(Payload payload) {
		// TODO Auto-generated method stub
		UserDetails socialUser = new UserDetails();
		socialUser.setEmail(payload.getEmail());
		socialUser.setName(payload.get("name").toString());
		socialUser.setImg_url(payload.get("picture").toString());
		String token = generateToken(socialUser);
		String userId = uniqueIdGenerator.generateUniqueUsedId(socialUser.getName().split(" ")[0]);
		socialUser.setUserId(userId);
		userRepository.save(socialUser);
		AuthSuccessResponse authResponse = new AuthSuccessResponse(socialUser.getEmail(), token, true);
		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> addNewUser(UserDetails user) throws UserAlreadyExistsException {
		// TODO Auto-generated method stub
		Optional<UserDetails> userData = userRepository.findByEmail(user.getEmail());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if (!userData.isEmpty()) {
			throw new UserAlreadyExistsException("User Already Exists");
		}
		String userId = uniqueIdGenerator.generateUniqueUsedId(user.getName().split(" ")[0]);
		user.setUserId(userId);
		userRepository.save(user);
		response = new CustomSucccessResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(),
				"New User Registered Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> validateUser(UserDetails user)
			throws UserNotFoundException, InvalidUsernameOrPasswordException {
		// TODO Auto-generated method stub
		UserDetails userData = userRepository.findByEmail(user.getEmail())
				.orElseThrow(() -> new UserNotFoundException("User Not Found."));
		if (null != user.getPassword() && null != userData.getPassword()
				&& !passwordEncoder.matches(user.getPassword(), userData.getPassword())) {
			throw new InvalidUsernameOrPasswordException("Invalid Username or Password");
		}
		return new ResponseEntity<>(userData, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> updateUser(UserDetails user)
			throws UserNotFoundException, InvalidUsernameOrPasswordException {
		UserDetails userData = userRepository.findByEmail(user.getEmail())
				.orElseThrow(() -> new UserNotFoundException("User Not Found."));
		userRepository.deleteByEmail(userData.getEmail());
		userRepository.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getUserByEmail(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		UserDetails user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("User Not Found."));
		return new ResponseEntity<>(user, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<Object> getUserByUserId(String userId) throws UserNotFoundException {
		// TODO Auto-generated method stub
		UserDetails user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User Not Found."));
		return new ResponseEntity<>(user, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<Object> updateUserCart(String userId, List<CartItemsInfo> cartItems)
			throws UserNotFoundException {
		// TODO Auto-generated method stub
		UserDetails user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User Not Found."));
		if (user.getCart().isEmpty()) {
			user.setCart(cartItems);
		} else {
			//List<CartItemsInfo> updatedCart = new ArrayList<>();
			user.getCart().addAll(
					cartItems.stream()
					.filter((cartItem)-> {
						for(CartItemsInfo item: user.getCart()) {
							if(item.getPid() == cartItem.getPid())
								return false;
						}
						return true;
						})							
					.collect(Collectors.toList())
					);
			//user.setCart(updatedCart);
			user.getCart().stream().forEach(System.out::println);

		}
		userRepository.deleteByEmail(user.getEmail());
		userRepository.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getAllUsers() throws UserNotFoundException {
		// TODO Auto-generated method stub
		List<UserDetails> userList = userRepository.findAll();
		if (CollectionUtils.isEmpty(userList)) {
			throw new UserNotFoundException("No User Found.");
		}
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

	@Override
	public String checkUser(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		UserDetails user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("User Not Found."));
		return String.valueOf(user.getPhoneNo());
	}

	@Override
	public ResponseEntity<Object> uploadUserImage(String userId, MultipartFile file)
			throws IOException, UserNotFoundException {
		// TODO Auto-generated method stub
		UserDetails user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User Not Found."));
		System.out.println("Original Image Byte Size - " + file.getBytes().length);
		BlobImage img = new BlobImage(file.getOriginalFilename(), file.getContentType(),
				compressBytes(file.getBytes()));
		user.setBlobImage(img);
		user.setImg_url(null);
		userRepository.save(user);
		reviewFeign.updateUserImages(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getUserImage(BlobImage image) throws DataFormatException, IOException {
		// TODO Auto-generated method stub
		image.setPicByte(decompressBytes(image.getPicByte()));
		return new ResponseEntity<>(image, HttpStatus.OK);
	}

	// compress the image bytes before storing it in the database
	public static byte[] compressBytes(byte[] data) throws IOException {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		outputStream.close();
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
	}

	// uncompress the image bytes before returning it to the angular application
	public static byte[] decompressBytes(byte[] data) throws DataFormatException, IOException {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!inflater.finished()) {
			int count = inflater.inflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		outputStream.close();
		return outputStream.toByteArray();
	}

	@Override
	public ResponseEntity<Object> resetPassword(ResetPwdObject resetPwdObj) throws UserNotFoundException {
		UserDetails user = userRepository.findByEmail(resetPwdObj.getEmail())
				.orElseThrow(() -> new UserNotFoundException("User Not Found."));
		System.out.println(resetPwdObj.getNew_pwd());
		user.setPassword(passwordEncoder.encode(resetPwdObj.getNew_pwd()));
		userRepository.deleteByEmail(user.getEmail());
		userRepository.save(user);
		response = new CustomSucccessResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(),
				"Password Updated Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getAllCustomers() {
		// TODO Auto-generated method stub
		List<UserDetails> usersList = userRepository.findAll();
		List<UserDetails> customers = usersList.stream()
				.filter((user)-> user.getRole().stream()
						.filter(role -> UserServiceConstants.CUSTOMERROLES.contains(role)).findFirst().isPresent())
				.filter((user)-> {
					if(user.getBlobImage() != null ) {
						try {
							user.getBlobImage().setPicByte(decompressBytes(user.getBlobImage().getPicByte()));
						} catch (DataFormatException | IOException e) {
							e.printStackTrace();
						} 
					}
					return true;
					}).collect(Collectors.toList());
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}

}
