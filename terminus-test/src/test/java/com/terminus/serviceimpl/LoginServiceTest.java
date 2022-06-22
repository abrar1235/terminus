package com.terminus.serviceimpl;

import com.terminus.entity.UserDTO;
import com.terminus.model.Credentials;
import com.terminus.repository.IUserRepository;
import com.terminus.util.ApplicationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class LoginServiceTest {

	@InjectMocks
	LoginService subject;

	@Mock
	IUserRepository userRepository;

	@Mock
	ApplicationUtil applicationUtil;

	Credentials credentials;

	@BeforeEach
	void setup() {
		credentials = Credentials.builder().email("test@test.com").password("test").build();
	}

	@Test
	void loginTest() throws Exception {
		UserDTO user = UserDTO.builder().id(UUID.randomUUID().toString()).name("test").email("test@test.com")
				.profession("profession").build();

		when(userRepository.loginUser(anyString(), anyString())).thenReturn(Optional.of(user));
		when(applicationUtil.encryptPassword(anyString())).thenReturn("encrypted");
		when(applicationUtil.generateToken(anyString())).thenReturn("token");
		assertNotNull(subject.login(credentials));
	}

	@Test
	void loginExceptionTest() {
		when(userRepository.loginUser(anyString(), anyString())).thenThrow(new RuntimeException("Test"));
		assertNotNull(subject.login(credentials));
	}
}
