package vn.ncduy_dev.todo.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import vn.ncduy_dev.todo.Entity.User;
import vn.ncduy_dev.todo.Repository.UserRepository;
import vn.ncduy_dev.todo.Service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void createUser_shouldReturnUser_WhenEmailValid() {
        // Chuẩn bị (service)
        User inputUser = new User(null, "ncduy", "ncduy2111@gmail.com");
        User outputUser = new User(1L, "ncduy", "ncduy2111@gmail.com");

        when(this.userRepository.existsByEmail(inputUser.getEmail())).thenReturn(false);

        when(this.userRepository.save(any())).thenReturn(outputUser);

        // Hành động (controller)
        User result = this.userService.createUser(inputUser);

        // So sánh
        assertEquals(1L, result.getId());
    }

    @Test
    public void createUser_shouldThrowException_WhenEmailInvalid() {
        // Chuẩn bị (service)
        User inputUser = new User(null, "ncduy", "ncduy2111@gmail.com");

        when(this.userRepository.existsByEmail(inputUser.getEmail())).thenReturn(true);

        // Hành động (controller)
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            this.userService.createUser(inputUser);
        });

        // So sánh
        assertEquals(ex.getMessage(), "Email already exists");
    }

    @Test
    public void getAllUser_shouldReturnAllUser() {
        // Chuẩn bị (service)
        List<User> listOutputUser = new ArrayList<>();
        listOutputUser.add(new User(1L, "duy", "test@gmail.com"));
        listOutputUser.add(new User(2L, "duy2", "test@gmail.com"));

        when(this.userRepository.findAll()).thenReturn(listOutputUser);

        // Hành động (controller)
        List<User> result = this.userService.getAllUsers();

        // So sánh
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getName(), "duy");

    }

    @Test
    public void getAllUserById_shouldReturnOptionalUser() {
        // Chuẩn bị (service)
        Long inputId = 1L;
        User inputUser = new User(inputId, "duy", "test@gmail.com");
        Optional<User> outputUser = Optional.of(inputUser);

        when(this.userRepository.findById(1L)).thenReturn(outputUser);

        // Hành động (controller)
        Optional<User> result = this.userService.getUserById(inputId);

        // So sánh
        assertEquals(true, result.isPresent());
    }

    @Test
    public void updateUser_shouldReturnUser_WhenIdValid() {
        // Chuẩn bị (service)
        Long inputId = 1L;
        User inputUser = new User(inputId, "duy", "test@gmail.com");
        User outputUser = new User(inputId, "duyUpdate", "test@gmail.com");

        when(this.userRepository.findById(inputId)).thenReturn(Optional.of(inputUser));

        when(this.userRepository.save(any())).thenReturn(outputUser);

        // Hành động (controller)
        User result = this.userService.updateUser(inputId, outputUser);

        // So sánh
        assertEquals(inputUser.getName(), result.getName());
    }

    @Test
    public void updateUser_shouldThrowException_WhenIdInvalid() {
        // Chuẩn bị (service)
        Long inputId = 1L;
        User outputUser = new User();

        when(this.userRepository.findById(inputId)).thenReturn(Optional.empty());

        // Hành động (controller)
        Exception ex = assertThrows(NoSuchElementException.class, () -> {
            this.userService.updateUser(inputId, outputUser);
        });

        // So sánh
        assertEquals(ex.getMessage(), "User not found");
    }

    @Test
    public void deleteUser_shouldReturnVoid_WhenIdValid() {
        // Chuẩn bị (service)
        Long inputId = 1L;

        when(this.userRepository.existsById(inputId)).thenReturn(true);

        // Hành động (controller)
        this.userService.deleteUser(inputId);

        // So sánh
        verify(this.userRepository).deleteById(inputId);
    }

    @Test
    public void deleteUser_shouldThrowException_WhenIdInvalid() {
        // Chuẩn bị (service)
        Long inputId = 1L;

        when(this.userRepository.existsById(inputId)).thenReturn(false);

        // Hành động (controller)
        Exception ex = assertThrows(NoSuchElementException.class, () -> {
            this.userService.deleteUser(inputId);
        });

        // So sánh
        assertEquals(ex.getMessage(), "User not found");
    }

}
