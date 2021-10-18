package com.as.persistence.repo;

import com.as.persistence.model.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface CarRepository extends CrudRepository<Car, String> {
}
