package com.practice.flagpicker.repository;

import com.practice.flagpicker.model.Country;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CountryRepository extends MongoRepository<Country, Object> {
    List<Country> findByContinentIsLike(String continentPattern);
    Country findCountryByNameIsLike(String name);
}