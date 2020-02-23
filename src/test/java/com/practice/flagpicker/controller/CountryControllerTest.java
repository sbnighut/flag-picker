package com.practice.flagpicker.controller;

import com.practice.flagpicker.business.CountryService;
import com.practice.flagpicker.exception.CountryNotFoundException;
import com.practice.flagpicker.model.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CountryController.class)
public class CountryControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CountryService service;

    @Test
    public void getCountries_whenPresent_thenReturnJsonArray()
            throws Exception {

        Country c = new Country("foo", "bar", "foobar");
        List<Country> allEmployees = Arrays.asList(c);

        given(service.getAllCountriesByContinent(anyString())).willReturn(allEmployees);
        given(service.getAllCountriesByContinent("")).willReturn(allEmployees);

        ResultActions resultActions = mvc.perform(get("/country?continent=")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("foo"));
    }

    @Test
    public void getCountries_returns400_formissingcontinentname()
            throws Exception {
        given(service.getAllCountriesByContinent(anyString())).willThrow(new CountryNotFoundException("No continent with name..."));

        ResultActions resultActions = mvc.perform(get("/country")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getCountries_returns404_foremptyresult()
            throws Exception {
        given(service.getAllCountriesByContinent(anyString())).willThrow(new CountryNotFoundException("No continent with name..."));

        ResultActions resultActions = mvc.perform(get("/country?continent=random")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
