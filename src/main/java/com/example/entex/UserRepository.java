package com.example.entex;

import org.springframework.data.repository.CrudRepository;

import com.example.entex.User;

import java.util.List;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {
	List<User> findUserByname(String name);
}
