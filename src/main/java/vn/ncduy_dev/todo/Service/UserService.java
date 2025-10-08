package vn.ncduy_dev.todo.Service;

import java.util.List;
import java.util.Optional;

import vn.ncduy_dev.todo.Entity.User;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    User updateUser(Long id, User updatedUser);

    void deleteUser(Long inputUser);
}