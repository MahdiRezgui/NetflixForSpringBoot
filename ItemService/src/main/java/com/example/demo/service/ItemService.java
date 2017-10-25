package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import com.example.demo.model.Item;
import com.example.demo.model.User;



public interface ItemService {
	Item addItemByUser(Item item, String username);
	List<Item> getAllItems();
	List<Item> getItemsByUsername(String username);
	Item getItemById(Long id);
	Item updateItem(Item item) throws IOException;
	void deleteItemById(Long id);
	User getUserByUsername(String username);
}
