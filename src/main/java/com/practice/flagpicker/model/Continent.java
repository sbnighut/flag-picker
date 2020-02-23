package com.practice.flagpicker.model;

import lombok.Data;

import java.util.List;

@Data
public class Continent {
    public String continent;
    public List<Country> countries;
}
