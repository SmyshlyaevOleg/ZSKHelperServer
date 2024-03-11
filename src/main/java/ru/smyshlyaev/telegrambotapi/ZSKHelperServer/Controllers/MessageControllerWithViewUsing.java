package ru.smyshlyaev.telegrambotapi.ZSKHelperServer.Controllers;


import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import ru.smyshlyaev.telegrambotapi.ZSKHelperServer.DTO.MessageDTO;
import ru.smyshlyaev.telegrambotapi.ZSKHelperServer.DTO.PeopleDTO;
import ru.smyshlyaev.telegrambotapi.ZSKHelperServer.Kafka.KafkaTelegramBotAPIProducer;
import ru.smyshlyaev.telegrambotapi.ZSKHelperServer.Models.Message;
import ru.smyshlyaev.telegrambotapi.ZSKHelperServer.Models.People;
import ru.smyshlyaev.telegrambotapi.ZSKHelperServer.Services.MessageService;
import ru.smyshlyaev.telegrambotapi.ZSKHelperServer.Services.PeopleService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/index")
public class MessageControllerWithViewUsing {
    private static final org.apache.log4j.Logger LOGGER= Logger.getLogger(MessageControllerWithViewUsing.class);

    private final MessageService messageService;
    private final PeopleService peopleService;
    private final ModelMapper modelMapper;
    private final KafkaTelegramBotAPIProducer kafkaTelegramBotAPIProducer;


    @Autowired
    public MessageControllerWithViewUsing(MessageService messageService, PeopleService peopleService, ModelMapper modelMapper, KafkaTelegramBotAPIProducer kafkaTelegramBotAPIProducer) {
        this.messageService = messageService;
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;

        this.kafkaTelegramBotAPIProducer = kafkaTelegramBotAPIProducer;
    }
    @GetMapping() //стартовая страница
    public String indexPage() {
        return "message/startedPage";
    }

    @GetMapping("/message") // страница с сообщениями
    public String getAllMessage(Model model) {

        model.addAttribute("messages", messageService.findAllMessage().stream().map(this::convertToMessageDTO).collect(Collectors.toList()) );
        return "message/messagePage";

    }

    @GetMapping("/people") // страница с пользователями
    public String getAllPeople(Model model) {

        model.addAttribute("users",peopleService.getAllPeople().stream().map(this::convertToPeopleDTO).collect(Collectors.toList()) );
       // return "message/peoplePage";

        return "message/peoplePage";

    }

    @GetMapping("/message/{id}") // страница с конкретным сообщением
    public String getOneMessage(@PathVariable("id") int id, Model model) {
        model.addAttribute("message",messageService.findOneMessage(id).map(this::convertToMessageDTO).get());
        return "message/show";
    }

    @PatchMapping("/message/{id}/response")
    public String sendAResponse(@PathVariable("id") int id)  {

        messageService.updateById(id);
        Optional<Message> responseMessage=messageService.findOneMessage(id);
        String message=" успешно рассмотрена";

        Map<String,Object> jsonFormatMessageValues=new HashMap<String, Object>();
        jsonFormatMessageValues.put("fullName",responseMessage.get().getSenderName());
        jsonFormatMessageValues.put("Id",responseMessage.get().getId());
        jsonFormatMessageValues.put("message", responseMessage.get().getMessage());
        jsonFormatMessageValues.put("response",message);

        kafkaTelegramBotAPIProducer.sendMessage(jsonFormatMessageValues);

            return "redirect:/index/message";

    }

    private MessageDTO convertToMessageDTO (Message message) { //метод конвертации
        return modelMapper.map(message, MessageDTO.class);
    }

    private PeopleDTO convertToPeopleDTO (People people) { //метод конвертации
        return modelMapper.map(people, PeopleDTO.class);
    }

    private Message convertToMessage (MessageDTO messageDTO) { //метод конвертации
        return modelMapper.map(messageDTO, Message.class);
    }


}
