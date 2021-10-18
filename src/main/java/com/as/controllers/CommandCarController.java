package com.as.controllers;

import com.as.exceptions.CarNotFoundException;
import com.as.model.CarRequest;
import com.as.services.CarService;
import com.as.validators.CarValidator;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.Optional;

import static com.as.helpers.ResponseHelper.errorMessage;

@Slf4j
@RestController
@RequestMapping("/cars")
public class CommandCarController {
    private final CarService carService;
    private final Gson GSON = new Gson();

    @Autowired
    public CommandCarController(CarService service) {
        this.carService = service;
    }

    @RequestMapping(
            value = "",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCar(HttpServletRequest servletRequest,
                                             @RequestBody(required = false) String body) {
        try {
            CarRequest request = fromJson(body, CarRequest.class);
            Optional<String> error = CarValidator.validate(request);
            return error.map(s -> errorMessage("Missing/Invalid " + s, HttpStatus.BAD_REQUEST))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.CREATED).body(GSON.toJson(carService.create(request))));
        }
        catch(JsonParseException e) {
            return errorMessage("Missing/Invalid body", HttpStatus.BAD_REQUEST);
        } catch(Exception e) {
            log.error("Exception during createCar ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCar(HttpServletRequest servletRequest,
                                            @PathVariable("id") String id) {
        try {
            carService.delete(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (CarNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch(Exception e) {
            log.error("Exception during deleteCar ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCar(HttpServletRequest servletRequest,
                                            @PathVariable("id") String id, @RequestBody(required = false) String body) {
        try {
            CarRequest request = fromJson(body, CarRequest.class);
            Optional<String> error = CarValidator.validate(request);
            if(error.isPresent()) return errorMessage("Missing/Invalid " + error.get(), HttpStatus.BAD_REQUEST);
            carService.update(request, id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch(JsonParseException e) {
            return errorMessage("Missing/Invalid body", HttpStatus.BAD_REQUEST);
        } catch (CarNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch(Exception e) {
            log.error("Exception during updateCar ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public <T> T fromJson(String json, Type type) throws JsonParseException {
        if (json == null || json.isEmpty()) {
            throw new JsonParseException("JSON document null or empty");
        }
        return GSON.fromJson(json, type);
    }
}