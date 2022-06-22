package com.terminus.controller;

import com.terminus.model.Credentials;
import com.terminus.model.Result;
import com.terminus.model.User;
import com.terminus.service.ILoginService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class LoginControllerTest {

    @InjectMocks
    LoginController subject;

    @Mock
    ILoginService loginService;

    @Test
    public void loginTest() {
        Credentials credentials = Credentials.builder()
                .email("test@test.com")
                .password("test@123")
                .build();
        when(loginService.login(any(Credentials.class)))
                .thenReturn(Result.success(User.builder().id(UUID.randomUUID().toString()).build()));
        assertNotNull(subject.login(credentials));
    }
}
