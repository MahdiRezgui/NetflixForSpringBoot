package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Item;
import com.example.demo.model.User;



@Transactional
public interface ItemRepository extends CrudRepository<Item,Long> {
	List<Item> findByUser(User user);
	Item findByName(String name);
	
}
