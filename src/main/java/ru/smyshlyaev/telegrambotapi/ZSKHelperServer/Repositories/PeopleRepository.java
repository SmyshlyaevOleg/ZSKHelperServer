package ru.smyshlyaev.telegrambotapi.ZSKHelperServer.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smyshlyaev.telegrambotapi.ZSKHelperServer.Models.People;

@Repository
public interface PeopleRepository extends JpaRepository<People,Long> {

     People findPeopleByTelegramId(long userId);
}

