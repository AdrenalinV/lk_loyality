package ru.gb.lk_loyality.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.lk_loyality.entities.Counter;

public interface CounterRepository extends JpaRepository<Counter, Long> {
}
