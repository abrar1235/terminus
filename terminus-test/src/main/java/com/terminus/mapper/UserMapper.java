package com.terminus.mapper;

import java.util.Collections;
import java.util.List;

import javax.persistence.Tuple;

import com.terminus.entity.UserDTO;
import com.terminus.model.User;
import static com.terminus.constants.UserModelGen.*;

public class UserMapper {

	public static User fromDTO(UserDTO user) {
		return User.builder().id(user.getId()).name(user.getName()).email(user.getEmail())
				.profession(user.getProfession()).build();
	}

	public static UserDTO fromPOJO(User user) {
		return UserDTO.builder().name(user.getName()).email(user.getEmail()).profession(user.getProfession()).build();
	}

	public static User fromTuple(Tuple tuple, List<String> keys) {
		return tuple == null ? new User() : parseUser(tuple, keys);
	}

	public static List<User> fromTupleList(List<Tuple> tuple, List<String> keys) {
		return tuple.isEmpty() ? Collections.emptyList() : tuple.stream().map(entry -> fromTuple(entry, keys)).toList();
	}

	private static User parseUser(Tuple tuple, List<String> keys) {
		User user = new User();
		int index = 0;
		for (String key : keys) {
			switch (key) {
			case ID -> {
				user.setId(tuple.get(index, String.class));
				index++;
			}
			case NAME -> {
				user.setName(tuple.get(index, String.class));
				index++;
			}
			case EMAIL -> {
				user.setEmail(tuple.get(index, String.class));
				index++;
			}
			case PROFESSION -> {
				user.setProfession(tuple.get(index, String.class));
				index++;
			}
			default -> {
				// default
			}
			}
		}
		return user;
	}

	private UserMapper() {
	}
}
