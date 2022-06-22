package com.terminus.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.terminus.model.Failure;
import com.terminus.model.Generic;
import com.terminus.model.Result;
import com.terminus.model.User;
import com.terminus.service.IUserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	IUserService userService;

	@GetMapping("/getUserByKey")
	public Result<User, Failure> getUser(@RequestParam String key, @RequestParam String value) {
		return userService.getUserByKey(key, value);
	}

	@PostMapping("/addUser")
	public Result<User, Failure> addUser(@Valid @RequestBody User user) {
		return userService.addUser(user);
	}

	@PutMapping("/updateUser")
	public Result<Generic, Failure> updateUser(@RequestBody Map<String, Object> update) {
		return userService.updateUser(update);
	}

	@DeleteMapping("/deleteUser")
	public Result<Generic, Failure> deleteUser(@RequestParam String userId) {
		return userService.deleteUser(userId);
	}

	@GetMapping("/getAllUserByKey")
	public Result<List<User>, Failure> getAllUserByKey(@RequestParam String key, @RequestParam String value) {
		return userService.getAllUserByKey(key, value);
	}
}
