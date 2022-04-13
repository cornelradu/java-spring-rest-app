package com.example.entex;

import org.springframework.data.repository.CrudRepository;

import com.example.entex.County;
import java.util.List;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface CountyRepository extends CrudRepository<County, Integer> {
	List<County> findCountyBycountyCode(String countyCode);
}
