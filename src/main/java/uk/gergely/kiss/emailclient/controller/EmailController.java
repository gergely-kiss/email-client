package uk.gergely.kiss.emailclient.controller;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gergely.kiss.emailclient.controller.service.EmailService;

@RestController
@RequestMapping("/rest/email")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<?> process(@RequestBody JSONObject request) {
        return new ResponseEntity<>(emailService.process(request), HttpStatus.OK);
    }
}
