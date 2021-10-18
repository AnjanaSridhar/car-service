package com.as.services;

import com.as.exceptions.CarNotFoundException;
import com.as.model.CarRequest;
import com.as.model.CarResponse;
import com.as.model.Model;
import com.as.model.Word;
import com.as.persistence.model.Car;
import com.as.persistence.repo.CarRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Component
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository repository;
    @Autowired
    private CarEnrichment enrichment;
    private static final Gson GSON = new Gson();

    @Override
    public List<CarResponse> getAll() {
         return StreamSupport.stream(repository.findAll().spliterator(), false).map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public CarResponse get(String id) throws CarNotFoundException {
        return toResponse(repository.findById(id).orElseThrow(CarNotFoundException::new));
    }

    @Override
    public CarResponse create(CarRequest request) {
        Car.CarBuilder carBuilder = Car.builder()
                .withId(UUID.randomUUID().toString())
                .withColour(request.getColour())
                .withMake(request.getMake())
                .withModel(request.getModel())
                .withYear(request.getYear())
                .withSoundsLike(getWords(request.getModel()));
        return toResponse(repository.save(carBuilder.build()));
    }

    @Override
    public void delete(String id) throws CarNotFoundException {
        repository.findById(id).orElseThrow(CarNotFoundException::new);
        repository.deleteById(id);
    }

    @Override
    public void update(CarRequest request, String id) throws CarNotFoundException {
        Car car = repository.findById(id).orElseThrow(CarNotFoundException::new);
        Car.CarBuilder builder = Car.builder()
                .withId(id)
                .withColour(request.getColour())
                .withMake(request.getMake())
                .withModel(request.getModel())
                .withYear(request.getYear());
        if (!car.getModel().equals(request.getModel())) builder.withSoundsLike(getWords(request.getModel()));
        repository.save(builder.build());
    }

    private CarResponse toResponse(Car car) {
        return CarResponse.builder()
                .withId(car.getId())
                .withMake(car.getMake())
                .withColour(car.getColour())
                .withModel(Model.builder().withName(car.getModel())
                        .withSoundsLike(GSON.fromJson(car.getSoundsLike(), new TypeToken<List<String>>() {}.getType())).build())
                .withYear(car.getYear()).build();
    }

    private String getWords(String model) {
        String words = "";
        try {
            words = enrichment.soundsSimilar(model);
        } catch (Exception e) {
            log.error("Data not enriched!", e);
        }
        List<Word> wordsList = GSON.fromJson(String.valueOf(words), new TypeToken<List<Word>>() {
        }.getType());
        return GSON.toJson(wordsList.stream().map(Word::getWord)
                .filter(word -> !word.equals(model)).limit(10).collect(Collectors.toList()));
    }
}
