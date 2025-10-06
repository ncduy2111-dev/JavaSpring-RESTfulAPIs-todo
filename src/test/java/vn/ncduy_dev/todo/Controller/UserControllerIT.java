package vn.ncduy_dev.todo.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.ncduy_dev.todo.IntegrationTest;
import vn.ncduy_dev.todo.Model.User;
import vn.ncduy_dev.todo.Repository.UserRepository;

@IntegrationTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void innitDB() {
        this.userRepository.deleteAll();
    }

    @Test
    public void createUser_shouldReturnUser_whenValid() throws Exception {
        // Chuẩn bị
        User inputUser = new User(null, "testITCreate", "testITcrteate@gmai.com");

        // Hành động
        String resultStr = mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(inputUser)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        User outputUser = objectMapper.readValue(resultStr, User.class);

        // So sánh
        assertEquals(inputUser.getName(), outputUser.getName());
    }

    // @Test
    // public void createUser_shouldReturnBadRequest_WhenEmailExists() throws
    // Exception {
    // // Chuẩn bị
    // this.userRepository
    // .saveAndFlush(new User(null, "testCreateExitEmail",
    // "testCreateExitEmail@gmail.com"));

    // User duplicateUser = new User(null, "testCreate",
    // "testCreateExitEmail@gmail.com");

    // // Hành động
    // this.mockMvc.perform(
    // post("/users")
    // .contentType(MediaType.APPLICATION_JSON)
    // .content(objectMapper.writeValueAsString(duplicateUser)))
    // .andExpect(status().isBadRequest());
    // }

    @Test
    public void getAllUser_shouldReturnAllUser() throws Exception {
        // Chuẩn bị
        User user1 = new User(null, "test1ITgetAll", "test1ITgetAll@gmai.com");

        User user2 = new User(null, "test2ITgetAll", "test2ITgetAll@gmai.com");

        List<User> inputData = List.of(user1, user2);

        this.userRepository.saveAll(inputData);

        // Hành động
        String resultStr = this.mockMvc.perform(
                get("/users"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<User> outputData = this.objectMapper.readValue(resultStr, new TypeReference<List<User>>() {
        });
        // So sánh
        assertEquals(inputData.size(), outputData.size());
        assertEquals(inputData.get(0).getName(), outputData.get(0).getName());

    }

    @Test
    public void getOneUser_shouldReturnAllUser() throws Exception {
        // Chuẩn bị
        User inputUser = this.userRepository.saveAndFlush(new User(null, "test1ITgetOne", "test1ITgetAll@gmai.com"));

        // Hành động
        String resultStr = this.mockMvc.perform(
                get("/users/{inputId}", inputUser.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        User outputUser = this.objectMapper.readValue(resultStr, User.class);

        // So sánh
        assertEquals(inputUser.getName(), outputUser.getName());
    }

    @Test
    public void getOneUser_shouldEmpty_whenIdNotFound() throws Exception {
        // Chuẩn bị

        // Hành động
        this.mockMvc.perform(
                get("/users/{inputId}", 0))
                .andExpect(status().isNotFound());

        // So sánh

    }

    @Test
    public void updateUser_shouldReturAllUser() throws Exception {
        // Chuẩn bị
        User inputUser = this.userRepository.saveAndFlush(new User(null, "testOldUpdate", "testOldITUpdate@gmai.com"));

        User updateUser = new User(inputUser.getId(), "testNewUpdate", "testNewITUpdate@gmai.com");
        // Hành động
        String resultStr = this.mockMvc.perform(
                put("/users/{inputId}", inputUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updateUser)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        User outputUser = this.objectMapper.readValue(resultStr, User.class);

        // So sánh
        assertEquals(updateUser.getName(), outputUser.getName());
    }

    @Test
    public void updateUser_shouldEmpty_whenIdNotFound() throws Exception {
        // Chuẩn bị

        // Hành động
        this.mockMvc.perform(
                put("/users/{inputId}", 0))
                .andExpect(status().isBadRequest());

        // So sánh

    }

    @Test
    public void deleteUser_shouldReturnVoid_WhenIdValid() throws Exception {
        // Chuẩn bị
        User inputUser = this.userRepository.saveAndFlush(new User(null, "testDelete", "testOldITDelete@gmai.com"));

        // Hành động
        this.mockMvc.perform(
                delete("/users/{inputId}", inputUser.getId()))
                .andExpect(status().isNoContent());

        long count = this.userRepository.count();
        // So sánh
        assertEquals(0, count);
    }

    // @Test
    // public void deleteUser_shouldEmpty_whenIdNotFound() throws Exception {
    // // Chuẩn bị

    // // Hành động
    // this.mockMvc.perform(
    // delete("/users/{inputId}", 0))
    // .andExpect(status().isInternalServerError());

    // // So sánh

    // }
}
