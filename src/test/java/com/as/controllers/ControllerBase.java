package com.as.controllers;

import com.as.model.CarRequest;
import com.as.model.CarResponse;
import com.as.model.Model;
import com.as.services.CarService;
import com.as.services.CarServiceImpl;
import com.google.gson.Gson;
import org.junit.Before;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class ControllerBase {
    static final Gson GSON = new Gson();
    @Mock protected HttpServletRequest request;
    @Mock protected CarService carService;
    static String colour = "red", make = "Toyota", model = "sport";
    static int year = 2020;

    @Before
    public void setUp() {
        openMocks(this);
        carService = mock(CarServiceImpl.class);
        when(carService.create(any())).thenReturn(carResponse());
    }

    CarResponse carResponse() {
        return CarResponse.builder().withColour(colour).withYear(year).withMake(make).withModel(getModel()).build();
    }

    Model getModel() {
        return Model.builder().withName(model).withSoundsLike(new ArrayList<>()).build();
    }

    CarRequest.CarRequestBuilder getRequestBody() {
        return CarRequest.builder()
                .withColour(colour)
                .withMake(make)
                .withModel(model)
                .withYear(year);
    }

}
