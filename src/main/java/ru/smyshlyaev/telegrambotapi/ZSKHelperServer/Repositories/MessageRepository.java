package ru.smyshlyaev.telegrambotapi.ZSKHelperServer.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smyshlyaev.telegrambotapi.ZSKHelperServer.Models.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findMessageBySenderMessage(long userId);

}
