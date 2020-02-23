package com.practice.flagpicker.controller;

import com.practice.flagpicker.model.Country;
import com.practice.flagpicker.business.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by snighut on 2/22/20.
 */
@RestController
@RequestMapping(value = "")
public class CountryController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final CountryService searchService;

    public CountryController(CountryService searchService) {
        this.searchService = searchService;
    }

    @RequestMapping(value = "country",
            method = RequestMethod.GET)
    public List<Country> getCountries(@RequestParam(value = "continent", required = false) String continent) {
        LOG.info("Fetching countries");
        return searchService.getAllCountriesByContinent(continent);
    }

    @RequestMapping(value = "country/{name}",
            method = RequestMethod.GET)
    public Country getCountry(@PathVariable("name") String name) {
        LOG.info("Fetching country details");
        return searchService.getCountry(name);
    }
}
