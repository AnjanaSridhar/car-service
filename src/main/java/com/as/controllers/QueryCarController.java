package com.as.controllers;

import com.as.exceptions.CarNotFoundException;
import com.as.services.CarServiceImpl;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cars")
@Slf4j
public class QueryCarController {
    private final CarServiceImpl carServiceImpl;
    private final Gson GSON = new Gson();

    @Autowired
    public QueryCarController(CarServiceImpl carServiceImpl) {
        this.carServiceImpl = carServiceImpl;
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrieveCars(HttpServletRequest request) {
        try {
            return ResponseEntity.ok(GSON.toJson(carServiceImpl.getAll()));
        } catch (Exception e) {
            log.error("Exception during retrieveCars ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrieveCar(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(GSON.toJson(carServiceImpl.get(id)));
        } catch (CarNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Exception during retrieveCar({}) ", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}