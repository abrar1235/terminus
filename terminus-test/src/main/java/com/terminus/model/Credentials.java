package com.terminus.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {

	@NotNull(message = "Email cannot be Null")
	@NotEmpty(message = "Email cannot be empty")
	private String email;

	@NotNull(message = "Password cannot be Null")
	@NotEmpty(message = "Password cannot be empty")
	private String password;
}
