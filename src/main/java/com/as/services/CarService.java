package com.as.services;

import com.as.exceptions.CarNotFoundException;
import com.as.model.CarRequest;
import com.as.model.CarResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CarService {
    List<CarResponse> getAll();

    CarResponse get(String id) throws CarNotFoundException;

    CarResponse create(CarRequest request);

    void delete(String id) throws CarNotFoundException;

    void update(CarRequest request, String id) throws CarNotFoundException;
}
