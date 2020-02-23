package com.practice.flagpicker.service;

import com.practice.flagpicker.model.Country;
import com.practice.flagpicker.repository.CountryRepository;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
public class SearchServiceTest {

    private CountryService countryService = Mockito.mock(CountryService.class);
    private CountryRepository countryRepository = Mockito.mock(CountryRepository.class);

    @BeforeEach
    void setup() {
        this.countryService = new CountryService(countryRepository);
    }

    @Test
    void getCountryReturnsCorrectValue() {
        when(countryRepository.findCountryByNameIsLike("China")).thenReturn(country());
        when(countryService.getCountry("China")).thenReturn(country());

        Country country = countryService.getCountry("China");
        assertThat(country).isNotNull();
        assert(country.name).equals("China");
    }

    @Test
    public void sends404ExceptionWhenNoResults() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            countryService.getAllCountriesByContinent("unknownContinent");
        });

        String expectedMessage = "Could not find any continent with name";
        String actualMessage = exception.getMessage();

        assert(actualMessage.contains(expectedMessage));
    }

    private Country country() {
        return new Country("China", "flagContent", "Asia");
    }
}