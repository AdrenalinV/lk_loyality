package ru.gb.lk_loyality.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.lk_loyality.entities.City;
import ru.gb.lk_loyality.repositories.CityRepository;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public City getCityByTitle(String title) {
        return cityRepository.findCityByTitle(title);
    }
}
