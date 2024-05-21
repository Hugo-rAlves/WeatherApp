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
	void getCurrentWeather_success() {
		String jsonResponse = "{\"location\":{\"name\":\"Buenos Aires\",\"region\":\"Distrito Federal\",\"country\":\"Argentina\",\"lat\":-34.59,\"lon\":-58.67},\"current\":{\"last_updated\":\"2022-01-01\",\"temp_c\":25.0}}";
		when(webClient.get()).thenReturn(requestHeadersUriSpec);
		when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
		when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(jsonResponse));

		String response = weatherApiService.getCurrentWeather("Buenos Aires");
		assertEquals(jsonResponse, response);
	}

	@Test
	void getCurrentWeather_failure() {
		when(webClient.get()).thenReturn(requestHeadersUriSpec);
		when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
		when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new WebClientResponseException(404, "Not Found", null, null, null)));

		assertThrows(WebClientResponseException.class, () -> weatherApiService.getCurrentWeather("InvalidCity"));
	}

	@Test
	void getWeather_success() throws JsonProcessingException {
		Location location = new Location();
		location.setName("Buenos Aires");
		location.setLat(-34.59);
		location.setLon(-58.67);

		String jsonResponse = "{\"location\":{\"name\":\"Buenos Aires\",\"region\":\"Distrito Federal\",\"country\":\"Argentina\",\"lat\":-34.59,\"lon\":-58.67},\"current\":{\"last_updated\":\"2022-01-01\",\"temp_c\":25.0}}";
		when(webClient.get()).thenReturn(requestHeadersUriSpec);
		when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
		when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(jsonResponse));

		Weather weather = weatherApiService.getWeather(location);
		assertNotNull(weather);
		assertEquals("Buenos Aires", weather.getCityLocation().getName());
	}

	@Test
	void getLocations_success() throws JsonProcessingException {
		String jsonResponse = "[{\"name\":\"Buenos Aires\",\"region\":\"Distrito Federal\",\"country\":\"Argentina\",\"lat\":-34.59,\"lon\":-58.67}]";
		when(webClient.get()).thenReturn(requestHeadersUriSpec);
		when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
		when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(jsonResponse));

		Location[] locations = searchApiService.getLocations("Buenos Aires");
		assertNotNull(locations);
		assertEquals(1, locations.length);
		assertEquals("Buenos Aires", locations[0].getName());
	}

	@Test
	void getLocations_failure() {
		when(webClient.get()).thenReturn(requestHeadersUriSpec);
		when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
		when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new WebClientResponseException(404, "Not Found", null, null, null)));

		assertThrows(WebClientResponseException.class, () -> searchApiService.getLocations("InvalidCity"));
	}

	@Test
	void getLocationController_success() throws JsonProcessingException {
		Location location = new Location();
		location.setName("Buenos Aires");

		when(searchService.getLocations(anyString())).thenReturn(new Location[]{location});

		ResponseEntity<?> response = apiController.getLocation("Buenos Aires");
		assertEquals(200, response.getStatusCodeValue());
		List<Location> locations = (List<Location>) response.getBody();
		assertNotNull(locations);
		assertEquals(1, locations.size());
		assertEquals("Buenos Aires", locations.get(0).getName());
	}

	@Test
	void getLocationController_failure() throws JsonProcessingException {
		when(searchService.getLocations(anyString())).thenReturn(new Location[]{});

		ResponseEntity<?> response = apiController.getLocation("InvalidCity");
		assertEquals(200, response.getStatusCodeValue());
		List<Location> locations = (List<Location>) response.getBody();
        assert locations != null;
        assertTrue(locations.isEmpty());
	}

	@Test
	void getWeatherController_success() throws JsonProcessingException {
		Location location = new Location();
		location.setName("Buenos Aires");

		Weather weather = new Weather();
		weather.setCityLocation(location);

		when(weatherService.getWeather(any(Location.class))).thenReturn(weather);

		ResponseEntity<?> response = apiController.getWeather(location);
		assertEquals(200, response.getStatusCodeValue());
		Weather responseBody = (Weather) response.getBody();
		assertNotNull(responseBody);
		assertEquals("Buenos Aires", responseBody.getCityLocation().getName());
	}

	@Test
	void getWeatherController_failure() throws JsonProcessingException {
		when(weatherService.getWeather(any(Location.class))).thenReturn(null);

		Location location = new Location();
		location.setName("InvalidCity");

		ResponseEntity<?> response = apiController.getWeather(location);
		assertEquals(200, response.getStatusCodeValue());
		Weather responseBody = (Weather) response.getBody();
		assertNull(responseBody);
	}

}
