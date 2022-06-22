package com.terminus.controller;

import com.terminus.model.Generic;
import com.terminus.model.Result;
import com.terminus.model.User;
import com.terminus.service.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController subject;

    @Mock
    IUserService userService;

    @Test
    void getUserByKeyTest() {
        when(userService.getUserByKey(anyString(), anyString()))
                .thenReturn(Result.success(User.builder().id(UUID.randomUUID().toString()).build()));
        assertNotNull(subject.getUser("test", "test"));
    }

    @Test
    void getAllUserByKeyTest() {
        when(userService.getAllUserByKey(anyString(), anyString()))
                .thenReturn(Result.success(List.of(User.builder().id(UUID.randomUUID().toString()).build())));
        assertNotNull(subject.getAllUserByKey("test", "test"));
    }

    @Test
    void addUserTest() {
        when(userService.addUser(any(User.class)))
                .thenReturn(Result.success(User.builder().id(UUID.randomUUID().toString()).build()));
        assertNotNull(subject.addUser(User.builder().id(UUID.randomUUID().toString()).build()));
    }

    @Test
    void updateUserTest() {
        when(userService.updateUser(anyMap()))
                .thenReturn(Result.success(Generic.builder().message("updated").build()));
        assertNotNull(subject.updateUser(Map.of("test", "test")));
    }

    @Test
    void deleteTest() {
        when(userService.deleteUser(anyString()))
                .thenReturn(Result.success(Generic.builder().message("updated").build()));
        assertNotNull(subject.deleteUser("test"));
    }
}
