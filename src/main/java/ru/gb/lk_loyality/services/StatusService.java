package ru.gb.lk_loyality.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.lk_loyality.entities.Status;
import ru.gb.lk_loyality.repositories.StatusRepository;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;

    public Status getStatusByTitle(String title) {
        return statusRepository.findStatusByTitle(title);
    }
}
