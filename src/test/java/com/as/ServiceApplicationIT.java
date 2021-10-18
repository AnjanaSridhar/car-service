package com.as;

import com.as.model.CarRequest;
import com.as.model.CarResponse;
import com.as.spring.ServiceApplication;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.Assert.assertEquals;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.profiles.active = local"})
public class ServiceApplicationIT {
    @LocalServerPort
    private int port;
    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();
    static final Gson GSON = new Gson();

    static {


    }

    @Test
    public void carService() {
        //Given
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/cars");
        //When
        ResponseEntity<String> responseEntity = makeCall(builder, GSON.toJson(getRequestBody()), HttpMethod.POST);
        //Then
        assertEquals(201, responseEntity.getStatusCodeValue());

        //Given
        CarResponse car = GSON.fromJson(responseEntity.getBody(), CarResponse.class);
        builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/cars/" + car.getId());
        //When
        responseEntity = makeCall(builder, GSON.toJson(getRequestBody().withColour("yellow")), HttpMethod.PUT);
        //Then
        assertEquals(204, responseEntity.getStatusCodeValue());

        //Given
        builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/cars/" + car.getId());
        //When
        responseEntity = makeCall(builder, null, HttpMethod.GET);
        //Then
        assertEquals(200, responseEntity.getStatusCodeValue());
        car = GSON.fromJson(responseEntity.getBody(), CarResponse.class);
        assertEquals("yellow", car.getColour());

        //Given
        builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/cars/" + car.getId());
        //When
        responseEntity = makeCall(builder, null, HttpMethod.DELETE);
        //Then
        assertEquals(202, responseEntity.getStatusCodeValue());

        //Given
        builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/cars");
        //When
        responseEntity = makeCall(builder, null, HttpMethod.GET);
        //Then
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("[]", responseEntity.getBody());

        //Given
        builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/cars/" + car.getId());
        //When
        responseEntity = makeCall(builder, null, HttpMethod.GET);
        //Then
        assertEquals(404, responseEntity.getStatusCodeValue());

    }

    private ResponseEntity<String> makeCall(UriComponentsBuilder builder, String body, HttpMethod method) {
        headers.add("content-type", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
        return this.restTemplate
                .exchange(builder.build().encode().toUri(), method, entity, String.class);
    }

    private CarRequest.CarRequestBuilder getRequestBody() {
        return CarRequest.builder()
                .withColour("red")
                .withMake("Toyota")
                .withModel("Camray")
                .withYear(2020);
    }
}