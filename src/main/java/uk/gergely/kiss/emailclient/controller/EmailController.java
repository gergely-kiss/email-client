package uk.gergely.kiss.emailclient.controller;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import uk.gergely.kiss.emailclient.controller.service.EmailService;
import uk.gergely.kiss.emailclient.service.MessageService;
import uk.gergely.kiss.emailclient.service.ProcessService;
import uk.gergely.kiss.emailclient.service.repo.MessageEntity;

import java.util.List;

@RestController
@RequestMapping("/rest/email")
public class EmailController {
    private final EmailService emailService;
    private final ProcessService processService;
    private final MessageService messageService;

    Logger logger = LoggerFactory.getLogger(EmailController.class);

    public EmailController(EmailService emailService, ProcessService processService, MessageService messageService) {
        this.emailService = emailService;
        this.processService = processService;
        this.messageService = messageService;
    }

    @PostMapping
    @Async
    public ResponseEntity<?> process(@RequestBody JSONObject request) {
        if (!processService.checkIfProcessIsRunningAndCreateIfNotExist("downloadEmails")) {
            emailService.process(request);
            return new ResponseEntity<>("downloadEmails is started", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("downloadEmails is already running", HttpStatus.OK);
        }
    }

    @GetMapping(path = "/")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(messageService.findAllByDone(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getAll(@PathVariable Integer id){
        return new ResponseEntity<>(messageService.findById(id), HttpStatus.OK);
    }


}
