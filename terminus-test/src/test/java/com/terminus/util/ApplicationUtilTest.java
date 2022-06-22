package com.terminus.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(SpringExtension.class)
class ApplicationUtilTest {

	@InjectMocks
	ApplicationUtil subject;

	@Test
	void encryptPasswordTest() throws Exception {
		Assertions.assertNotNull(subject.encryptPassword("test"));
	}

	@Test
	void generateTokenTest() {
		ReflectionTestUtils.setField(subject, "secret", "secret");
		Assertions.assertNotNull(subject.generateToken("test"));
	}
}
