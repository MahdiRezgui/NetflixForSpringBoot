package com.example.demo.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.UserFeignClient;
import com.example.demo.model.Item;
import com.example.demo.model.User;
import com.example.demo.util.UserContextHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;




@Service
public class ItemServiceImpl implements ItemService{
	private static final Logger LOG = LoggerFactory.getLogger(ItemService.class);
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private UserFeignClient userFeignClient; 
	
	
	@Override
	public Item addItemByUser(Item item, String username) {
		Item localItem = itemRepository.findByName(item.getName());
		if(localItem != null) {
			LOG.info("Item with name {} already exists. Nothing will be done." ,item.getName());
		}else {
			Date today=new Date();
			item.setAddDate(today);
			
			User user=userService.findByUsername(username);
			item.setUser(user);
			Item newItem = itemRepository.save(item);
			return newItem;
		}
		return localItem;
		
	}

	@Override
	public List<Item> getAllItems() {
		return (List<Item>)itemRepository.findAll();
	}

	@Override
	public List<Item> getItemsByUsername(String username) {
		User user=userService.findByUsername(username);
		return (List<Item>) itemRepository.findByUser(user);
	}

	@Override
	public Item getItemById(Long id) {
		return itemRepository.findOne(id);
	}

	@Override
	public Item updateItem(Item item) throws IOException {
		Item localItem = getItemById(item.getId());
		
		if(localItem==null) {
			throw new IOException("Item was not found");
		}else {
//			localItem.setName(item.getName());
//			localItem.setItemCondition(item.getItemCondition());
//			localItem.setStatus(item.getStatus());
//			localItem.setDescription(item.getDescription());
			return itemRepository.save(localItem);
		}
	}

	@Override
	public void deleteItemById(Long id) {
		itemRepository.delete(id);
	}

	@Override
	//@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="12000") 
	@HystrixCommand(fallbackMethod="buildFallBackUser")
	public User getUserByUsername(String username) {
		//randomRenLong();
		//return userService.findByUsername(username);
		LOG.debug("ItemService.getUserByUsername Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
		
		return userFeignClient.getUserByUsername(username);
	}
	
	private void randomRenLong() {
		Random rand =new Random();
		int randomNum=rand.nextInt((3-1)+1)+1;
		if(randomNum==3) sleep();
	}

	private void sleep() {
		try {
			Thread.sleep(11000);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private User buildFallBackUser(String usernamem) {
		User user = new User();
		user.setId(123223L);
		user.setUsername("Temp Username");
		return user;
	}
}
