package uk.gergely.kiss.emailclient.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.gergely.kiss.emailclient.service.repo.MessageEntity;
import uk.gergely.kiss.emailclient.service.repo.MessageEntityRepository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageEntityRepository repo;
    Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    public MessageServiceImpl(MessageEntityRepository repo) {
        this.repo = repo;
    }

    @Override
    public void saveBatchOfMessages(List<MessageDTO> messageList) {
        for (MessageDTO message : messageList) {
            if (message.getSubject() != null && message.getContent() != null) {
                MessageEntity messageEntity = createMessageEntity(message);
                save(messageEntity);
            }
        }
    }

    @Override
    public List<MessageEntity> findAllByDone( ) {
        return StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public MessageEntity findById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new NoResultException());
    }

    private void save(MessageEntity messageEntity) {
        try {
            repo.save(messageEntity);
        } catch (Exception e) {

        }
    }

    private MessageEntity createMessageEntity(MessageDTO messageDTO) {
        String subject = messageDTO.getSubject();
        String n = "";
        for (int i = 0; i < subject.toCharArray().length; i++) {
            try {
                int t = Integer.parseInt(subject.charAt(i) + "");
                n = n + "" + t;
            } catch (Exception e) {

            }
        }
        log.info("id {}", n);
        MessageEntity messageEntity = new MessageEntity();
        Integer id = Integer.parseInt(n);

        messageEntity.setId(id);
        if (subject.toLowerCase().contains("easy")) {
            messageEntity.setDifficulty("easy");
        } else if (subject.toLowerCase().contains("medium")) {
            messageEntity.setDifficulty("medium");
        } else if (subject.toLowerCase().contains("hard")) {
            messageEntity.setDifficulty("hard");
        } else {
            messageEntity.setDifficulty("unknown");
        }

        messageEntity.setContent(parseContent(messageDTO.getContent()));
        return messageEntity;
    }

    private String parseContent(String content) {
        int n = 0;
        int ind = 0;
        for (int i = 0; i < content.toCharArray().length; i++) {
            if (ind > 1) {
                break;
            }
            if (content.toCharArray()[i] == '.') {
                n = i;
                ind++;
            }
        }
        int m = content.toLowerCase().indexOf("upgrade to premium");
        String ccc = content.substring(n, m).trim();
        String rep1 = ccc.replaceAll(".\\r\\n\\r\\n", " ");
        String rep2 = rep1.replaceAll("\\r\\n", " ");
        String rep3 = rep2.replaceAll("--------------------------------------------------------------------------------", " ");
        return rep3;
    }
}
