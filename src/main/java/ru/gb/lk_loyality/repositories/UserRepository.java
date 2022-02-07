package ru.gb.lk_loyality.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.lk_loyality.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
