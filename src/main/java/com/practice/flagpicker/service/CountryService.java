package com.practice.flagpicker.service;

import com.practice.flagpicker.exception.CountryNotFoundException;
import com.practice.flagpicker.model.Continent;
import com.practice.flagpicker.model.Country;
import com.practice.flagpicker.repository.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {
    @Autowired
    private final CountryRepository countryRepository;
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CountryService(CountryRepository countryRepository){
        this.countryRepository = countryRepository;
    }

    public List<Country> getAllCountriesByContinent(String continentName) {
        List<Country> countries = countryRepository.findByContinentIsLike(continentName == null ? "" : continentName);
        if(countries.size() == 0 && continentName != null){
            LOG.info("Could not find any continent with name {}", continentName);
            throw new CountryNotFoundException(String.format("Could not find any continent with name %s", continentName));
        }
        return countries;
    }

    public void saveContinents(List<Continent> continents){
        try {
            countryRepository.saveAll(continents.stream()
                    .flatMap(cont -> cont.countries.stream().map(country -> new Country(country.getName(), country.getFlag(), cont.continent)))
                    .collect(Collectors.toList())
            );
        }
        catch(DuplicateKeyException dupEx){
            LOG.error("Exception occurred while pushing country data to db. Details {}", dupEx.getMessage());
        }
        catch(Exception ex){
            LOG.error("Unknown occurred while pushing country data to db. Details {}", ex.getMessage());
        }
    }

    public Country getCountry(String countryName){
        Country country = null;

        try {
            country = countryRepository.findCountryByNameIsLike(countryName);
            if(country != null){
                LOG.info("Country details for {} retrieved successfully. Associated continent is {}",
                        countryName, country.getContinent());
            }
            else {
                throw new CountryNotFoundException(String.format("Could not find country flag for %s", countryName));
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return country;
    }
}
