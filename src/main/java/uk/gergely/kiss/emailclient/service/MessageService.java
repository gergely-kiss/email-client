package uk.gergely.kiss.emailclient.service;

import uk.gergely.kiss.emailclient.service.repo.MessageEntity;

import java.util.List;

public interface MessageService {
    void saveBatchOfMessages(List<MessageDTO> messageList);
    List<MessageEntity> findAllByDone();
    MessageEntity findById(Integer id);
}
