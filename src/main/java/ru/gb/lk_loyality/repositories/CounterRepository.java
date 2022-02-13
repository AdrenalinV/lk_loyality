package ru.gb.lk_loyality.repositories;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.lk_loyality.entities.Counter;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Long> {

}
