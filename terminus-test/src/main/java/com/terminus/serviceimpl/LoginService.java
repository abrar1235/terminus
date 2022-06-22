package com.terminus.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.terminus.entity.UserDTO;
import com.terminus.model.Credentials;
import com.terminus.model.Failure;
import com.terminus.model.Result;
import com.terminus.model.User;
import com.terminus.repository.IUserRepository;
import com.terminus.service.ILoginService;
import com.terminus.util.ApplicationUtil;

import lombok.extern.slf4j.Slf4j;
import static com.terminus.model.Failure.*;

import java.util.Optional;

import static com.terminus.mapper.UserMapper.*;

@Service
@Slf4j
public class LoginService implements ILoginService {

	@Autowired
	IUserRepository userRepository;

	@Autowired
	ApplicationUtil applicationUtil;

	@Override
	public Result<User, Failure> login(Credentials credentials) {
		try {
			log.info("Login user with email [ {} ]", credentials.getEmail());
			Optional<UserDTO> user = userRepository.loginUser(credentials.getEmail(),
					applicationUtil.encryptPassword(credentials.getPassword()));
			User response = fromDTO(user.orElseThrow(() -> new RuntimeException("No Record Found")));
			log.info("User Found with id [ {} ]", response.getId());
			response.setToken(applicationUtil.generateToken(response.getEmail()));
			return Result.success(response);
		} catch (Exception e) {
			log.error("an error occurred while login user", e);
			return Result.failure(newInstance(e));
		}
	}

}
