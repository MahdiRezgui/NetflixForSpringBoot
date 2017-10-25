package com.example.demo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import com.example.demo.model.Item;
import com.example.demo.model.User;
import com.example.demo.service.ItemService;
import com.example.demo.service.UserService;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EnableCircuitBreaker
public class ItemServiceApplication implements CommandLineRunner{
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		User user = userService.findByUsername("jadams");
		
		Item item=new Item();
		item.setName("Item 1");
		item.setItemCondition("New");
		item.setStatus("Active");
		item.setAddDate(new Date());
		item.setDescription("This is an item description");
		item.setUser(user);
		
		
		Item item2=new Item();
		itemService.addItemByUser(item, user.getUsername());
		item2.setName("Item 2");
		item2.setItemCondition("Used");
		item2.setStatus("Inactive");
		item2.setAddDate(new Date());
		item2.setDescription("This is an item description for item2");
		item2.setUser(user);
		
		itemService.addItemByUser(item2, user.getUsername());
	}
}
