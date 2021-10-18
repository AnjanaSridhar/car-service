package com.as.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder(setterPrefix = "with")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    private String id;
    private String model;
    private String make;
    private String colour;
    private int year;
    private String soundsLike;
}
