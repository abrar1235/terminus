package com.terminus.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.terminus.entity.UserDTO;
import com.terminus.model.Failure;
import com.terminus.model.Generic;
import com.terminus.model.Result;
import com.terminus.model.User;
import com.terminus.repository.IUserRepository;
import com.terminus.service.IUserService;
import com.terminus.util.ApplicationUtil;

import lombok.extern.slf4j.Slf4j;

import static com.terminus.model.Failure.*;
import static com.terminus.mapper.UserMapper.*;
import static com.terminus.constants.UserModelGen.*;

@Service
@Slf4j
public class UserService implements IUserService {

	@Autowired
	IUserRepository userRepository;

	@Autowired
	ApplicationUtil applicationUtil;

	@Autowired
	EntityManager manager;

	@Override
	public Result<User, Failure> getUserByKey(String key, String value) {
		try {
			log.info("Fetching User with key [ {} ] and value [ {} ]", key, value);
			boolean validKey = isValidKey(key, Arrays.asList(ID, EMAIL));
			if (!validKey) {
				String message = String.format("Invalid Key Passed [ %s ]", key);
				log.error(message);
				throw new IllegalArgumentException(message);
			}
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Tuple> query = builder.createTupleQuery();
			Root<UserDTO> root = query.from(UserDTO.class);
			query.multiselect(root.get(ID), root.get(NAME), root.get(EMAIL), root.get(PROFESSION))
					.where(builder.equal(root.get(key), value));
			Tuple user = manager.createQuery(query).getSingleResult();
			log.info("user found with id [ {} ]", user.get(0));
			return Result.success(fromTuple(user, Arrays.asList(ID, NAME, EMAIL, PROFESSION)));
		} catch (Exception e) {
			log.error("an error occured while fetching user", e);
			return Result.failure(newInstance(e));
		}
	}

	@CacheEvict(value = "users", allEntries = true)
	@Override
	public Result<List<User>, Failure> getAllUserByKey(String key, String value) {
		try {
			log.info("Fetching User with key [ {} ] and value [ {} ]", key, value);
			boolean validKey = isValidKey(key, Arrays.asList(EMAIL, NAME, PROFESSION));
			if (!validKey) {
				String message = String.format("Invalid Key Passed [ %s ]", key);
				log.error(message);
				throw new IllegalArgumentException(message);
			}
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Tuple> query = builder.createTupleQuery();
			Root<UserDTO> root = query.from(UserDTO.class);
			query.multiselect(root.get(ID), root.get(NAME), root.get(EMAIL), root.get(PROFESSION))
					.where(builder.like(root.get(key), "%" + value + "%"));
			List<Tuple> user = manager.createQuery(query).getResultList();
			return Result.success(fromTupleList(user, Arrays.asList(ID, NAME, EMAIL, PROFESSION)));
		} catch (Exception e) {
			log.error("an error occured while fetching user", e);
			return Result.failure(newInstance(e));
		}
	}

	@Override
	public Result<User, Failure> addUser(User user) {
		try {
			log.info("adding user with email [ {} ]", user.getEmail());
			UserDTO request = fromPOJO(user);
			request.setPassword(applicationUtil.encryptPassword(user.getPassword()));
			request = userRepository.save(request);
			log.info("User Added with id [ {} ]", request.getId());
			User response = fromDTO(request);
			response.setToken(applicationUtil.generateToken(response.getEmail()));
			return Result.success(response);
		} catch (Exception e) {
			log.error("an error occurred while adding user", e);
			return Result.failure(newInstance(e));
		}
	}

	@Override
	@Transactional
	public Result<Generic, Failure> updateUser(Map<String, Object> request) {
		try {
			log.info("Updating user with id [ {} ]", request.get(ID));
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaUpdate<UserDTO> update = builder.createCriteriaUpdate(UserDTO.class);
			Root<UserDTO> root = update.from(UserDTO.class);
			request.forEach((key, value) -> update.set(root.get(key), value));
			update.where(builder.equal(root.get(ID), request.get(ID)));
			int updates = manager.createQuery(update).executeUpdate();
			log.info("User Updated, total Updates [ {} ]", updates);
			String message = updates > 0 ? "User Updated" : "Invalid Request";
			return Result.success(Generic.builder().message(message).build());
		} catch (Exception e) {
			log.error("an error occurred while updating user", e);
			return Result.failure(newInstance(e));
		}
	}

	@Override
	public Result<Generic, Failure> deleteUser(String userId) {
		try {
			log.info("Deleting user with id [ {} ]", userId);
			userRepository.deleteById(userId);
			String message = "User Deleted";
			log.info(message);
			return Result.success(Generic.builder().message(message).build());
		} catch (Exception e) {
			log.error("an error occured while deleting user", e);
			return Result.failure(newInstance(e));
		}
	}

	private boolean isValidKey(String key, List<String> allKeys) {
		return allKeys.stream().anyMatch(entry -> entry.equalsIgnoreCase(key));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = getUserByKey(EMAIL, username).getSuccess();
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				new ArrayList<>(List.of(() -> "USER")));
	}
}
