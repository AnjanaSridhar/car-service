package com.as.controllers;

import com.as.model.CarRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

public class CommandCarControllerTest extends ControllerBase {
    @InjectMocks
    private CommandCarController controller;

    @Before
    public void setUp() {
        super.setUp();
        controller = Mockito.spy(new CommandCarController(carService));
    }

    @Test
    public void createCar_validBody_Success() {
        //When
        ResponseEntity<String> response = controller.createCar(request, GSON.toJson(getRequestBody().build()));
        //Then
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
    }

    @Test
    public void createCar_invalidBody_BadRequest() {
        //When
        ResponseEntity<String> response = controller.createCar(request, null);
        //Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());

        //When
        response = controller.createCar(request, GSON.toJson(CarRequest.builder().build()));
        //Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());

        //When
        response = controller.createCar(request, GSON.toJson(CarRequest.builder().withMake(make).build()));
        //Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());

        //When
        response = controller.createCar(request, GSON.toJson(CarRequest.builder().withMake(make).withColour(colour).build()));
        //Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());

        //When
        response = controller.createCar(request, GSON.toJson(CarRequest.builder().withMake(make).withColour(colour).withYear(year).build()));
        //Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());
    }

    @Test
    public void updateCar_validBody_Success() {
        //When
        ResponseEntity<String> response = controller.updateCar(request, "id", GSON.toJson(getRequestBody().withColour("blue").build()));
        //Then
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode().value());
    }

    @Test
    public void updateCar_invalidBody_BadRequest() {
        //When
        ResponseEntity<String> response = controller.updateCar(request, "id", null);
        //Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());

        //When
        response = controller.updateCar(request, "id", GSON.toJson(CarRequest.builder().build()));
        //Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());

        //When
        response = controller.updateCar(request, "id", GSON.toJson(CarRequest.builder().withMake(make).build()));
        //Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());

        //When
        response = controller.updateCar(request, "id", GSON.toJson(CarRequest.builder().withMake(make).withColour(colour).build()));
        //Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());

        //When
        response = controller.updateCar(request, "id", GSON.toJson(CarRequest.builder().withMake(make).withColour(colour).withYear(year).build()));
        //Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());
    }

    @Test
    public void deleteCar_validId_Success() {
        //When
        ResponseEntity<String> response = controller.deleteCar(request, "id");
        //Then
        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatusCode().value());
    }

}
