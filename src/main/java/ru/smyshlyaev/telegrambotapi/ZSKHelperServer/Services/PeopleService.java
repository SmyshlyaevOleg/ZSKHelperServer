package ru.smyshlyaev.telegrambotapi.ZSKHelperServer.Services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smyshlyaev.telegrambotapi.ZSKHelperServer.Models.People;
import ru.smyshlyaev.telegrambotapi.ZSKHelperServer.Repositories.PeopleRepository;

import java.util.List;


@Service
public class PeopleService {
    private final PeopleRepository peopleRepository;


    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Transactional(readOnly = true)
    public List<People> getAllPeople() {
        return peopleRepository.findAll();
    }
}

