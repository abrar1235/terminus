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
public class User {

	private String id;

	@NotNull(message = "Name Cannot be Null")
	@NotEmpty(message = "Name Cannot be Empty")
	private String name;

	@NotNull(message = "Email Cannot be Null")
	@NotEmpty(message = "Email Cannot be Empty")
	private String email;
	private String profession;

	@NotNull(message = "Password Cannot be Null")
	@NotEmpty(message = "Password Cannot be Empty")
	private String password;

	private String token;
}
