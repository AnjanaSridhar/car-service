package com.as.validators;

import com.as.model.CarRequest;
import org.apache.logging.log4j.util.Strings;
import java.util.Optional;

public class CarValidator {
    public static Optional<String> validate(CarRequest request) {
        if(Strings.isEmpty(request.getMake())) return Optional.of("make");
        if(Strings.isEmpty(request.getColour())) return Optional.of("colour");
        if(request.getYear() == 0) return Optional.of("year");
        if(Strings.isEmpty(request.getModel())) return Optional.of("model");
        return Optional.empty();
    }
}
