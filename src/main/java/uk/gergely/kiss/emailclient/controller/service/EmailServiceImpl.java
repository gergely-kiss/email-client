package uk.gergely.kiss.emailclient.controller.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import uk.gergely.kiss.emailclient.controller.rsc.ControllerConstants;
import uk.gergely.kiss.emailclient.service.EmailReceiverComponent;

@Service
public class EmailServiceImpl implements EmailService {
    private final EmailReceiverComponent emailReceiverComponent;

    public EmailServiceImpl(EmailReceiverComponent emailReceiverComponent) {
        this.emailReceiverComponent = emailReceiverComponent;
    }

    @Override
    public JSONObject process(org.json.simple.JSONObject request) {
        return emailReceiverComponent.downloadEmails(
                request.get(ControllerConstants.PROTOCOL).toString(),
                request.get(ControllerConstants.HOST).toString(),
                request.get(ControllerConstants.PORT).toString(),
                request.get(ControllerConstants.EMAIL).toString(),
                request.get(ControllerConstants.PW).toString(),
                request.get(ControllerConstants.FILTER).toString(),
                request.get(ControllerConstants.FOLDER).toString()
        );
    }
}
