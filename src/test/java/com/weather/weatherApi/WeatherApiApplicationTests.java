package com.weather.weatherApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.weather.weatherApi.Controller.ApiController;
import com.weather.weatherApi.Model.Location;
import com.weather.weatherApi.Model.Weather;
import com.weather.weatherApi.Service.SearchApiService;
import com.weather.weatherApi.Service.WeatherApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class WeatherApiApplicationTests {

	@Mock
	private WebClient.Builder clientBuilder;

	@Mock
	private WebClient webClient;

	@SuppressWarnings("rawtypes")
	@Mock
	private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

	@SuppressWarnings("rawtypes")
	@Mock
	private WebClient.RequestHeadersSpec requestHeadersSpec;

	@Mock
	private WebClient.ResponseSpec responseSpec;

	@Mock
	private WeatherApiService weatherService;

	@Mock
	private SearchApiService searchService;

	@InjectMocks
	private WeatherApiService weatherApiService;

	@InjectMocks
	private SearchApiService searchApiService;

	@InjectMocks
	private ApiController apiController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(clientBuilder.baseUrl(anyString())).thenReturn(clientBuilder);
		when(clientBuilder.defaultHeader(anyString(), anyString())).thenReturn(clientBuilder);
		when(clientBuilder.build()).thenReturn(webClient);
	}

	@Test
	void getLocationController_success() throws JsonProcessingException {
		Location location = new Location();
		location.setName("Recife");

		when(searchService.getLocations(anyString())).thenReturn(new Location[]{location});

		ResponseEntity<?> response = apiController.getLocation("Recife");
		assertEquals(200, response.getStatusCodeValue());
		List<Location> locations = (List<Location>) response.getBody();
		assertNotNull(locations);
		assertEquals(1, locations.size());
		assertEquals("Recife", locations.get(0).getName());
	}


	@Test
	void getWeatherController_success() throws JsonProcessingException {
		Location location = new Location();
		location.setName("Recife");

		Weather weather = new Weather();
		weather.setCityLocation(location);

		when(weatherService.getWeather(any(Location.class))).thenReturn(weather);

		ResponseEntity<?> response = apiController.getWeather(location);
		assertEquals(200, response.getStatusCodeValue());
		Weather responseBody = (Weather) response.getBody();
		assertNotNull(responseBody);
		assertEquals("Recife", responseBody.getCityLocation().getName());
	}

}
