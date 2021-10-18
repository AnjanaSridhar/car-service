package com.as.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
public class CarRequest {
    private String make;
    private String model;
    private String colour;
    private int year;
}
