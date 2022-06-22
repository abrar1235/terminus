package com.terminus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	@Id
	@GeneratedValue(generator = "UUID-Generator")
	@GenericGenerator(name = "UUID-Generator", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "profession")
	private String profession;
	
	@Column(name = "password")
	private String password;
}
