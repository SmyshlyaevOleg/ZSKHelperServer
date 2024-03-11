package ru.smyshlyaev.telegrambotapi.ZSKHelperServer.Services;


import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smyshlyaev.telegrambotapi.ZSKHelperServer.Models.Message;
import ru.smyshlyaev.telegrambotapi.ZSKHelperServer.Repositories.MessageRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final EntityManager entityManager;

    @Autowired
    public MessageService(MessageRepository messageRepository, EntityManager entityManager) {
        this.messageRepository = messageRepository;
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public List<Message> findAllMessage() {
        return messageRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Message> findOneMessage(long id) {
        return messageRepository.findById(id);
    }

    @Transactional
    public void updateById(long id) {
        Session session=entityManager.unwrap(Session.class);
        Message message=session.get(Message.class,id);
        message.setStatus("Рассмотрена");

    }


}


