package vn.ncduy_dev.todo.Service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.ncduy_dev.todo.Entity.User;
import vn.ncduy_dev.todo.Repository.UserRepository;
import vn.ncduy_dev.todo.Service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User createUser(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email already exists");
		}
		return userRepository.save(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}

	public User updateUser(Long id, User updatedUser) {
		return userRepository.findById(id).map(user -> {
			user.setName(updatedUser.getName());
			user.setEmail(updatedUser.getEmail());
			return userRepository.save(user);
		}).orElseThrow(() -> new NoSuchElementException("User not found"));
	}

	public void deleteUser(Long inputUser) {
		if (!userRepository.existsById(inputUser)) {
			throw new NoSuchElementException("User not found");
		}
		userRepository.deleteById(inputUser);
	}
}
