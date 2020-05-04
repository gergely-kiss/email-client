package uk.gergely.kiss.emailclient.service;
import org.springframework.stereotype.Service;
import uk.gergely.kiss.emailclient.service.repo.MessageEntity;
import uk.gergely.kiss.emailclient.service.repo.MessageEntityRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageEntityRepository repo;

    public MessageServiceImpl(MessageEntityRepository repo) {
        this.repo = repo;
    }

    @Override
    public void saveBatchOfMessages(List<MessageDTO> messageList) {
        List<MessageEntity> messageEntityList = new ArrayList<>();
        for (MessageDTO message : messageList) {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setSubject(message.getSubject());
            messageEntity.setContent(message.getContent());
            messageEntityList.add(messageEntity);
        }
        repo.saveAll(messageEntityList);
    }
}
