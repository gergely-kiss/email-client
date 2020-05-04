package uk.gergely.kiss.emailclient.service;

import java.util.List;

public interface MessageService {
    void saveBatchOfMessages(List<MessageDTO> messageList);
}
