package com.terminus.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terminus.model.Credentials;
import com.terminus.model.Failure;
import com.terminus.model.Result;
import com.terminus.model.User;
import com.terminus.service.ILoginService;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

	@Autowired
	ILoginService loginService;

	@PostMapping
	public Result<User, Failure> login(@Valid @RequestBody Credentials credentials) {
		return loginService.login(credentials);
	}
}
