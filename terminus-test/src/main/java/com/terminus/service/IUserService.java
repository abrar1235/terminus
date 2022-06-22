package com.terminus.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.terminus.model.Failure;
import com.terminus.model.Generic;
import com.terminus.model.Result;
import com.terminus.model.User;

public interface IUserService extends UserDetailsService {

	Result<User, Failure> getUserByKey(String key, String value);

	Result<List<User>, Failure> getAllUserByKey(String key, String value);

	Result<User, Failure> addUser(User user);

	Result<Generic, Failure> updateUser(Map<String, Object> update);

	Result<Generic, Failure> deleteUser(String userId);
}
