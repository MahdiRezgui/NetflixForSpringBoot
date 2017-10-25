package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Item;
import com.example.demo.model.User;
import com.example.demo.service.ItemService;
import com.example.demo.service.UserService;
import com.example.demo.util.UserContextFilter;
import com.example.demo.util.UserContextHolder;


@RestController
@RequestMapping("/v1/item")
public class ItemController {
	private static final Logger logger=LoggerFactory.getLogger(UserContextFilter.class);
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method=RequestMethod.POST)
	public Item addItem(@RequestBody Item item) {
		String username="jadams";
		return itemService.addItemByUser(item, username);
	}
	
	@RequestMapping("/itemsByUser")
	public List<Item> getAllItemsByUser(){
		String username="jadams";
		return itemService.getItemsByUsername(username);
	}
	
	@RequestMapping("/all")
	public List<Item> getAllItems(){
		return itemService.getAllItems();
	}
	
	@RequestMapping("/{id}")
	public Item getItemById(@PathVariable Long id) {
		return itemService.getItemById(id);
	}
	
	@RequestMapping(value="/{id}", method =RequestMethod.PUT)
	public Item updateItem(@PathVariable Long id,@RequestBody Item item) throws Exception{
		item.setId(id);
		return itemService.updateItem(item);
	}
	
	@RequestMapping(value="/{id}", method =RequestMethod.DELETE)
	public void deleteItemById(@PathVariable Long id) throws IOException{
		itemService.deleteItemById(id);
	}
	
	@RequestMapping(value="/user/{username}")
	public User getUserByUsername(@PathVariable String username) throws IOException{
		logger.debug("ItemServiceController Correlation id0: {}",UserContextHolder.getContext().getCorrelationId());
		return itemService.getUserByUsername(username);
	}
}
