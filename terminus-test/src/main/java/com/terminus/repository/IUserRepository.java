package com.terminus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.terminus.entity.UserDTO;

public interface IUserRepository extends JpaRepository<UserDTO, String> {

	@Query(value = "SELECT * FROM users WHERE email = :email and password = :password", nativeQuery = true)
	Optional<UserDTO> loginUser(@Param("email") String email, @Param("password") String password);
}
