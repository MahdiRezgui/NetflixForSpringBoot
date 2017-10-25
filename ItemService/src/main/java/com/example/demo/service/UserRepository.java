package com.example.demo.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.User;





public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
}
