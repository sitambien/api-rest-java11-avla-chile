package com.avla.apirestavlachile.services;

import java.util.List;

import com.avla.apirestavlachile.entities.User;

public interface IUserService {
	
	public List<User> findAll();

	public User findById(Long id);

	public User save(User user);
    
	public void delete(Long id);
}
