package com.itemsharing.userservice.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestBody;

import com.itemsharing.userservice.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
}
