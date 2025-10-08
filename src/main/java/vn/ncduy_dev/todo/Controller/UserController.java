package vn.ncduy_dev.todo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.ncduy_dev.todo.Entity.ApiResponse;
import vn.ncduy_dev.todo.Entity.User;
import vn.ncduy_dev.todo.Service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/users")
	public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) {
		User created = userService.createUser(user);
		ApiResponse<User> result = new ApiResponse<User>(HttpStatus.CREATED, "createUser", created, null);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@GetMapping("/users")
	public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
		ApiResponse<List<User>> result = new ApiResponse<List<User>>(HttpStatus.OK, "getAllUsers",
				userService.getAllUsers(), null);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
		Optional<User> user = this.userService.getUserById(id);

		if (user != null && user.isPresent()) {
			ApiResponse<User> result = new ApiResponse<User>(HttpStatus.OK, "getUserById", user.get(), null);
			return ResponseEntity.ok(result);
		} else {
			ApiResponse<User> errors = new ApiResponse<User>(HttpStatus.NOT_FOUND,
					"Không tìm thấy người dùng với ID là: " + id,
					null, "USER_NOT_FOUND");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
		}
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
		User updated = userService.updateUser(id, user);
		ApiResponse<User> result = new ApiResponse<User>(HttpStatus.OK, "updateUser", updated, null);

		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		ApiResponse<Void> result = new ApiResponse<Void>(HttpStatus.NO_CONTENT, "deleteUser", null, null);
		return ResponseEntity.ok().body(result);
	}
}
