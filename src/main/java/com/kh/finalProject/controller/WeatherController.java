package com.kh.finalProject.controller;

import com.kh.finalProject.dto.WeatherDto;
import com.kh.finalProject.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/get")
    public ResponseEntity<List<WeatherDto>> getRegionWeather(@RequestParam String regionList) {
        List<WeatherDto> list = weatherService.getWeatherData(regionList);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

//    @GetMapping("/scheduledGet")
//    public ResponseEntity<List<WeatherDto>> getScheduledWeatherData() {
//        List<WeatherDto> weatherData = weatherService.getCachedWeatherData();
//        return new ResponseEntity<>(weatherData, HttpStatus.OK);
//    }
}
