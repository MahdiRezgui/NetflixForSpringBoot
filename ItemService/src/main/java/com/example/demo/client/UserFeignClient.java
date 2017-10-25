package com.example.demo.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;

@FeignClient("userservice")
public interface UserFeignClient {
	@RequestMapping(value = "/v1/user/{username}", consumes = "application/json")
	User getUserByUsername(@PathVariable("username") String uername);
}
