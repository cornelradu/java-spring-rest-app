package com.example.entex;

import org.springframework.data.repository.CrudRepository;

import com.example.entex.Locality;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

import java.util.List;

public interface LocalityRepository extends CrudRepository<Locality, Integer> {
	List<Locality> findLocalityBylocalityName(String localityName);
}
