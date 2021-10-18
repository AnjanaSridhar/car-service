package com.as.controllers;

import com.as.exceptions.CarNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class QueryCarControllerTest extends ControllerBase {
    @InjectMocks
    private QueryCarController controller;

    @Before
    public void setUp() {
        super.setUp();
        controller = Mockito.spy(new QueryCarController(carService));
    }

    @Test
    public void retrieveCars_Success() {
        //When
        ResponseEntity<String> response = controller.retrieveCars(request);
        //Then
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    @Test
    public void retrieveCar_Success() {
        //When
        ResponseEntity<String> response = controller.retrieveCar(request, "id");
        //Then
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    @Test
    public void retrieveCar_NotFound() throws CarNotFoundException {
        //Given
        when(carService.get(anyString())).thenThrow(new CarNotFoundException());
        //When
        ResponseEntity<String> response = controller.retrieveCar(request, "id");
        //Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
    }
}
