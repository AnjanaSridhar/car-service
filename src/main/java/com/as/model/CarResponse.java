package com.as.model;

import lombok.Builder;
import lombok.Getter;

@Builder(setterPrefix = "with")
@Getter
public class CarResponse {
    private String id;
    private String make;
    private String colour;
    private int year;
    private Model model;
}
