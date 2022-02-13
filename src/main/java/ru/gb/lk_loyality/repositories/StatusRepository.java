package ru.gb.lk_loyality.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.lk_loyality.entities.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findStatusByTitle(String title);
}
